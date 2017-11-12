package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveMapTileObject class
 */
public class DeathTile extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public DeathTile(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        this.playScreen = screen;
        fixture.setUserData(this);
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Death Tile", "Collision");

        playScreen.setPlayerCurrentHealth(-1);
       // playScreen.respawnPlayer(false); // false = level not finished, don't reset timer
    }

    @Override
    public void onPlayerEndContact() {

    }
}
