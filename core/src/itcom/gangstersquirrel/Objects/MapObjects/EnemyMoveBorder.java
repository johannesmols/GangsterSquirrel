package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Objects.Player;
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
    public void onPlayerBeginContact(Player player) {

    }

    @Override
    public void onPlayerEndContact(Player player) {

    }

    @Override
    public void onEnemyBeginContact(Enemy enemy) {
        playScreen.log("Collision : Move border reached by enemy");
        enemy.setMovingLeftOrRight(!enemy.getIsMovingLeftOrRight());
    }

    @Override
    public void onEnemyEndContact(Enemy enemy) {

    }

    @Override
    public void createFilterMask() {
        Filter filter = new Filter();
        filter.categoryBits = MainGameClass.CATEGORY_ENEMY_MOVE_BORDER;
        filter.maskBits = MainGameClass.MASK_ENEMY_MOVE_BORDER;
        fixture.setFilterData(filter);
    }
}
