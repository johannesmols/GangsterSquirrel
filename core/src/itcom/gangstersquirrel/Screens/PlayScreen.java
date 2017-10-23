package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import itcom.gangstersquirrel.MainGameClass;

public class PlayScreen implements Screen {

    MainGameClass game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(MainGameClass game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGameClass.WIDTH / MainGameClass.PPM, MainGameClass.HEIGHT / MainGameClass.PPM, camera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGameClass.PPM);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() {

    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        camera.update();
        renderer.setView(camera);
    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.isTouched()) {
            camera.position.x += 100 / MainGameClass.PPM * deltaTime;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
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

    }
}
