package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveTileObject class to test one specific case of interactive tile objects
 */
public class TestCoin extends InteractiveTileObject {

    public TestCoin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
    }
}
