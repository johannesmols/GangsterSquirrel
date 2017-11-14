package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveMapTileObject class
 */
public class Finish extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public Finish(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        this.playScreen = screen;

        fixture.setUserData(this);
        createFilterMask();
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Finish", "Collision");
        playScreen.levelFinished();
    }

    @Override
    public void onPlayerEndContact() {

    }

    @Override
    public void createFilterMask() {
        Filter filter = new Filter();
        filter.categoryBits = MainGameClass.CATEGORY_FINISH;
        filter.maskBits = MainGameClass.MASK_FINISH;
        fixture.setFilterData(filter);
    }
}
