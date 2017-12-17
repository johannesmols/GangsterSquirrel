package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.MainGameClass;

public class LevelSelectionMenu implements Screen {

    private MainGameClass game;

    private Skin skin;
    private Stage stage;

    private GameProgress gameProgress;

    private Label titleLabel;
    private TextButton level1Button;
    private TextButton level2Button;
    private TextButton level3Button;
    private TextButton backButton;

    public LevelSelectionMenu(MainGameClass game) {
        this.game = game;
        gameProgress = new GameProgress();
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

        titleLabel = new Label("Level Selection", skin, "title");
        level1Button = new TextButton("Level 1", skin, "default");
        level2Button = new TextButton("Level 2", skin, "default");
        level3Button = new TextButton("Level 3", skin, "default");
        backButton = new TextButton("Back", skin, "default");

        // TODO: Make disabled buttons actually look disabled

        switch (gameProgress.getUnlockedLevels()) {
            case 1:
                level2Button.setDisabled(true);
                level3Button.setDisabled(true);
                break;
            case 2:
                level3Button.setDisabled(true);
                break;
            case 3:
                break;
            default:
                break;
        }

        layoutTable.add(titleLabel).top().center().expandX().colspan(3).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(level1Button).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(level2Button).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(75f));
        layoutTable.add(level3Button).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(75f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(75f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(backButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        level1Button.addListener(level1ButtonClickListener);
        level2Button.addListener(level2ButtonClickListener);
        level3Button.addListener(level3ButtonClickListener);
        backButton.addListener(backButtonClickListener);
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

    private ClickListener level1ButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);

            if (!level1Button.isDisabled()) {
                new GameProgress().setCurrentLevel(1);
                game.setScreen(new PlayScreen(game));
            }
        }
    };

    private ClickListener level2ButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);

            if (!level2Button.isDisabled()) {
                new GameProgress().setCurrentLevel(2);
                game.setScreen(new PlayScreen(game));
            }
        }
    };

    private ClickListener level3ButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);

            if (!level3Button.isDisabled()) {
                new GameProgress().setCurrentLevel(3);
                game.setScreen(new PlayScreen(game));
            }
        }
    };

    private ClickListener backButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new MainMenu(game));
        }
    };
}
