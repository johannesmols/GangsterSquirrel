package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class EnemyMoveBorder extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public EnemyMoveBorder(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        this.playScreen = screen;

        fixture.setUserData(this);
        createFilterMask();
    }

    @Override
    public void onPlayerBeginContact() {

    }

    @Override
    public void onPlayerEndContact() {

    }

    @Override
    public void onEnemyBeginContact() {
        Gdx.app.log("Collision", "Move Border reached by enemy");
    }

    @Override
    public void onEnemyEndContact() {

    }

    @Override
    public void createFilterMask() {
        Filter filter = new Filter();
        filter.categoryBits = MainGameClass.CATEGORY_ENEMY_MOVE_BORDER;
        filter.maskBits = MainGameClass.MASK_ENEMY_MOVE_BORDER;
        fixture.setFilterData(filter);
    }
}
