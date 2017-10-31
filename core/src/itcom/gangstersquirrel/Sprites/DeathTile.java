package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class DeathTile extends InteractiveTileObject {

    public DeathTile(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        fixture.setUserData(this);
    }

    @Override
    public void onPlayerHit() {
        Gdx.app.log("Death Tile", "Collision");
    }
}
