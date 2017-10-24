package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import itcom.gangstersquirrel.Input.GameplayInputProcessor;
import itcom.gangstersquirrel.KeyBindings.KeyBindings;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Sprites.Player;
import itcom.gangstersquirrel.Tools.Box2DWorldCreator;

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

    // Box2D variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    // Player variables
    private Player player;
    public static boolean isMovingLeft;
    public static boolean isMovingRight;
    public static boolean isJumping;

    // Keymap variables
    public static KeyBindings keyBindings;

    // Input processors
    private InputProcessor gamePlayInputProcessor;

    // Audio variables
    // Sounds = kept in memory (shouldn't be longer than 10 seconds)
    // Music = streamed from file (can be memory intensive to keep in memory)
    private Sound dropSoundReplaceLater;
    private Music rainMusicReplaceLater;

    // Gameplay variables
    private final float JUMP_IMPULSE_VELOCITY = 4f;
    private final float WALK_IMPULSE_VELOCITY = 0.1f;
    private final float MAXIMAL_X_VELOCITY = 2f;

    /**
     * Set up all important things, can be considered as the create() method like in the MainGameClass
     * @param game The main game class
     */
    public PlayScreen(MainGameClass game) {
        this.game = game;

        // Set up camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGameClass.GAME_WORLD_WIDTH / MainGameClass.PPM, MainGameClass.GAME_WORLD_HEIGHT / MainGameClass.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Load the first map from Tiles
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/test_level/test_level.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGameClass.PPM);

        // Box2D physics setup
        world = new World(new Vector2(0, - MainGameClass.GRAVITY), true); // gravity, doSleep
        box2DDebugRenderer = new Box2DDebugRenderer();

        // Set up the collision boxes for the ground and obstacle layers
        new Box2DWorldCreator(world, map, new int[] { 2, 3, 4});

        // Player set-up
        player = new Player(world);

        // Keybinding setup
        keyBindings = new KeyBindings();

        // Register input processors
        gamePlayInputProcessor = new GameplayInputProcessor();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gamePlayInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //Load the template sound effect and the template background music and play immediately
        dropSoundReplaceLater = Gdx.audio.newSound(Gdx.files.internal("audio/waterdrop_replace_later.wav"));
        rainMusicReplaceLater = Gdx.audio.newMusic(Gdx.files.internal("audio/rain_replace_later.mp3"));
        rainMusicReplaceLater.setLooping(true);
        if (MainGameClass.DEBUG_PLAY_SOUNDS) {
            rainMusicReplaceLater.play();
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

        // Sets the coordinate system for rendering
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // Draw specific textures in here with the help of OpenGL
        game.batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        rainMusicReplaceLater.dispose();
        dropSoundReplaceLater.dispose();
    }

    private void update(float deltaTime) {
        this.handleInput(deltaTime);

        // timeStep = amount of time the world simulates (https://github.com/libgdx/libgdx/wiki/Box2d)
        // velocity and position iterations = defines the precision of the calculations. Needs more calculation power, if higher. 6 and 2 are the recommended values
        world.step(1 / (float)MainGameClass.FPS, 6, 2);

        // Follow player with camera
        camera.position.x = player.body.getPosition().x;
        camera.position.y = player.body.getPosition().y;

        // Update the camera's position
        camera.update();
        // Renderer will draw only what the camera can see
        renderer.setView(camera);
    }

    private void handleInput(float deltaTime) {

        // Jumping
        if (isJumping && player.body.getLinearVelocity().y == 0) {
            player.body.applyLinearImpulse(new Vector2(0, JUMP_IMPULSE_VELOCITY), player.body.getWorldCenter(), true);
        }

        // Horizontal movement
        if (isMovingRight && player.body.getLinearVelocity().x <= MAXIMAL_X_VELOCITY) {
            player.body.applyLinearImpulse(new Vector2(WALK_IMPULSE_VELOCITY, 0), player.body.getWorldCenter(), true);
        }
        if (isMovingLeft && player.body.getLinearVelocity().x >= -MAXIMAL_X_VELOCITY) {
            player.body.applyLinearImpulse(new Vector2(-WALK_IMPULSE_VELOCITY, 0), player.body.getWorldCenter(), true);
        }
    }
}
