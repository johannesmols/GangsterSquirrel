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
import com.badlogic.gdx.utils.Align;
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
    private Label secondsPlayedValueLabel;
    private Label jumpsMadeLabel;
    private Label jumpsMadeValueLabel;
    private Label levelsFinishedLabel;
    private Label levelsFinishedValueLabel;
    private Label deathsLabel;
    private Label deathsValueLabel;
    private Label healthLostLabel;
    private Label healthLostValueLabel;
    private Label damageInflictedLabel;
    private Label damageInflictedValueLabel;
    private Label enemiesKilledLabel;
    private Label enemiesKilledValueLabel;
    private Label itemsCollectedLabel;
    private Label itemsCollectedValueLabel;

    private final String secondsPlayedText = "Seconds played:";
    private final String jumpsMadeText = "Jumps made:";
    private final String levelsFinishedText = "Levels finished:";
    private final String deathsText = "Deaths:";
    private final String healthLostText = "Health lost:";
    private final String damageInflictedText = "Damage inflicted:";
    private final String enemiesKilledText = "Enemies killed:";
    private final String itemsCollectedText = "Items collected:";

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
        secondsPlayedLabel = new Label(secondsPlayedText, skin, "default");
        secondsPlayedValueLabel = new Label("error reading from file", skin, "default");
        jumpsMadeLabel = new Label(jumpsMadeText, skin, "default");
        jumpsMadeValueLabel = new Label("error reading from file", skin, "default");
        levelsFinishedLabel =  new Label(levelsFinishedText, skin, "default");
        levelsFinishedValueLabel = new Label("error reading from file", skin, "default");
        deathsLabel =  new Label(deathsText, skin, "default");
        deathsValueLabel = new Label("error reading from file", skin, "default");
        healthLostLabel =  new Label(healthLostText, skin, "default");
        healthLostValueLabel = new Label("error reading from file", skin, "default");
        damageInflictedLabel =  new Label(damageInflictedText, skin, "default");
        damageInflictedValueLabel = new Label("error reading from file", skin, "default");
        enemiesKilledLabel =  new Label(enemiesKilledText, skin, "default");
        enemiesKilledValueLabel = new Label("error reading from file", skin, "default");
        itemsCollectedLabel =  new Label(itemsCollectedText, skin, "default");
        itemsCollectedValueLabel = new Label("error reading from file", skin, "default");

        secondsPlayedLabel.setStyle(changeLabelStyleFont(secondsPlayedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        secondsPlayedLabel.setAlignment(Align.right);
        secondsPlayedValueLabel.setStyle(changeLabelStyleFont(secondsPlayedValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        jumpsMadeLabel.setStyle(changeLabelStyleFont(jumpsMadeLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        jumpsMadeLabel.setAlignment(Align.right);
        jumpsMadeValueLabel.setStyle(changeLabelStyleFont(jumpsMadeValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        levelsFinishedLabel.setStyle(changeLabelStyleFont(levelsFinishedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        levelsFinishedLabel.setAlignment(Align.right);
        levelsFinishedValueLabel.setStyle(changeLabelStyleFont(levelsFinishedValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        deathsLabel.setStyle(changeLabelStyleFont(deathsLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        deathsLabel.setAlignment(Align.right);
        deathsValueLabel.setStyle(changeLabelStyleFont(deathsValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        healthLostLabel.setStyle(changeLabelStyleFont(healthLostLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        healthLostLabel.setAlignment(Align.right);
        healthLostValueLabel.setStyle(changeLabelStyleFont(healthLostValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        damageInflictedLabel.setStyle(changeLabelStyleFont(damageInflictedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        damageInflictedLabel.setAlignment(Align.right);
        damageInflictedValueLabel.setStyle(changeLabelStyleFont(damageInflictedValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        enemiesKilledLabel.setStyle(changeLabelStyleFont(enemiesKilledLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        enemiesKilledLabel.setAlignment(Align.right);
        enemiesKilledValueLabel.setStyle(changeLabelStyleFont(enemiesKilledValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        itemsCollectedLabel.setStyle(changeLabelStyleFont(itemsCollectedLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));
        itemsCollectedLabel.setAlignment(Align.right);
        itemsCollectedValueLabel.setStyle(changeLabelStyleFont(itemsCollectedValueLabel.getStyle(), "fonts/segoeui.ttf", 48, Color.BLACK));

        layoutTable.add(titleLabel).top().center().expandX().colspan(4).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(secondsPlayedLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(secondsPlayedValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(jumpsMadeLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(jumpsMadeValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(levelsFinishedLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(levelsFinishedValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(deathsLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(deathsValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(healthLostLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(healthLostValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(damageInflictedLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(damageInflictedValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(enemiesKilledLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(enemiesKilledValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(itemsCollectedLabel).top().center().growX().expandX().uniformX().padRight(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(itemsCollectedValueLabel).top().center().growX().expandX().uniformX().padLeft(10f).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(resetToDefaultButton).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(backButton).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
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

        secondsPlayedValueLabel.setText(String.valueOf(statistics.getSecondsPlayed()));
        jumpsMadeValueLabel.setText(String.valueOf(statistics.getJumpsMade()));
        levelsFinishedValueLabel.setText(String.valueOf(statistics.getLevelsFinished()));
        deathsValueLabel.setText(String.valueOf(statistics.getDiedThisManyTimes()));
        healthLostValueLabel.setText(String.valueOf(statistics.getHealthLost()));
        damageInflictedValueLabel.setText(String.valueOf(statistics.getDamageInflicted()));
        enemiesKilledValueLabel.setText(String.valueOf(statistics.getEnemiesKilled()));
        itemsCollectedValueLabel.setText(String.valueOf(statistics.getItemsCollected()));
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
