package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Settings.Settings;
import itcom.gangstersquirrel.Tools.BitmapFontGenerator;
import itcom.gangstersquirrel.Tools.ResolutionObject;

import java.awt.*;
import java.util.*;

/**
 * The settings menu of the game
 */
public class SettingsMenu extends MenuScreen {

    private Settings settings;
    private GameProgress gameProgress;

    private Label titleLabel;
    private TextButton cancelButton;
    private TextButton applyButton;
    private TextButton resetGameProgress;
    private CheckBox fullscreenCheckBox;
    private SelectBox resolutionSelectBox;
    private Label resolutionLabel;
    private Label userNameLabel;
    private TextField userNameTextField;
    private Label twitchChannelLabel;
    private TextField twitchChannelTextField;
    private Label twitchOAuthLabel;
    private TextField twitchOAuthTextField;

    private HashMap<String, ResolutionObject> items = new HashMap<>();

    private int screenWidth;
    private int screenHeight;

    /**
     * Sets up all widgets and defines the layout of the menu
     * @param game the main game class
     */
    public SettingsMenu(MainGameClass game) {
        super(game);
        this.settings = new Settings();
        this.gameProgress = new GameProgress();

        titleLabel = new Label("Settings", skin, "title");
        cancelButton = new TextButton("Cancel", skin, "default");
        applyButton = new TextButton("Apply", skin, "default");
        resetGameProgress = new TextButton("Reset Game Progress", skin, "default");
        resolutionLabel = new Label("Resolution: ", skin, "default");
        userNameLabel = new Label("Username: ", skin, "default");
        userNameTextField = new TextField("", skin, "default");
        twitchChannelLabel = new Label("Twitch Channel: ", skin, "default");
        twitchChannelTextField = new TextField("", skin, "default");
        twitchOAuthLabel = new Label("Twitch OAuth: ", skin, "default");
        twitchOAuthTextField = new TextField("", skin, "default");

        fullscreenCheckBox = new CheckBox(" Fullscreen", skin, "default");
        fullscreenCheckBox.setChecked(settings.getFullscreen());

        resolutionSelectBox = new SelectBox(skin);
        resolutionSelectBox.setItems(getItemsForResolutionList());
        setResolutionSelectionToCurrentResolution();

        resolutionLabel.setStyle(changeLabelStyleFont(resolutionLabel.getStyle(), "fonts/segoeui.ttf", 32, Color.WHITE));
        userNameLabel.setStyle(changeLabelStyleFont(userNameLabel.getStyle(), "fonts/segoeui.ttf", 32, Color.WHITE));
        twitchChannelLabel.setStyle(changeLabelStyleFont(twitchChannelLabel.getStyle(), "fonts/segoeui.ttf", 32, Color.WHITE));
        twitchOAuthLabel.setStyle(changeLabelStyleFont(twitchOAuthLabel.getStyle(), "fonts/segoeui.ttf", 32, Color.WHITE));

        userNameTextField.setText(settings.getPlayerName());
        twitchChannelTextField.setText(game.getTwitchThread().getTwitchCredentials().getChannel());
        twitchOAuthTextField.setText(game.getTwitchThread().getTwitchCredentials().getOauth());

        layoutTable.add(titleLabel).top().center().expandX().colspan(4).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(fullscreenCheckBox).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(resolutionLabel).center().left().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(resolutionSelectBox).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(userNameLabel).center().left().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(userNameTextField).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(twitchChannelLabel).center().left().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(twitchChannelTextField).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(twitchOAuthLabel).center().left().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(twitchOAuthTextField).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(resetGameProgress).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(cancelButton).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(applyButton).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        fullscreenCheckBox.addListener(fullscreenCheckBoxChangeListener);
        resolutionSelectBox.addListener(resolutionSelectBoxChangeListener);
        cancelButton.addListener(cancelButtonClickListener);
        applyButton.addListener(applyButtonClickListener);
        resetGameProgress.addListener(resetGameProgressButtonClickListener);
    }

