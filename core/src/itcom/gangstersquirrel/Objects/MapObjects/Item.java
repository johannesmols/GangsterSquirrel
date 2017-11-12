package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveMapTileObject class
 */
public class Item extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public Item(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        fixture.setUserData(this);

        playScreen = screen;
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Item", "Collision");

        // Save collected item to statistics
        //Statistics statistics = new Statistics();
        playScreen.getStatistics().setItemsCollected(playScreen.statistics.getItemsCollected() + 1);
    }

    @Override
    public void onPlayerEndContact() {

    }
}
