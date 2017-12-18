package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.Input.GameplayInputProcessor;
import itcom.gangstersquirrel.Items.WeaponObject;
import itcom.gangstersquirrel.Items.WeaponList;
import itcom.gangstersquirrel.KeyBindings.KeyBindings;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.EnemyObjects.MonkeyEnemy;
import itcom.gangstersquirrel.Scenes.PlayScreenHud;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.EnemyObjects.FrogEnemy;
import itcom.gangstersquirrel.Objects.Player;
import itcom.gangstersquirrel.Statistics.Statistics;
import itcom.gangstersquirrel.Tools.Box2DWorldCreator;
import itcom.gangstersquirrel.Tools.WorldContactListener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The most important screen of the game, the game itself
 */
public class PlayScreen implements Screen {

    // Main class of the project
    private final MainGameClass game;

    // Camera variables
    private OrthographicCamera camera;
    private Viewport viewport;

    // Map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // HUD
    private PlayScreenHud hud;

    // Level 1 Configuration
    private int level_1_spawnPositionX = 34;
    private int level_1_spawnPositionY = 20;

    // Level 2 Configuration
    private int level_2_spawnPositionX = 47;
    private int level_2_spawnPositionY = 30;

    // Level 3 Configuration
    private int level_3_spawnPositionX = 2;
    private int level_3_spawnPositionY = 3;

    // Box2D variables
    private World world;
    private Box2DWorldCreator box2DWorldCreator;
    private Box2DDebugRenderer box2DDebugRenderer;
    private WorldContactListener worldContactListener;
    private ArrayList<Enemy> enemiesCurrentlyInRightAttackRange = new ArrayList<>();
    private ArrayList<Enemy> enemiesCurrentlyInLeftAttackRange = new ArrayList<>();
    public ArrayList<Body> destroyBodiesQueue = new ArrayList<>();

    // Texture variables
    private TextureAtlas playerTextureAtlas;
    private TextureAtlas enemyFrogTextureAtlas;
    private TextureAtlas enemyMonkeyTextureAtlas;

    // Player variables
    private Player player;
    public static boolean isPressingExit;
    public static boolean isPressingMoveLeft;
    public static boolean isPressingMoveRight;
    public static boolean isPressingJump;

    // Enemy variables
    private ArrayList<Enemy> enemies = new ArrayList<>();

    // Game Progress and Statistics
    private GameProgress gameProgress = new GameProgress();
    private Statistics statistics = new Statistics();

    // Timer
    private float deltaTimeCount = 0f;
    private long timer = 0;

    // Item variables
    private List<WeaponObject> allWeapons = new WeaponList().getAllWeapons();

    // Keymap variables
    public static KeyBindings keyBindings;

    // Input processors
    private InputProcessor gamePlayInputProcessor;

    // Audio variables
    // Sounds = kept in memory (shouldn't be longer than 10 seconds)
    // Music = streamed from file (can be memory intensive to keep in memory)
    private Sound jumpSound;
    private Music level_1_backgroundMusic;
    private Music level_2_backgroundMusic;

    /**
     * Set up all important things, can be considered as the create() method like in the MainGameClass
     * @param game The main game class
     */
    public PlayScreen(MainGameClass game) {
        this.game = game;
        setupScreen();
    }

    /**
     * Sets up the screen
     */
    private void setupScreen() {

        // Set up player texture atlas
        playerTextureAtlas = new TextureAtlas("sprites/player/squirrel.atlas");
        enemyFrogTextureAtlas = new TextureAtlas("sprites/enemies/frog/frog.atlas");
        enemyMonkeyTextureAtlas = new TextureAtlas("sprites/enemies/monkey/monkey.atlas");

        // Set up camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGameClass.GAME_WORLD_WIDTH / MainGameClass.PPM, MainGameClass.GAME_WORLD_HEIGHT / MainGameClass.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Set up HUD
        hud = new PlayScreenHud(this);

        // Load the first map from Tiles
        mapLoader = new TmxMapLoader();

        switch (gameProgress.getCurrentLevel()) {
            case 1:
                map = mapLoader.load("maps/level_1/level_1.tmx");
                break;
            case 2:
                map = mapLoader.load("maps/level_2/level_2.tmx");
                break;
            case 3:
                map = mapLoader.load("maps/boss/boss_level.tmx");
                break;
            default:
                game.exitApplication("Couldn't find level, exiting application");
                break;
        }

        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGameClass.PPM);

