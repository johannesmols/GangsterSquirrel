package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;
import itcom.gangstersquirrel.Statistics.Statistics;

/**
 * A subclass of the InteractiveTileObject class
 */
public class Item extends InteractiveTileObject {

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
