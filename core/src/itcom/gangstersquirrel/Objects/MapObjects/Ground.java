package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveMapTileObject class
 */
public class Ground extends InteractiveMapTileObject {

    public Ground(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, false);
        fixture.setUserData(this);
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Ground", "Collision");
    }

    @Override
    public void onPlayerEndContact() {

    }
}
