package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.MainGameClass;

public class MainMenuScreen implements Screen {

    private MainGameClass game;

    private Skin skin;
    private Stage stage;

    private PlayScreen playScreen = new PlayScreen(game);

    private Button background = new Button();
    private Button playButton = new Button();
    private Button optionsButton = new Button();
    private Button exitButton = new Button();

    private Camera camera;

    public MainMenuScreen(final MainGameClass game) {
        this.game = game;

        camera = new OrthographicCamera(MainGameClass.WIDTH, MainGameClass.HEIGHT);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("mainmenu.json"), new TextureAtlas("mainmenu.pack"));

        background = new Button(skin, "background");
        playButton = new Button(skin, "playButton");
        optionsButton = new Button(skin, "optionsButton");
        exitButton = new Button(skin, "exitButton");

        background.setPosition(0, 0);
        playButton.setPosition(MainGameClass.WIDTH / 2 - playButton.getWidth() / 2, 10);
        optionsButton.setPosition(MainGameClass.WIDTH / 2 - optionsButton.getWidth() / 2, 200);
        exitButton.setPosition(MainGameClass.WIDTH / 2 - optionsButton.getWidth() / 2, 300);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //super.clicked(event, x, y);
                game.setScreen(playScreen);
            }
        });

        stage.addActor(background);
        stage.addActor(playButton);
        stage.addActor(optionsButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        stage.draw();
        game.batch.end();
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

    }
}