        // Box2D physics setup
        world = new World(new Vector2(0, - MainGameClass.GRAVITY), true); // gravity, doSleep
        box2DDebugRenderer = new Box2DDebugRenderer();
        worldContactListener = new WorldContactListener(this);
        world.setContactListener(worldContactListener);

        // Set up the collision boxes for the ground and obstacle layers
        box2DWorldCreator = new Box2DWorldCreator(this);

        // Player set-up
        switch (gameProgress.getCurrentLevel()) {
            case 1:
                player = new Player(this, level_1_spawnPositionX, level_1_spawnPositionY);
                break;
            case 2:
                player = new Player(this, level_2_spawnPositionX, level_2_spawnPositionY);
                break;
            case 3:
                player = new Player(this, level_3_spawnPositionX, level_3_spawnPositionY);
                break;
            default:
                game.exitApplication("No current level defined, exiting application");
                break;
        }

        // Set up remaining player variables
        setUpPlayer();

        // Keybinding setup
        keyBindings = new KeyBindings();

        // Register input processors
        gamePlayInputProcessor = new GameplayInputProcessor();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gamePlayInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //Load the background music, set looping to true and play immediately
        jumpSound = game.assetManager.get("audio/jump.mp3", Sound.class);
        level_1_backgroundMusic = game.assetManager.get("audio/level_1_music.mp3", Music.class);
        level_1_backgroundMusic.setLooping(true);
        level_2_backgroundMusic = game.assetManager.get("audio/level_2_music.mp3", Music.class);
        level_2_backgroundMusic.setLooping(true);

        if (MainGameClass.PLAY_SOUNDS) {
            switch (gameProgress.getCurrentLevel()) {
                case 1:
                    level_1_backgroundMusic.play();
                    break;
                case 2:
                    level_2_backgroundMusic.play();
                    break;
                default:
                    break;
            }
        }

