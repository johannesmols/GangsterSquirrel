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
    private int[] level_1_collisionLayers = new int[] { 2, 3 , 4, 5 };
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
    public static boolean isPressingMoveLeft;
    public static boolean isPressingMoveRight;
    public static boolean isPressingJump;

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

        switch (MainGameClass.CURRENT_LEVEL) {
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
        switch (MainGameClass.CURRENT_LEVEL) {
            case 1:
                player = new Player(this, level_1_spawnPositionX, level_1_spawnPositionY);
                break;
            default:
                game.exitApplication("No current level defined, exiting application");
                break;
        }

        if (allWeapons != null && allWeapons.size() > 0) {
            // Replace this with whatever weapon the player should have at the start of the level later
            player.getWeapons().add(allWeapons.get(0));
        }

        player.setHealth(100);
        player.setJumpImpulseVelocity(4f);
        player.setWalkImpulseVelocity(0.1f);
        player.setClimbImpulseVelocity(1f);
        player.setMaxWalkVelocity(2f);
        player.setMaxClimbVelocity(1f);

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
        if (MainGameClass.DEBUG_PLAY_SOUNDS) {
            level_1_backgroundMusic.play();
        }
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

        // Jumping
        if (isPressingJump && player.body.getLinearVelocity().y == 0) {
            player.body.applyLinearImpulse(new Vector2(0, player.getJumpImpulseVelocity()), player.body.getWorldCenter(), true);
            jumpSound.play();

        }

        // Horizontal movement
        if (isPressingMoveRight && player.body.getLinearVelocity().x <= player.getMaxWalkVelocity()) {
            player.body.applyLinearImpulse(new Vector2(player.getWalkImpulseVelocity(), 0), player.body.getWorldCenter(), true);
        }
        if (isPressingMoveLeft && player.body.getLinearVelocity().x >= -player.getMaxWalkVelocity()) {
            player.body.applyLinearImpulse(new Vector2(-player.getWalkImpulseVelocity(), 0), player.body.getWorldCenter(), true);
        }
        //Escape
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            game.exitApplication("Fuck You");
        }
    }

    /**
     * Returns the texture atlas for the player sprite
     * @return the player texture atlas
     */
    public TextureAtlas getPlayerTextureAtlas() {
        return playerTextureAtlas;
    }

    //returns the texture atlast for the Frog Enemy sprite
    public TextureAtlas getEnemyFrogTextureAtlas() {
        return enemyFrogTextureAtlas;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
}
