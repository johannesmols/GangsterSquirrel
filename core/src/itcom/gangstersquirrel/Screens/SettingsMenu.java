package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Settings.Settings;
import itcom.gangstersquirrel.Tools.BitmapFontGenerator;

public class SettingsMenu extends MenuScreen {

    private Settings settings;

    private Label titleLabel;
    private TextButton backButton;

    private CheckBox fullscreenCheckBox;

    public SettingsMenu(MainGameClass game) {
        super(game);
        this.settings = new Settings();

        titleLabel = new Label("Settings", skin, "title");
        backButton = new TextButton("Back", skin, "default");

        fullscreenCheckBox = new CheckBox(" Fullscreen", skin, "default");
        fullscreenCheckBox.setChecked(settings.getFullscreen());

        layoutTable.add(titleLabel).top().center().expandX().colspan(3).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(fullscreenCheckBox).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(backButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        fullscreenCheckBox.addListener(fullscreenCheckBoxChangeListener);
        backButton.addListener(backButtonClickListener);
    }

    @Override
    public void renderExtended(float delta) {

    }

    private Label.LabelStyle changeLabelStyleFont(Label.LabelStyle original, String filePath, float densityIndependentPixels, Color color) {
        original.font = BitmapFontGenerator.generateFont(filePath, densityIndependentPixels, color);
        return original;
    }

    /* ----- EVENT LISTENER ----- */

    private ChangeListener fullscreenCheckBoxChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            settings.setFullscreen(fullscreenCheckBox.isChecked());
            game.changeGameResolution();
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