        // Set up timer with previous time or new time for the level
        timer = gameProgress.getCurrentTime();
    }

    private void setUpPlayer() {

        // Player health
        player.setHealth(gameProgress.getPlayerMaxHealth());

        // Add default weapon, if the players weapon list is empty
        if (gameProgress.getPlayerWeaponList().size() == 0) {
            ArrayList<WeaponObject> playerWeaponList = gameProgress.getPlayerWeaponList();
            playerWeaponList.add(allWeapons.get(0));
            gameProgress.setPlayerWeaponList(playerWeaponList);
        } else if (gameProgress.getPlayerWeaponList().size() > 0) {
            player.setWeapons(gameProgress.getPlayerWeaponList());
        }

        // Movement variables
        player.setJumpImpulseVelocity(gameProgress.getPlayerJumpImpulseVelocity());
        player.setWalkImpulseVelocity(gameProgress.getPlayerWalkImpulseVelocity());
        player.setClimbImpulseVelocity(gameProgress.getPlayerClimbImpulseVelocity());
        player.setMaxWalkVelocity(gameProgress.getPlayerMaxWalkVelocity());
        player.setMaxClimbVelocity(gameProgress.getPlayerMaxClimbVelocity());
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* -------- THIS COMMENT MARKS THE START OF THE ENTIRE RENDER LOGIC, EACH METHOD IS LINKED TO EACH OTHER -------- */
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * Will be executed once every frame
     * @param deltaTime deltaTime is the time passed between the last and the current frame in seconds. VERY IMPORTANT!
     */
    @Override
    public void render(float deltaTime) {
        this.update(deltaTime);

        // Clear the previous frame
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        // Render the Box2D Debug Lines
        if (MainGameClass.DEBUG) {
            box2DDebugRenderer.render(world, camera.combined);
        }

        // Update the player sprite
        player.update(deltaTime);

        // Update each enemy sprite
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }

        hud.stage.draw();

        // Sets the coordinate system for rendering
        game.batch.setProjectionMatrix(camera.combined);
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        game.batch.begin();

        // Draw specific textures in here with the help of OpenGL
        player.draw(game.batch);

        for (Enemy enemy : enemies) {
            enemy.draw(game.batch);
        }

        game.batch.end();
    }

    /**
     * Extends the render method, updates the world and the camera
     * @param deltaTime the time between the last and current frame in seconds
     */
    private void update(float deltaTime) {
        this.handleInput(deltaTime);

        // increase timer
        deltaTimeCount += deltaTime;
        if (deltaTimeCount >= 1) {
            timer++;
            deltaTimeCount = 0f;
        }

        // increase effect timer in the main game class (for twitch)
        game.effectStateTime += deltaTime;
        game.updateTwitchEffectTimer();

        // timeStep = amount of time the world simulates (https://github.com/libgdx/libgdx/wiki/Box2d)
        // velocity and position iterations = defines the precision of the calculations. Needs more calculation power, if higher. 6 and 2 are the recommended values
        world.step(1 / (float) MainGameClass.FPS, 6, 2);

        // Flip texture depending on the moving direction of the player
        // Don't do anything when the velocity is zero, leave it flipped or unflipped
        if (player.body.getLinearVelocity().x > 0) {
            player.flipPlayerDirection(false);
        } else if (player.body.getLinearVelocity().x < 0) {
            player.flipPlayerDirection(true);
        }

        // Update the HUD
        hud.update(deltaTime);

        // Follow player with camera
        updateCamera(deltaTime);

        // Update the camera's position
        camera.update();
        // Renderer will draw only what the camera can see
        renderer.setView(camera);

        // Destroy scheduled Box2D bodies
        destroyQueuedBodies();
    }

    /**
     * Follows the player with interpolation / lerping
     * @param deltaTime the time between the last and current frame in seconds
     */
    private void updateCamera(float deltaTime) {
        // current camera position + (target - current camera position) * lerp factor
        Vector3 position = camera.position;
        if (MainGameClass.USE_CAMERA_INTERPOLATION) {
            position.x = camera.position.x + (player.body.getPosition().x - camera.position.x) * MainGameClass.INTERPOLATION_X;
            position.y = camera.position.y + (player.body.getPosition().y - camera.position.y) * MainGameClass.INTERPOLATION_Y;
        } else {
            position = new Vector3(player.body.getPosition().x, player.body.getPosition().y, 0f);
        }
        camera.position.set(position);
    }

    /**
     * Destroys all Box2D bodies that are in the queue for deleting
     */
    private void destroyQueuedBodies() {
        Iterator<Body> i = destroyBodiesQueue.iterator();
        if (!world.isLocked()) {
            while (i.hasNext()) {
                Body b = i.next();
                world.destroyBody(b);
                i.remove();
            }
        }
    }

    /**
     * Extends the update method, handles the input and updates the game world accordingly
     * @param deltaTime the time between the last and current frame in seconds
     */
    private void handleInput(float deltaTime) {

        // Toggle debug camera
        for (Integer keyBinding : keyBindings.DEBUG) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                MainGameClass.DEBUG = !MainGameClass.DEBUG;
            }
        }

        // Exit
        if(isPressingExit) {
            isPressingExit = false; // to prevent a bug, where the variable would still be true when switching to the main menu and the game would directly exit again when starting a new level from the main menu

            // Save current time
            statistics.setSecondsPlayed(statistics.getSecondsPlayed() + timer);

            game.setScreen(new MainMenu(game));
        }

        // Jumping: user is pressing jump key, player is on jumpable terrain and the vertical velocity is 0
        if (isPressingJump && player.getIsOnJumpableGround() && player.body.getLinearVelocity().y == 0) {
            player.body.applyLinearImpulse(new Vector2(0, player.getJumpImpulseVelocity()), player.body.getWorldCenter(), true);

            if (MainGameClass.PLAY_SOUNDS) {
                jumpSound.play();
            }

            // Save jump to statistics
            statistics.setJumpsMade(statistics.getJumpsMade() + 1);
        }

        // Horizontal movement
        if (isPressingMoveRight && player.body.getLinearVelocity().x <= player.getMaxWalkVelocity()) {
            player.body.applyLinearImpulse(new Vector2(player.getWalkImpulseVelocity() * deltaTime * 60, 0), player.body.getWorldCenter(), true);
        }
        if (isPressingMoveLeft && player.body.getLinearVelocity().x >= -player.getMaxWalkVelocity()) {
            player.body.applyLinearImpulse(new Vector2(-player.getWalkImpulseVelocity() * deltaTime * 60, 0), player.body.getWorldCenter(), true);
        }

        // Attacking
        for (Integer keyBinding : keyBindings.ATTACK) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.attack();
            }
        }

        // Changing equipped weapon
        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_0) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(0);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_1) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(1);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_2) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(2);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_3) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(3);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_4) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(4);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_5) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(5);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_6) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(6);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_7) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(7);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_8) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(8);
            }
        }

        for (Integer keyBinding : keyBindings.EQUIP_WEAPON_SLOT_9) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                player.changeWeapon(9);
            }
        }

        for (Integer keyBinding : keyBindings.TAKE_SCREENSHOT) {
            if (Gdx.input.isKeyJustPressed(keyBinding)) {
                game.takeScreenshot();
            }
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* -------- THIS COMMENT MARKS THE END OF THE ENTIRE RENDER LOG, EACH METHOD IS LINKED TO EACH OTHER ------------ */
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * Gets called when the application is shown for the first time
     */
    @Override
    public void show() {

    }

    /**
     * Gets called when the application is resized
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Gets called when the application is paused, only available on Android
     */
    @Override
    public void pause() {

    }

    /**
     * Gets called when the application is resumed, only available on Android
     */
    @Override
    public void resume() {

    }

    /**
     * Gets called when the application is hidden
     */
    @Override
    public void hide() {

    }

    /**
     * Disposes of unused resources correctly when closing the application
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        level_1_backgroundMusic.dispose();
        level_2_backgroundMusic.dispose();
    }

    /**
     * Resets the player to the start of the level and resets all attributes
     */
    public void respawnPlayer(boolean levelFinished) {
        level_1_backgroundMusic.stop();
        level_2_backgroundMusic.stop();

        // Save current timer time to the statistics
        statistics.setSecondsPlayed(statistics.getSecondsPlayed() + timer);

        // Only when the level is finished, it should reset the timer
        if (levelFinished) {
            gameProgress.setCurrentTime(0);
        } else {
            log("Game over, player died, respawning now...");
            // If the player died, add one to the death counter in the statistics
            statistics.setDiedThisManyTimes(statistics.getDiedThisManyTimes() + 1);

            // Subtract player life
            gameProgress.setPlayerLifes(gameProgress.getPlayerLifes() - 1);

            gameProgress.setCurrentTime(timer);
        }

        game.setScreen(new PlayScreen(game));
    }

    /**
     * Current level finished, save time, increase level and load the next level
     */
    public void levelFinished() {
        log("Level finished, saving and loading next level...");

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://api.ludvig.xyz/gangstersquirrel/");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("level", String.valueOf(gameProgress.getCurrentLevel())));
        params.add(new BasicNameValuePair("name", gameProgress.getPlayerName()));
        params.add(new BasicNameValuePair("time", String.valueOf(timer)));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // writing error to Log
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                // EntityUtils to get the response content
                String content =  EntityUtils.toString(respEntity);
            }
        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }

        // If the old highscores is above zero, proceed. If not, this means there was no highscore set yet and there is no need to compare old time with new time
        if (statistics.getHighscoreTimes()[gameProgress.getCurrentLevel() - 1] > 0) {
            // If the new time for this level is faster, replace the old time with the new one
            if (timer < statistics.getHighscoreTimes()[gameProgress.getCurrentLevel() - 1]) {
                long[] tmp = statistics.getHighscoreTimes();
                tmp[gameProgress.getCurrentLevel() - 1] = timer;
                statistics.setHighscoreTimes(tmp);
            }
        } else {
            long[] tmp = statistics.getHighscoreTimes();
            tmp[gameProgress.getCurrentLevel() - 1] = timer;
            statistics.setHighscoreTimes(tmp);
        }

        // Increase highest finished level
        if (gameProgress.getUnlockedLevels() <= gameProgress.getCurrentLevel()) {
            gameProgress.setUnlockedLevels(gameProgress.getCurrentLevel() + 1);
        }

        // Increase current level
        if (gameProgress.getCurrentLevel() < MainGameClass.NUMBER_OF_LEVELS) {
            gameProgress.setCurrentLevel(gameProgress.getCurrentLevel() + 1);
        } else {
            log("Game finished, no more levels to play");
        }

        // Save finished level to statistics
        statistics.setLevelsFinished(statistics.getLevelsFinished() + 1);

        // Reset current lifes to the maximum
        gameProgress.setPlayerLifes(gameProgress.getPlayerMaximumLifes());

        respawnPlayer(true);
    }

    /**
     * Spawn an enemy in the current level
     * @param type the class type of the enemy, has to extend the abstract class Enemy
     * @param spawnPositionX the tile spawn position on the x axis
     * @param spawnPositionY the tile spawn position on the y axis
     */
    public void spawnEnemy(Class<? extends Enemy> type, int spawnPositionX, int spawnPositionY) {
        if (type == FrogEnemy.class) {
            enemies.add(new FrogEnemy(this, spawnPositionX, spawnPositionY));
        } else if (type == MonkeyEnemy.class) {
            enemies.add(new MonkeyEnemy(this, spawnPositionX, spawnPositionY));
        }
    }

    public void log(String message) {
        if (MainGameClass.USE_PLAYSCREEN_LOG) {
            Gdx.app.log(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " @ timer " + String.valueOf(timer), message);
        }
    }

    /* ----- GETTERS AND SETTERS ------------------------------------------------------------------------------------ */

    /**
     * Returns the texture atlas for the player sprite
     * @return the player texture atlas
     */
    public TextureAtlas getPlayerTextureAtlas() {
        return playerTextureAtlas;
    }

    /**
     * Returns the texture atlas for the frog enemy sprite
     */
    public TextureAtlas getEnemyFrogTextureAtlas() {
        return enemyFrogTextureAtlas;
    }

    /**
     * Returns the texture atlas for the monkey enemy sprite
     */
    public TextureAtlas getEnemyMonkeyTextureAtlas() {
        return enemyMonkeyTextureAtlas;
    }


    /**
     * @return the game class
     */
    public MainGameClass getGame() {
        return game;
    }

    /**
     * @return the map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return the game progress
     */
    public GameProgress getGameProgress() {
        return gameProgress;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the statistics
     */
    public Statistics getStatistics() {
        return statistics;
    }

    public void setPlayerCurrentHealth(int newHealth) {
        // Save lost health to statistics
        statistics.setHealthLost(statistics.getHealthLost() + (player.getHealth() - newHealth));

        log("Player health change : New: " + newHealth + ", Old: " + player.getHealth() + ", Damage: " + (player.getHealth() - newHealth));
        player.setHealth(newHealth);

        if (player.getHealth() <= 0) {
            if(gameProgress.getPlayerLifes() > 1) {
                respawnPlayer(false);
            } else {
                game.exitApplication("Out of lifes");
            }
        }
    }

    public long getTimer() {
        return timer;
    }

    public Box2DWorldCreator getBox2DWorldCreator() {
        return box2DWorldCreator;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Enemy> getEnemiesCurrentlyInRightAttackRange() {
        return enemiesCurrentlyInRightAttackRange;
    }

    public ArrayList<Enemy> getEnemiesCurrentlyInLeftAttackRange() {
        return enemiesCurrentlyInLeftAttackRange;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
}
