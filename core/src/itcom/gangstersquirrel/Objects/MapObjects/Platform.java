package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveMapTileObject class
 */
public class Platform extends InteractiveMapTileObject {

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