    /**
     * An extended method from the render method of the super class to have the chance to add something specific to this class
     * @param delta the time between the current and the last frame
     */
    @Override
    public void renderExtended(float delta) {

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

    /**
     * Get a list of Strings that will be represented in the selection box for the possible resolutions
     * @return the list of Strings
     */
    private String[] getItemsForResolutionList() {

        ArrayList<ResolutionObject> resolutionItems = checkForPossibleResolutions();

        boolean currentResolutionInResolutionList = false;
        for (ResolutionObject resolution : resolutionItems) {
            if (resolution.getWidth() == Gdx.graphics.getWidth() && resolution.getHeight() == Gdx.graphics.getHeight()) {
                currentResolutionInResolutionList = true;
            }
        }

        for (ResolutionObject resolution : resolutionItems) {
            items.put(resolution.getWidth() + "x" + resolution.getHeight() + " (" + resolution.getAspectRatio() + ")", resolution);
        }

        if (!currentResolutionInResolutionList) {
            items.put("(" + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight() + ")", new ResolutionObject("unknown", Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        }

        String[] itemStrings = new String[items.size()];
        int cnt = 0;
        for (Map.Entry<String, ResolutionObject> resolutionObject : items.entrySet()) {
            itemStrings[cnt] = resolutionObject.getKey();
            ++cnt;
        }

        Arrays.sort(itemStrings);

        return itemStrings;
    }

    /**
     * Filters out the list of all resolutions depending on the screen size
     * @return the filtered resolutions list
     */
    private ArrayList<ResolutionObject> checkForPossibleResolutions() {

        ArrayList<ResolutionObject> possibleResolutions = new ArrayList<>();

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = gd.getDisplayMode().getWidth();
        screenHeight = gd.getDisplayMode().getHeight();

        ArrayList<ResolutionObject> allResolutions = game.getResolutions();
        for (ResolutionObject resolution : allResolutions) {
            if (resolution.getWidth() <= screenWidth && resolution.getHeight() <= screenHeight) {
                possibleResolutions.add(resolution);
            }
        }

        return possibleResolutions;
    }

    /**
     * Set the currently selected item in the list of resolutions to be the current resolution
     */
    private void setResolutionSelectionToCurrentResolution() {
        for (HashMap.Entry<String, ResolutionObject> item : items.entrySet()) {
            if (item.getValue().getWidth() == settings.getGameWidth() && item.getValue().getHeight() == settings.getGameHeight()) {
                try {
                    // To prevent the event listener from triggering
                    resolutionSelectBox.removeListener(resolutionSelectBoxChangeListener);
                    resolutionSelectBox.setSelected(item.getKey());
                    resolutionSelectBox.addListener(resolutionSelectBoxChangeListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* ----- EVENT LISTENER ----- */

    /**
     * Gets called when the fullscreen check box gets toggled
     */
    private ChangeListener fullscreenCheckBoxChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            settings.setFullscreen(fullscreenCheckBox.isChecked());

            // Set resolution to maximum when switching to fullscreen
            if (settings.getGameWidth() != screenWidth && settings.getGameHeight() != screenHeight) {
                settings.setGameWidth(screenWidth);
                settings.setGameHeight(screenHeight);

                String selected = (String) resolutionSelectBox.getSelected();
                if (items.containsKey(selected)) {
                    ResolutionObject selectedResolution = items.get(selected);
                    changeAspectRatioOfGameForResolution(selectedResolution);
                }

                setResolutionSelectionToCurrentResolution();
            }

            game.changeGameResolution();
        }
    };

    /**
     * Gets called when the selected resolution in the dropdown list is changed
     */
    private ChangeListener resolutionSelectBoxChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            String selected = (String) resolutionSelectBox.getSelected();
            if (items.containsKey(selected)) {
                ResolutionObject selectedResolution = items.get(selected);
                settings.setGameWidth(selectedResolution.getWidth());
                settings.setGameHeight(selectedResolution.getHeight());
                MainGameClass.WIDTH = selectedResolution.getWidth();
                MainGameClass.HEIGHT = selectedResolution.getHeight();

                changeAspectRatioOfGameForResolution(selectedResolution);

                // If the selected resolution is lower, don't allow fullscreen
                if (selectedResolution.getWidth() <= screenWidth && selectedResolution.getHeight() <= screenHeight && screenWidth > 0 && screenHeight > 0) {
                    settings.setFullscreen(false);

                    // To prevent the event handler from triggering
                    fullscreenCheckBox.removeListener(fullscreenCheckBoxChangeListener);
                    fullscreenCheckBox.setChecked(false);
                    fullscreenCheckBox.addListener(fullscreenCheckBoxChangeListener);
                }

                game.changeGameResolution();
            }
        }
    };

    /**
     * Change the aspect ratio values in the main game class according to the new aspect ratio
     * @param resolution the resolution object containing the aspect ratio
     */
    private void changeAspectRatioOfGameForResolution(ResolutionObject resolution) {

        switch (resolution.getAspectRatio()) {
            case "4:3":
                MainGameClass.GAME_WORLD_WIDTH = MainGameClass.ZOOM * 16;
                MainGameClass.GAME_WORLD_HEIGHT = MainGameClass.ZOOM * 12;
                break;
            case "5:3":
                MainGameClass.GAME_WORLD_WIDTH = MainGameClass.ZOOM * 15;
                MainGameClass.GAME_WORLD_HEIGHT = MainGameClass.ZOOM * 9;
                break;
            case "5:4":
                MainGameClass.GAME_WORLD_WIDTH = MainGameClass.ZOOM * 15;
                MainGameClass.GAME_WORLD_HEIGHT = MainGameClass.ZOOM * 12;
                break;
            case "16:9":
                MainGameClass.GAME_WORLD_WIDTH = MainGameClass.ZOOM * 16;
                MainGameClass.GAME_WORLD_HEIGHT = MainGameClass.ZOOM * 9;
                break;
            case "16:10":
                MainGameClass.GAME_WORLD_WIDTH = MainGameClass.ZOOM * 16;
                MainGameClass.GAME_WORLD_HEIGHT = MainGameClass.ZOOM * 10;
                break;
            case "17:9":
                MainGameClass.GAME_WORLD_WIDTH = MainGameClass.ZOOM * 17;
                MainGameClass.GAME_WORLD_HEIGHT = MainGameClass.ZOOM * 9;
                break;
            default:
                break;
        }
    }

    /**
     * Gets called when the cancel button is clicked
     */
    private ClickListener cancelButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new MainMenu(game));
        }
    };

    /**
     * Gets called when the apply button is clicked
     */
    private ClickListener applyButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);

            // Username
            if (!userNameTextField.getText().trim().isEmpty()) {
                settings.setPlayerName(userNameTextField.getText().trim());
            }

            // Twitch channel
            if (!twitchChannelTextField.getText().trim().isEmpty()) {
                game.getTwitchThread().getTwitch().setTwitchChannel(twitchChannelTextField.getText().trim());
            }

            // Twitch OAuth
            if (!twitchOAuthTextField.getText().trim().isEmpty()) {
                game.getTwitchThread().getTwitch().setOAuth(twitchOAuthTextField.getText().trim());
            }

            game.newTwitchThread();
            game.setScreen(new MainMenu(game));
        }
    };

    /**
     * Gets called when the reset button is clicked
     */
    private ClickListener resetGameProgressButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            gameProgress.resetToDefault();
        }
    };
}
