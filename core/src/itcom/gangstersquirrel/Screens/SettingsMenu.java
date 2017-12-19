package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Settings.Settings;
import itcom.gangstersquirrel.Tools.BitmapFontGenerator;
import itcom.gangstersquirrel.Tools.ResolutionObject;

import java.awt.*;
import java.util.*;

public class SettingsMenu extends MenuScreen {

    private Settings settings;

    private Label titleLabel;
    private TextButton backButton;
    private TextButton applyButton;
    private CheckBox fullscreenCheckBox;
    private SelectBox resolutionSelectBox;
    private Label userNameLabel;
    private TextField userNameTextField;

    private HashMap<String, ResolutionObject> items = new HashMap<>();

    private int screenWidth;
    private int screenHeight;

    public SettingsMenu(MainGameClass game) {
        super(game);
        this.settings = new Settings();

        titleLabel = new Label("Settings", skin, "title");
        backButton = new TextButton("Back", skin, "default");
        applyButton = new TextButton("Apply", skin, "default");
        userNameLabel = new Label("Username: ", skin, "default");
        userNameTextField = new TextField("", skin, "default");

        fullscreenCheckBox = new CheckBox(" Fullscreen", skin, "default");
        fullscreenCheckBox.setChecked(settings.getFullscreen());

        resolutionSelectBox = new SelectBox(skin);
        resolutionSelectBox.setItems(getItemsForResolutionList());
        setResolutionSelectionToCurrentResolution();

        userNameLabel.setStyle(changeLabelStyleFont(userNameLabel.getStyle(), "fonts/segoeui.ttf", 32, Color.WHITE));
        userNameTextField.setText(settings.getPlayerName());

        layoutTable.add(titleLabel).top().center().expandX().colspan(4).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(fullscreenCheckBox).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(resolutionSelectBox).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(userNameLabel).center().left().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(userNameTextField).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(backButton).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(applyButton).top().center().growX().expandX().colspan(2).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        fullscreenCheckBox.addListener(fullscreenCheckBoxChangeListener);
        resolutionSelectBox.addListener(resolutionSelectBoxChangeListener);
        backButton.addListener(backButtonClickListener);
        applyButton.addListener(applyButtonClickListener);
    }

    @Override
    public void renderExtended(float delta) {

    }

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

    private ChangeListener fullscreenCheckBoxChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            settings.setFullscreen(fullscreenCheckBox.isChecked());

            // Set resolution to maximum when switching to fullscreen
            if (settings.getGameWidth() != screenWidth && settings.getGameHeight() != screenHeight) {
                settings.setGameWidth(screenWidth);
                settings.setGameHeight(screenHeight);

                setResolutionSelectionToCurrentResolution();
            }

            game.changeGameResolution();
        }
    };

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

                switch (selectedResolution.getAspectRatio()) {
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

    private ClickListener backButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new MainMenu(game));
        }
    };

    private ClickListener applyButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);

            // Username
            if (!userNameTextField.getText().trim().isEmpty()) {
                settings.setPlayerName(userNameTextField.getText().trim());
            }

            game.setScreen(new MainMenu(game));
        }
    };
}
