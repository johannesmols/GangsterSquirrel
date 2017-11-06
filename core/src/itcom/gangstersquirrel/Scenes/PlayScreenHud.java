package itcom.gangstersquirrel.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
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

    Label timerLabel;
    ProgressBar healthBar;

    public PlayScreenHud(PlayScreen playScreen) {
        this.playScreen = playScreen;

        viewport = new FitViewport(MainGameClass.WIDTH, MainGameClass.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, playScreen.getGame().batch);

        stage.setDebugAll(MainGameClass.DEBUG);

        Table layoutTable = new Table();
        layoutTable.top();
        layoutTable.setFillParent(true);
        layoutTable.pad(getPixelSizeFromDensityIndependentPixels(32));

        timerLabel = new Label(
                String.format("%04d", playScreen.getGameProgress().getCurrentTime()),
                new Label.LabelStyle(BitmapFontGenerator.generateFont("fonts/PressStart2P.ttf", 64),
                Color.WHITE)
        );

        healthBar = new ProgressBar(
                0f,
                playScreen.getGameProgress().getPlayerMaxHealth(),
                1f,
                false,
                new ProgressBar.ProgressBarStyle()
        );

        //layoutTable.add(healthBar).expandX().left().top();
        layoutTable.add(timerLabel).expandX().right().top();
        layoutTable.row();

        stage.addActor(layoutTable);
    }

    public void update(float deltaTime) {
        timerLabel.setText(String.format("%04d", playScreen.getTimer()));

        // Toggle debug
        stage.setDebugAll(MainGameClass.DEBUG);
    }

    private int getPixelSizeFromDensityIndependentPixels(float dip) {
        return (int) (dip * Gdx.graphics.getDensity());
    }
}
