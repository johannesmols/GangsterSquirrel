package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;
import itcom.gangstersquirrel.Sprites.*;

/**
 * Creates the Box2D world of a map
 */
public class Box2DWorldCreator {

    /**
     * Sets up the collision boxes of the map objects
     * @param layers all the layers in the map that should get collision boxes
     */
    public Box2DWorldCreator(PlayScreen screen, int[] layers) {
        TiledMap map = screen.getMap();

        for (int layer : layers) {
            for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                switch (layer) {
                    case 0:
                        // only graphics
                        break;
                    case 1:
                        // only graphics
                        break;
                    case 2:
                        // Ground
                        new Ground(screen, rectangle);
                        break;
                    case 3:
                        // Platform
                        new Platform(screen, rectangle);
                        break;
                    case 4:
                        // Death tile (spikes or fell out of map)
                        new DeathTile(screen, rectangle);
                        break;
                    case 5:
                        // Item
                        new Item(screen, rectangle);
                        break;
                    case 6:
                        // Finish / Goal
                        new Finish(screen, rectangle);
                    default:
                        break;
                }
            }
        }
    }
}