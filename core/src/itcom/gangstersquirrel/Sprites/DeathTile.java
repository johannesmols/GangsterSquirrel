package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveTileObject class
 */
public class DeathTile extends InteractiveTileObject {

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
