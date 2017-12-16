package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Statistics.Statistics;
import itcom.gangstersquirrel.Tools.BitmapFontGenerator;

public class StatisticsMenu implements Screen {

    private MainGameClass game;

    private Skin skin;
    private Stage stage;

    private Statistics statistics;

    private Label titleLabel;
    private TextButton backButton;
    private TextButton resetToDefaultButton;
    private Label secondsPlayedLabel;
    private Label jumpsMadeLabel;
    private Label levelsFinishedLabel;
    private Label deathsLabel;
    private Label healthLostLabel;
    private Label damageInflictedLabel;
    private Label enemiesKilledLabel;
    private Label itemsCollectedLabel;

    private final String secondsPlayedText = "Seconds played: ";
    private final String jumpsMadeText = "Jumps made: ";
    private final String levelsFinishedText = "Levels finished: ";
    private final String deathsText = "Deaths: ";
    private final String healthLostText = "Health lost: ";
    private final String damageInflictedText = "Damage inflicted: ";
    private final String enemiesKilledText = "Enemies killed: ";
    private final String itemsCollectedText = "Items collected: ";

    public StatisticsMenu(MainGameClass game) {
        this.game = game;
        this.statistics = new Statistics();
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

        titleLabel = new Label("Statistics", skin, "title");
        backButton = new TextButton("Back", skin, "default");
        resetToDefaultButton = new TextButton("Reset", skin, "default");
        secondsPlayedLabel = new Label(secondsPlayedText + " error reading file", skin, "default");
        jumpsMadeLabel = new Label(jumpsMadeText + " error reading file", skin, "default");
        levelsFinishedLabel =  new Label(levelsFinishedText + " error reading file", skin, "default");
        deathsLabel =  new Label(deathsText + " error reading file", skin, "default");
        healthLostLabel =  new Label(healthLostText + " error reading file", skin, "default");
        damageInflictedLabel =  new Label(damageInflictedText + " error reading file", skin, "default");
        enemiesKilledLabel =  new Label(enemiesKilledText + " error reading file", skin, "default");
        itemsCollectedLabel =  new Label(itemsCollectedText + " error reading file", skin, "default");

        secondsPlayedLabel.setStyle(changeLabelStyleFont(secondsPlayedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        jumpsMadeLabel.setStyle(changeLabelStyleFont(jumpsMadeLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        levelsFinishedLabel.setStyle(changeLabelStyleFont(levelsFinishedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        deathsLabel.setStyle(changeLabelStyleFont(deathsLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        healthLostLabel.setStyle(changeLabelStyleFont(healthLostLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        damageInflictedLabel.setStyle(changeLabelStyleFont(damageInflictedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        enemiesKilledLabel.setStyle(changeLabelStyleFont(enemiesKilledLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        itemsCollectedLabel.setStyle(changeLabelStyleFont(itemsCollectedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        layoutTable.add(titleLabel).top().center().expandX().colspan(3).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(secondsPlayedLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(jumpsMadeLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(levelsFinishedLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(deathsLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(healthLostLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(damageInflictedLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(enemiesKilledLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(itemsCollectedLabel).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(resetToDefaultButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(backButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        backButton.addListener(backButtonClickListener);
        resetToDefaultButton.addListener(resetButtonClickListener);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(244.0f / 255.0f, 200.0f / 255.0f, 117.0f / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();

        // Toggle debug
        stage.setDebugAll(MainGameClass.DEBUG);

        secondsPlayedLabel.setText(secondsPlayedText + String.valueOf(statistics.getSecondsPlayed()));
        jumpsMadeLabel.setText(jumpsMadeText + String.valueOf(statistics.getJumpsMade()));
        levelsFinishedLabel.setText(levelsFinishedText + String.valueOf(statistics.getLevelsFinished()));
        deathsLabel.setText(deathsText + String.valueOf(statistics.getDiedThisManyTimes()));
        healthLostLabel.setText(healthLostText + String.valueOf(statistics.getHealthLost()));
        damageInflictedLabel.setText(damageInflictedText + String.valueOf(statistics.getDamageInflicted()));
        enemiesKilledLabel.setText(enemiesKilledText + String.valueOf(statistics.getEnemiesKilled()));
        itemsCollectedLabel.setText(itemsCollectedText + String.valueOf(statistics.getItemsCollected()));
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

    private Label.LabelStyle changeLabelStyleFont(Label.LabelStyle original, String filePath, float densityIndependentPixels, Color color) {
        original.font = BitmapFontGenerator.generateFont(filePath, densityIndependentPixels, color);
        return original;
    }

    /* ----- EVENT LISTENER ----- */

    private ClickListener backButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new MainMenu(game));
        }
    };

    private ClickListener resetButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            statistics.resetToDefault();
        }
    };
}
