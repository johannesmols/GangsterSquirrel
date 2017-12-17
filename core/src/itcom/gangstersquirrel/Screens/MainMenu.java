package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.MainGameClass;


public class MainMenu extends MenuScreen {

    private Label gameTitleLabel;
    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton statisticsButton;
    private TextButton exitButton;

    public MainMenu(MainGameClass game) {
        super(game);
    }

    @Override
    public void show() {
        gameTitleLabel = new Label("Gangster Squirrel", skin, "title");
        playButton = new TextButton("Play", skin, "default");
        settingsButton = new TextButton("Settings", skin, "default");
        statisticsButton = new TextButton("Statistics", skin, "default");
        exitButton = new TextButton("Exit", skin, "default");

        layoutTable.add(gameTitleLabel).top().center().expandX().colspan(3).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(playButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(settingsButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
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
        settingsButton.addListener(settingsButtonClickListener);
        statisticsButton.addListener(statisticsButtonClickListener);
        exitButton.addListener(exitButtonClickListener);
    }

    @Override
    public void renderExtended(float delta) {

    }

    /* ----- EVENT LISTENER ----- */

    private ClickListener playButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new LevelSelectionMenu(game));
        }
    };

    private ClickListener settingsButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
        }
    };

    private ClickListener statisticsButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new StatisticsMenu(game));
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
