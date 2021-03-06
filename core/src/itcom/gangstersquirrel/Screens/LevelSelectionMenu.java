package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.MainGameClass;

/**
 * A menu to select which level the user wants to play
 */
public class LevelSelectionMenu extends MenuScreen {

    private GameProgress gameProgress;

    private Label titleLabel;
    private TextButton level1Button;
    private TextButton level2Button;
    private TextButton level3Button;
    private TextButton backButton;

    /**
     * Sets up all widgets and defines the layout of the menu
     * @param game the main game class
     */
    public LevelSelectionMenu(MainGameClass game) {
        super(game);

        gameProgress = new GameProgress();

        titleLabel = new Label("Level Selection", skin, "title");
        level1Button = new TextButton("Level 1", skin, "default");
        level2Button = new TextButton("Level 2", skin, "default");
        level3Button = new TextButton("Level 3", skin, "default");
        backButton = new TextButton("Back", skin, "default");

        switch (gameProgress.getUnlockedLevels()) {
            case 1:
                level2Button.setDisabled(true);
                level3Button.setDisabled(true);
                level2Button.setText(level2Button.getText() + " (locked)");
                level3Button.setText(level3Button.getText() + " (locked)");
                break;
            case 2:
                level3Button.setDisabled(true);
                level3Button.setText(level3Button.getText() + " (locked)");
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

    /**
     * An extended method from the render method of the super class to have the chance to add something specific to this class
     * @param delta the time between the current and the last frame
     */
    @Override
    public void renderExtended(float delta) {

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
