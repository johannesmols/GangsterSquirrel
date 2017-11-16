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
public class Platform extends InteractiveMapTileObject {

    public Platform(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, false);
        fixture.setUserData(this);

        fixture.setUserData(this);
        createFilterMask();
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Platform", "Collision");
    }

    @Override
    public void onPlayerEndContact() {

    }

    @Override
    public void onEnemyBeginContact() {

    }

    @Override
    public void onEnemyEndContact() {

    }

    @Override
    public void createFilterMask() {
        Filter filter = new Filter();
        filter.categoryBits = MainGameClass.CATEGORY_PLATFORM;
        filter.maskBits = MainGameClass.MASK_PLATFORM;
        fixture.setFilterData(filter);
    }
}
