package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import itcom.gangstersquirrel.MainGameClass;

public class MainMenu implements Screen {

    private MainGameClass game;

    private Skin skin;
    private Stage stage;
    private Window window;

    private Label gameTitleLabel;
    private TextButton playButton;
    private TextButton optionsButton;
    private TextButton statisticsButton;
    private TextButton exitButton;

    public MainMenu(MainGameClass game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/Flat_Earth_UI_Skin/flatearthui/flat-earth-ui.json"));

        ProgressBar.ProgressBarStyle progressBarStyle = skin.get("fancy", ProgressBar.ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("slider-fancy-knob").tint(skin.getColor("selection"));
        tiledDrawable.setMinWidth(0);
        progressBarStyle.knobBefore = tiledDrawable;

        Slider.SliderStyle sliderStyle = skin.get("fancy", Slider.SliderStyle.class);
        sliderStyle.knobBefore = tiledDrawable;

        Table layoutTable = new Table();
        layoutTable.top();
        layoutTable.setFillParent(true);
        layoutTable.pad(getPixelSizeFromDensityIndependentPixels(50));

        gameTitleLabel = new Label("Gangster Squirrel", skin, "title");
        playButton = new TextButton("Play", skin, "default");
        optionsButton = new TextButton("Options", skin, "default");
        statisticsButton = new TextButton("Statistics", skin, "default");
        exitButton = new TextButton("Exit", skin, "default");

        layoutTable.add(gameTitleLabel).top().center().expandX().colspan(3).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(playButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(optionsButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(statisticsButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(exitButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        playButton.addListener(playButtonClickListener);
        optionsButton.addListener(optionsButtonClickListener);
        statisticsButton.addListener(statisticsButtonClickListener);
        exitButton.addListener(exitButtonClickListener);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(244.0f / 255.0f, 200.0f / 255.0f, 117.0f / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();

        // Toggle debug
        stage.setDebugAll(MainGameClass.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        //window.setSize(MainGameClass.WIDTH, MainGameClass.HEIGHT);
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
        stage.dispose();
        skin.dispose();
    }

    private int getPixelSizeFromDensityIndependentPixels(float dip) {
        return (int) (dip * Gdx.graphics.getDensity());
    }

    /* ----- EVENT LISTENER ----- */

    private ClickListener playButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new PlayScreen(game));
        }
    };

    private ClickListener optionsButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
        }
    };

    private ClickListener statisticsButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
        }
    };

    private ClickListener exitButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.exitApplication("Exited from main menu");
        }
    };
}
