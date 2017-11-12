package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveTileObject class
 */
public class Finish extends InteractiveTileObject {

    private PlayScreen playScreen;

    public Finish(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        this.playScreen = screen;
        fixture.setUserData(this);
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Finish", "Collision");
        playScreen.levelFinished();
    }

    @Override
    public void onPlayerEndContact() {

    }
}
