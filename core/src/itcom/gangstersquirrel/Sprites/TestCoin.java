package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A subclass of the InteractiveTileObject class to test one specific case of interactive tile objects
 */
public class TestCoin extends InteractiveTileObject {

    public TestCoin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
