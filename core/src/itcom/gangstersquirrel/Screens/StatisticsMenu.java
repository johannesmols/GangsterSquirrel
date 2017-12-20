package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Statistics.Statistics;
import itcom.gangstersquirrel.Tools.BitmapFontGenerator;

/**
 * The statistics menu of the game
 */
public class StatisticsMenu extends MenuScreen {

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

    /**
     * Sets up all widgets and defines the layout of the menu
     * @param game the main game class
     */
    public StatisticsMenu(MainGameClass game, boolean comingFromTheLastLevel) {
        super(game);
        this.statistics = new Statistics();

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

        // Show pop up if the user finished the game
        if (comingFromTheLastLevel) {
            Skin defaultSkin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));

            Dialog dialog = new Dialog("Game finished", defaultSkin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            dialog.text("Congratulations, you finished the game!\nWe prepared some statistics in the following screen.\nThanks for playing!");
            dialog.button("Okay", true); //sends "true" as the result
            dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
            dialog.show(stage);
        }
    }

    /**
     * An extended method from the render method of the super class to have the chance to add something specific to this class
     * @param delta the time between the current and the last frame
     */
    @Override
    public void renderExtended(float delta) {
        secondsPlayedValueLabel.setText(String.valueOf(statistics.getSecondsPlayed()));
        jumpsMadeValueLabel.setText(String.valueOf(statistics.getJumpsMade()));
        levelsFinishedValueLabel.setText(String.valueOf(statistics.getLevelsFinished()));
        deathsValueLabel.setText(String.valueOf(statistics.getDiedThisManyTimes()));
        healthLostValueLabel.setText(String.valueOf(statistics.getHealthLost()));
        damageInflictedValueLabel.setText(String.valueOf(statistics.getDamageInflicted()));
        enemiesKilledValueLabel.setText(String.valueOf(statistics.getEnemiesKilled()));
        itemsCollectedValueLabel.setText(String.valueOf(statistics.getItemsCollected()));
    }

    /**
     * Changes the font style of a label to use a true type font that gets converted to a Bitmap font in an appropriate scale
     * @param original the original label style
     * @param filePath the file path of the true type font
     * @param densityIndependentPixels the density independent pixel size of the font
     * @return the new style with the scaled bitmap font
     */
    private Label.LabelStyle changeLabelStyleFont(Label.LabelStyle original, String filePath, float densityIndependentPixels, Color color) {
        original.font = BitmapFontGenerator.generateFont(filePath, densityIndependentPixels, color);
        return original;
    }

    /* ----- EVENT LISTENER ----- */

    /**
     * Gets called when the back button is clicked
     */
    private ClickListener backButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new MainMenu(game));
        }
    };

    /**
     * Gets called when the reset button is clicked
     */
    private ClickListener resetButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            statistics.resetToDefault();
        }
    };
}
