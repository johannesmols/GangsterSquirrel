package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Settings.Settings;

/**
 * The main menu of the game
 */
public class MainMenu extends MenuScreen {

    private Label gameTitleLabel;
    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton statisticsButton;
    private TextButton leaderbordButton;
    private TextButton exitButton;

    /**
     * Sets up all widgets and defines the layout of the menu
     * @param game the main game class
     */
    public MainMenu(MainGameClass game) {
        super(game);

        gameTitleLabel = new Label("Gangster Squirrel", skin, "title");
        playButton = new TextButton("Play", skin, "default");
        settingsButton = new TextButton("Settings", skin, "default");
        statisticsButton = new TextButton("Statistics", skin, "default");
        leaderbordButton = new TextButton("Leaderboard", skin, "default");
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
        layoutTable.add(leaderbordButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
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
        leaderbordButton.addListener(leaderboardButtonClickListener);
        exitButton.addListener(exitButtonClickListener);

        setUsernameToComputerName();
    }

    /**
     * An extended method from the render method of the super class to have the chance to add something specific to this class
     * @param delta the time between the current and the last frame
     */
    @Override
    public void renderExtended(float delta) {

    }

    /**
     * Changes the user name to be the computer name if the name hasn't been changed yet
     */
    private void setUsernameToComputerName() {
        Settings settings = new Settings();
        if (settings.getPlayerName().equals("player")) {
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                try {
                    settings.setPlayerName(System.getProperty("user.name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
            game.setScreen(new SettingsMenu(game));
        }
    };

    private ClickListener statisticsButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new StatisticsMenu(game));
        }
    };

    private ClickListener leaderboardButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            Gdx.net.openURI("http://resources.ludvig.xyz/gangstersquirrel/");
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
