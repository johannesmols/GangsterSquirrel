package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import itcom.gangstersquirrel.MainGameClass;

/**
 * Shows a splashscreen at the start of the application for a defined amount of time
 */
public class SplashScreen implements Screen {

    private MainGameClass game;
    private Texture texture;
    private Image splashImage;
    private Stage stage;

    public SplashScreen(MainGameClass game) {
        this.game = game;
        stage = new Stage();
        texture = game.assetManager.get("sprites/splashscreen/splashscreen.png", Texture.class);
        splashImage = new Image(texture);
    }

    /**
     * Gets called when the application is shown for the first time
     */
    @Override
    public void show() {
        splashImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(3.0f), Actions.delay(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new PlayScreen(game));
            }
        })));
    }

    /**
     * Gets called once every frame and updates all visual components of the application
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    /**
     * Gets called when the application is resized
     */
    @Override
    public void resize(int width, int height) {

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
        texture.dispose();
    }
}
