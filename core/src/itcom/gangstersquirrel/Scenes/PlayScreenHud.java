package itcom.gangstersquirrel.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;
import itcom.gangstersquirrel.Tools.BitmapFontGenerator;

/**
 * The Heads Up Display of the Play Screen
 */
public class PlayScreenHud {

    private PlayScreen playScreen;

    public Stage stage;
    private Viewport viewport;

    private TextureAtlas skinAtlas;
    private Skin skin;

    private Label timerLabel;
    private ProgressBar healthBar;

    public PlayScreenHud(PlayScreen playScreen) {
        this.playScreen = playScreen;

        viewport = new FitViewport(MainGameClass.WIDTH, MainGameClass.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, playScreen.getGame().batch);
        stage.setDebugAll(MainGameClass.DEBUG);

        skinAtlas = new TextureAtlas("skins/skin.atlas");
        skin = new Skin(Gdx.files.internal("skins/skin.json"));
        skin.addRegions(skinAtlas);

        Table layoutTable = new Table();
        layoutTable.top();
        layoutTable.setFillParent(true);
        layoutTable.pad(getPixelSizeFromDensityIndependentPixels(32));

        timerLabel = new Label(String.format("%04d", playScreen.getGameProgress().getCurrentTime()), skin, "default");
        timerLabel.setStyle(changeLabelStyleFont(timerLabel.getStyle(), "fonts/PressStart2P.ttf", 64));

        healthBar = new ProgressBar(
                0f,
                playScreen.getGameProgress().getPlayerMaxHealth(),
                1f,
                false,
                skin,
                "default-horizontal"
        );

        layoutTable.add(healthBar).expandX().width(getPixelSizeFromDensityIndependentPixels(750)).left().top();
        layoutTable.add(timerLabel).expandX().right().top();
        layoutTable.row();

        stage.addActor(layoutTable);
    }

    public void update(float deltaTime) {
        timerLabel.setText(String.format("%04d", playScreen.getTimer()));
        healthBar.setValue(playScreen.getPlayer().getHealth());

        // Toggle debug
        stage.setDebugAll(MainGameClass.DEBUG);
    }

    private int getPixelSizeFromDensityIndependentPixels(float dip) {
        return (int) (dip * Gdx.graphics.getDensity());
    }

    private Label.LabelStyle changeLabelStyleFont(Label.LabelStyle original, String filePath, float densityIndependentPixels) {
        original.font = BitmapFontGenerator.generateFont(filePath, densityIndependentPixels);
        return original;
    }
}
