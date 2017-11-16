package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class Jumpable extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public Jumpable(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        playScreen = screen;

        fixture.setUserData(this);
        createFilterMask();
    }

    @Override
    public void onPlayerBeginContact() {
        // Player is on jumpable ground
        playScreen.getPlayer().setIsOnJumpableGround(true);
    }

    @Override
    public void onPlayerEndContact() {
        // Player leaves jumpable ground
        playScreen.getPlayer().setIsOnJumpableGround(false);
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
        filter.categoryBits = MainGameClass.CATEGORY_JUMPABLE;
        filter.maskBits = MainGameClass.MASK_JUMPABLE;
        fixture.setFilterData(filter);
    }
}
