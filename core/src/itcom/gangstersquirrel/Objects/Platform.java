package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveTileObject class
 */
public class Platform extends InteractiveTileObject {

    public Platform(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, false);
        fixture.setUserData(this);
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Platform", "Collision");
    }

    @Override
    public void onPlayerEndContact() {

    }
}
