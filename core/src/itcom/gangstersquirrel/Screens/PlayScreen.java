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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.Input.GameplayInputProcessor;
import itcom.gangstersquirrel.Items.WeaponObject;
import itcom.gangstersquirrel.Items.WeaponList;
import itcom.gangstersquirrel.KeyBindings.KeyBindings;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Scenes.PlayScreenHud;
import itcom.gangstersquirrel.Sprites.Enemy;
import itcom.gangstersquirrel.Sprites.FrogEnemy;
import itcom.gangstersquirrel.Sprites.Player;
import itcom.gangstersquirrel.Statistics.Statistics;
import itcom.gangstersquirrel.Tools.Box2DWorldCreator;
import itcom.gangstersquirrel.Tools.WorldContactListener;

import java.util.ArrayList;
import java.util.List;

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
    private int level_1_spawnPositionX = 2;
    private int level_1_spawnPositionY = 20;

    // Level 2 Configuration
    private int level_2_spawnPositionX = 2;
    private int level_2_spawnPositionY = 3;

    // Box2D variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private WorldContactListener worldContactListener;

    // Texture variables
    private TextureAtlas playerTextureAtlas;
    private TextureAtlas enemyFrogTextureAtlas;

    // Player variables
    private Player player;
    public static boolean isPressingExit;
    public static boolean isPressingMoveLeft;
    public static boolean isPressingMoveRight;
    public static boolean isPressingJump;

    // Enemy variables
    private ArrayList<Enemy> enemies = new ArrayList<>();

    // Game Progress and Statistics
    public GameProgress gameProgress = new GameProgress();
    public Statistics statistics = new Statistics();

    // Gameplay variables
    private int playerCurrentHealth = gameProgress.getPlayerMaxHealth();

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
    private Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("audio/jump.mp3"));
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
        playerTextureAtlas = new TextureAtlas("sprites/player/squirrel.txt");
        enemyFrogTextureAtlas = new TextureAtlas("sprites/enemies/frog.txt");

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
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        // Set up the collision boxes for the ground and obstacle layers
        new Box2DWorldCreator(this); // int array = object layers of the map that need collision boxes

        // Player set-up
        switch (gameProgress.getCurrentLevel()) {
            case 1:
                player = new Player(this, level_1_spawnPositionX, level_1_spawnPositionY);

                // Add enemies to this level
                enemies.add(new FrogEnemy(this, 3, 10));
                enemies.add(new FrogEnemy(this, 5, 10));
                enemies.add(new FrogEnemy(this, 7, 10));
                break;
            case 2:
                player = new Player(this, level_2_spawnPositionX, level_2_spawnPositionY);
                break;
            default:
                game.exitApplication("No current level defined, exiting application");
                break;
        }

        // Equip player with his weapons
        for (WeaponObject weapon : gameProgress.getPlayerWeaponList()) {
            player.getWeapons().add(weapon);
        }

        player.setHealth(gameProgress.getPlayerMaxHealth());
        player.setJumpImpulseVelocity(gameProgress.getPlayerJumpImpulseVelocity());
        player.setWalkImpulseVelocity(gameProgress.getPlayerWalkImpulseVelocity());
        player.setClimbImpulseVelocity(gameProgress.getPlayerClimbImpulseVelocity());
        player.setMaxWalkVelocity(gameProgress.getPlayerMaxWalkVelocity());
        player.setMaxClimbVelocity(gameProgress.getPlayerMaxClimbVelocity());

        // Player texture looks in left direction by default, levels go to the right, flip at the start
        player.setFlip(true, false);

        // Keybinding setup
        keyBindings = new KeyBindings();

        // Register input processors
        gamePlayInputProcessor = new GameplayInputProcessor();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gamePlayInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //Load the background music, set looping to true and play immediately
        level_1_backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/level_1_music.mp3"));
        level_1_backgroundMusic.setLooping(true);
        level_2_backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/level_2_music.mp3"));
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

            Gdx.app.log("Timer", String.valueOf(timer));
        }

        // timeStep = amount of time the world simulates (https://github.com/libgdx/libgdx/wiki/Box2d)
        // velocity and position iterations = defines the precision of the calculations. Needs more calculation power, if higher. 6 and 2 are the recommended values
        world.step(1 / (float) MainGameClass.FPS, 6, 2);

        // Follow player with camera
        camera.position.x = player.body.getPosition().x;
        camera.position.y = player.body.getPosition().y;

        // Flip texture depending on the moving direction of the player
        // Don't do anything when the velocity is zero, leave it flipped or unflipped
        if (player.body.getLinearVelocity().x > 0) {
            player.setFlip(true, false);
        } else if (player.body.getLinearVelocity().x < 0) {
            player.setFlip(false, false);
        }

        // Update the HUD
        hud.update(deltaTime);

        // Update the camera's position
        camera.update();
        // Renderer will draw only what the camera can see
        renderer.setView(camera);
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

        // Exit application
        if(isPressingExit) {
            // Save current time
            statistics.setSecondsPlayed(statistics.getSecondsPlayed() + timer);

            game.exitApplication();
        }

        // Jumping
        if (isPressingJump && player.body.getLinearVelocity().y == 0) {
            player.body.applyLinearImpulse(new Vector2(0, player.getJumpImpulseVelocity()), player.body.getWorldCenter(), true);

            if (MainGameClass.PLAY_SOUNDS) {
                jumpSound.play();
            }

            // Save jump to statistics
            statistics.setJumpsMade(statistics.getJumpsMade() + 1);

        }

        // Horizontal movement
        if (isPressingMoveRight && player.body.getLinearVelocity().x <= player.getMaxWalkVelocity()) {
            player.body.applyLinearImpulse(new Vector2(player.getWalkImpulseVelocity(), 0), player.body.getWorldCenter(), true);
        }
        if (isPressingMoveLeft && player.body.getLinearVelocity().x >= -player.getMaxWalkVelocity()) {
            player.body.applyLinearImpulse(new Vector2(-player.getWalkImpulseVelocity(), 0), player.body.getWorldCenter(), true);
        }
    }

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
        Gdx.app.log("Game over", "Player died, respawning now...");
        level_1_backgroundMusic.stop();
        level_2_backgroundMusic.stop();

        // Save current timer time to the statistics
        statistics.setSecondsPlayed(statistics.getSecondsPlayed() + timer);

        // Only when the level is finished, it should reset the timer
        if (levelFinished) {
            gameProgress.setCurrentTime(0);
        } else {
            // If the player died, add one to the death counter in the statistics
            statistics.setDiedThisManyTimes(statistics.getDiedThisManyTimes() + 1);

            gameProgress.setCurrentTime(timer);
        }

        game.setScreen(new PlayScreen(game));
    }

    /**
     * Current level finished, save time, increase level and load the next level
     */
    public void levelFinished() {
        Gdx.app.log("Level finished", "Saving and loading next level...");

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

        if (gameProgress.getCurrentLevel() < MainGameClass.NUMBER_OF_LEVELS) {
            gameProgress.setCurrentLevel(gameProgress.getCurrentLevel() + 1);
        } else {
            Gdx.app.log("Game finished", "No more levels to play");
        }

        // Save finished level to statistics
        statistics.setLevelsFinished(statistics.getLevelsFinished() + 1);

        respawnPlayer(true);
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

    public int getPlayerCurrentHealth() {
        return playerCurrentHealth;
    }

    public void setPlayerCurrentHealth(int newHealth) {
        // Save lost health to statistics
        statistics.setHealthLost(statistics.getHealthLost() + (playerCurrentHealth - newHealth));

        Gdx.app.log("Player health change", "New: " + newHealth + ", Old: " + playerCurrentHealth + ", Damage: " + (playerCurrentHealth - newHealth));
        playerCurrentHealth = newHealth;

        if (playerCurrentHealth <= 0) {
            respawnPlayer(false);
        }
    }

    public long getTimer() {
        return timer;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
}
