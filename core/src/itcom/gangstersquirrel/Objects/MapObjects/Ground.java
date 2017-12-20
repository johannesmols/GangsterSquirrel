package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Objects.Player;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * A subclass of the InteractiveMapTileObject class
 */
public class Ground extends InteractiveMapTileObject {

    public Ground(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, false);

        fixture.setUserData(this);
        createFilterMask();
    }

    @Override
    public void onPlayerBeginContact(Player player) {
        playScreen.log("Ground : Collision with player");
    }

    @Override
    public void onPlayerEndContact(Player player) {

    }

    @Override
    public void onEnemyBeginContact(Enemy enemy) {

    }

    @Override
    public void onEnemyEndContact(Enemy enemy) {

    }

    /**
     * Creates and sets the collision filter mask and category of this object
     */
    @Override
    public void createFilterMask() {
        Filter filter = new Filter();
        filter.categoryBits = MainGameClass.CATEGORY_GROUND;
        filter.maskBits = MainGameClass.MASK_GROUND;
        fixture.setFilterData(filter);
    }
}
