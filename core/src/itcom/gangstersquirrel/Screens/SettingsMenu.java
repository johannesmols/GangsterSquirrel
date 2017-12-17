package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    private CheckBox fullscreenCheckBox;
    private SelectBox resolutionSelectBox;

    private HashMap<String, ResolutionObject> items = new HashMap<>();

    public SettingsMenu(MainGameClass game) {
        super(game);
        this.settings = new Settings();

        titleLabel = new Label("Settings", skin, "title");
        backButton = new TextButton("Back", skin, "default");

        fullscreenCheckBox = new CheckBox(" Fullscreen", skin, "default");
        fullscreenCheckBox.setChecked(settings.getFullscreen());

        resolutionSelectBox = new SelectBox(skin);
        resolutionSelectBox.setItems(getItemsForResolutionList());

        layoutTable.add(titleLabel).top().center().expandX().colspan(3).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(fullscreenCheckBox).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(resolutionSelectBox).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(backButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        fullscreenCheckBox.addListener(fullscreenCheckBoxChangeListener);
        resolutionSelectBox.addListener(resolutionSelectBoxChangeListener);
        backButton.addListener(backButtonClickListener);
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
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        ArrayList<ResolutionObject> allResolutions = game.getResolutions();
        for (ResolutionObject resolution : allResolutions) {
            if (resolution.getWidth() <= width && resolution.getHeight() <= height) {
                possibleResolutions.add(resolution);
            }
        }

        return possibleResolutions;
    }

    /* ----- EVENT LISTENER ----- */

    private ChangeListener fullscreenCheckBoxChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            settings.setFullscreen(fullscreenCheckBox.isChecked());
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
}
