package itcom.gangstersquirrel.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

    private Skin skin;

    private Label timerLabel;
    private ProgressBar healthBar;
    private ImageTextButton lifesImageTextButton;
    private ImageTextButton weaponStrengthImageTextButton;
    private ImageTextButton twitchImageTextButton;

    public PlayScreenHud(PlayScreen playScreen) {
        this.playScreen = playScreen;

        viewport = new FitViewport(MainGameClass.WIDTH, MainGameClass.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, playScreen.getGame().batch);
        stage.setDebugAll(MainGameClass.DEBUG);

        skin = new Skin(Gdx.files.internal("skins/hud/hud.json"));

        Table layoutTable = new Table();
        layoutTable.top();
        layoutTable.setFillParent(true);
        layoutTable.pad(getPixelSizeFromDensityIndependentPixels(50));

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

        lifesImageTextButton = new ImageTextButton(String.valueOf(playScreen.getGameProgress().getPlayerLifes()), skin, "lifes");
        lifesImageTextButton.setStyle(changeImageTextButtonStyle(lifesImageTextButton.getStyle(), "fonts/PressStart2P.ttf", 48));

        weaponStrengthImageTextButton = new ImageTextButton(String.valueOf(playScreen.getGameProgress().getPlayerWeaponList().get(playScreen.getGameProgress().getCurrentlyEquippedWeapon()).getStrength()), skin, "weapon_strength");
        weaponStrengthImageTextButton.setStyle(changeImageTextButtonStyle(weaponStrengthImageTextButton.getStyle(), "fonts/PressStart2P.ttf", 48));

        twitchImageTextButton = new ImageTextButton(String.format("%01d", 0), skin, "twitch");
        twitchImageTextButton.setStyle(changeImageTextButtonStyle(twitchImageTextButton.getStyle(), "fonts/PressStart2P.ttf", 48));

        layoutTable.add(healthBar).expandX().growX().left().top().spaceBottom(25);
        layoutTable.add(new Actor()).expandX().fillX().center().top().spaceBottom(25);
        layoutTable.add(timerLabel).expandX().right().top().spaceBottom(25);
        layoutTable.row();
        layoutTable.add(lifesImageTextButton).expandX().left().top().spaceBottom(10);
        layoutTable.row();
        layoutTable.add(weaponStrengthImageTextButton).expandX().left().top().spaceBottom(10);
        layoutTable.row();
        layoutTable.add(twitchImageTextButton).expandX().left().top().spaceBottom(10);

        stage.addActor(layoutTable);
    }

    public void update(float deltaTime) {
        timerLabel.setText(String.format("%04d", playScreen.getTimer()));
        healthBar.setValue(playScreen.getPlayer().getHealth());
        lifesImageTextButton.setText(String.valueOf(playScreen.getGameProgress().getPlayerLifes()));
        weaponStrengthImageTextButton.setText(String.valueOf(playScreen.getGameProgress().getPlayerWeaponList().get(playScreen.getGameProgress().getCurrentlyEquippedWeapon()).getStrength()));
        twitchImageTextButton.setText(playScreen.getGame().twitchEffectActive ? String.valueOf(Math.round(playScreen.getGame().maximumEffectTimeInSeconds - playScreen.getGame().effectStateTime)) : "0");

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

    private ImageTextButton.ImageTextButtonStyle changeImageTextButtonStyle(ImageTextButton.ImageTextButtonStyle original, String filePath, float densityIndependentPixels) {
        original.font = BitmapFontGenerator.generateFont(filePath, densityIndependentPixels);
        return original;
    }
}
