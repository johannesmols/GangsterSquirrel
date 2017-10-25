package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import itcom.gangstersquirrel.MainGameClass;

public class SplashScreen implements Screen {

    private MainGameClass game;
    private Texture texture = new Texture(Gdx.files.internal("sprites/splashscreen/placeholder_splashscreen.png"));
    private Image splashImage = new Image(texture);
    private Stage stage = new Stage();

    public SplashScreen(MainGameClass game) {
        this.game = game;
    }

    @Override
    public void show() {
        splashImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(3.0f), Actions.delay(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        texture.dispose();
    }
}
