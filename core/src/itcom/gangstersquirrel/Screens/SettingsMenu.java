package itcom.gangstersquirrel.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Settings.Settings;

public class SettingsMenu extends MenuScreen {

    private Settings settings;

    private Label titleLabel;
    private TextButton backButton;

    public SettingsMenu(MainGameClass game) {
        super(game);
        this.settings = new Settings();

        titleLabel = new Label("Settings", skin, "title");
        backButton = new TextButton("Back", skin, "default");

        layoutTable.add(titleLabel).top().center().expandX().colspan(3).spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(backButton).top().center().growX().expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.add(new Actor()).expandX().spaceBottom(getPixelSizeFromDensityIndependentPixels(25f));
        layoutTable.row();

        stage.addActor(layoutTable);

        backButton.addListener(backButtonClickListener);
    }

    @Override
    public void renderExtended(float delta) {

    }

    /* ----- EVENT LISTENER ----- */

    private ClickListener backButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new MainMenu(game));
        }
    };
}
