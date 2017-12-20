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
public class DeathTile extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public DeathTile(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        this.playScreen = screen;

        fixture.setUserData(this);
        createFilterMask();
    }

    @Override
    public void onPlayerBeginContact(Player player) {
        playScreen.log("Death Tile : Collision with player");

        playScreen.setPlayerCurrentHealth(-1);
    }

    @Override
    public void onPlayerEndContact(Player player) {

    }

    @Override
    public void onEnemyBeginContact(Enemy enemy) {
        playScreen.log("Death Tile : Collision with enemy");

        enemy.setHealth(-1);
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
        filter.categoryBits = MainGameClass.CATEGORY_DEATHTILE;
        filter.maskBits = MainGameClass.MASK_DEATHTILE;
        fixture.setFilterData(filter);
    }
}
