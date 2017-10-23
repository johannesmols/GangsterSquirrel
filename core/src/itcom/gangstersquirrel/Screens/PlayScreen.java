package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import itcom.gangstersquirrel.MainGameClass;

public class PlayScreen implements Screen {

    // Main class of the project
    final MainGameClass game;

    // Camera variables
    private OrthographicCamera camera;
    private Viewport viewport;

    // Map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Audio variables
    // Sounds = kept in memory (shouldn't be longer than 10 seconds)
    // Music = streamed from file (can be memory intensive to keep in memory)
    private Sound dropSoundReplaceLater;
    private Music rainMusicReplaceLater;

    public PlayScreen(MainGameClass game) {
        this.game = game;

        // Create a new game camera and set the viewport, divide by the pixels per meter constant for scaling
        camera = new OrthographicCamera();
        //unnecessary -> camera.setToOrtho(false, MainGameClass.WIDTH, MainGameClass.HEIGHT);
        viewport = new FitViewport(MainGameClass.WIDTH / MainGameClass.PPM, MainGameClass.HEIGHT / MainGameClass.PPM, camera);

        // Load the first map from Tiles
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/level_1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGameClass.PPM);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        //Load the template sound effect and the template background music and play immediately
        dropSoundReplaceLater = Gdx.audio.newSound(Gdx.files.internal("audio/waterdrop_replace_later.wav"));
        rainMusicReplaceLater = Gdx.audio.newMusic(Gdx.files.internal("audio/rain_replace_later.mp3"));
        rainMusicReplaceLater.setLooping(true);
        rainMusicReplaceLater.play();
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        camera.update();
        renderer.setView(camera);
    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.justTouched()) {
            // Play template sound effect
            dropSoundReplaceLater.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= 100 / MainGameClass.PPM * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += 100 / MainGameClass.PPM * deltaTime;
        }
    }

    @Override
    public void show() {

    }

    /**
     * Will be executed once every frame
     * @param delta deltaTime is the time passed between the last and the current frame in seconds. VERY IMPORTANT!
     */
    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        // Sets the coordinate system for rendering
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // Draw specific textures in here with the help of OpenGL
        game.batch.end();
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
        rainMusicReplaceLater.dispose();
        dropSoundReplaceLater.dispose();
    }
}
