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
import itcom.gangstersquirrel.Sprites.Player;
import itcom.gangstersquirrel.Tools.Box2DWorldCreator;
import itcom.gangstersquirrel.Tools.WorldContactListener;

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

    // Level 1 Configuration
    private int[] level_1_collisionLayers = new int[] { 2, 3 , 4, 5, 6 };
    private int level_1_spawnPositionX = 2;
    private int level_1_spawnPositionY = 3;

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

    // Game Progress
    private GameProgress gameProgress = new GameProgress();

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
    Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("audio/jump.mp3"));
    // Music = streamed from file (can be memory intensive to keep in memory)
    private Music level_1_backgroundMusic;

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

        // Load the first map from Tiles
        mapLoader = new TmxMapLoader();

        switch (gameProgress.getCurrentLevel()) {
            case 1:
                map = mapLoader.load("maps/level_1/level_1.tmx");
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
        new Box2DWorldCreator(this, level_1_collisionLayers); // int array = object layers of the map that need collision boxes

        // Player set-up
        switch (gameProgress.getCurrentLevel()) {
            case 1:
                player = new Player(this, level_1_spawnPositionX, level_1_spawnPositionY);
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
        if (MainGameClass.PLAY_SOUNDS) {
            level_1_backgroundMusic.play();
        }

        // Set up timer with previous time or new time for the level
        timer = gameProgress.getCurrentTime();
    }

    /**
     * Will be executed once every frame
     * @param delta deltaTime is the time passed between the last and the current frame in seconds. VERY IMPORTANT!
     */
    @Override
    public void render(float delta) {
        this.update(delta);

        // Clear the previous frame
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        // Render the Box2D Debug Lines
        if (MainGameClass.DEBUG) {
            box2DDebugRenderer.render(world, camera.combined);
        }

        // Update the player sprite
        player.update(delta);

        // Sets the coordinate system for rendering
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // Draw specific textures in here with the help of OpenGL
        player.draw(game.batch);
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

        // Update the camera's position
        camera.update();
        // Renderer will draw only what the camera can see
        renderer.setView(camera);
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
            game.exitApplication();
        }

        // Jumping
        if (isPressingJump && player.body.getLinearVelocity().y == 0) {
            player.body.applyLinearImpulse(new Vector2(0, player.getJumpImpulseVelocity()), player.body.getWorldCenter(), true);

            if (MainGameClass.PLAY_SOUNDS) {
                jumpSound.play();
            }

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
     * Resets the player to the start of the level and resets all attributes
     */
    public void respawnPlayer(boolean levelFinished) {
        Gdx.app.log("Game over", "Player died, respawning now...");
        level_1_backgroundMusic.stop();

        // Only when the player died, when the level is finished, it should reset the timer
        if (levelFinished) {
            gameProgress.setCurrentTime(0);
        } else {
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
        if (gameProgress.getPlayerHighscoreTimes()[gameProgress.getCurrentLevel() - 1] > 0) {
            // If the new time for this level is faster, replace the old time with the new one
            if (timer < gameProgress.getPlayerHighscoreTimes()[gameProgress.getCurrentLevel() - 1]) {
                long[] tmp = gameProgress.getPlayerHighscoreTimes();
                tmp[gameProgress.getCurrentLevel() - 1] = timer;
                gameProgress.setPlayerHighscoreTimes(tmp);
            }
        } else {
            long[] tmp = gameProgress.getPlayerHighscoreTimes();
            tmp[gameProgress.getCurrentLevel() - 1] = timer;
            gameProgress.setPlayerHighscoreTimes(tmp);
        }

        if (gameProgress.getCurrentLevel() < MainGameClass.NUMBER_OF_LEVELS) {
            gameProgress.setCurrentLevel(gameProgress.getCurrentLevel() + 1);
        } else {
            Gdx.app.log("Game finished", "No more levels to play");
        }

        respawnPlayer(true);
    }

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
}
