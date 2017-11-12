package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Objects.MapObjects.*;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * Creates the Box2D world of a map
 */
public class Box2DWorldCreator {

    /**
     * Sets up the collision boxes of the map objects
     */
    public Box2DWorldCreator(PlayScreen screen) {
        TiledMap map = screen.getMap();
        MapLayers mapLayers = map.getLayers();

        // Loop through every layer in the map
        for (MapLayer mapLayer : mapLayers) {
            // Loop through every rectangular shape in this layer
            for (MapObject mapObject : mapLayer.getObjects().getByType(RectangleMapObject.class)) {

                // The rectangular collision box
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

                // Create object depending on the type of the shape
                switch (mapLayer.getName()) {
                    case "background":
                        // Only graphics
                        break;
                    case "graphics":
                        // Only graphics
                        break;
                    case "ground":
                        new Ground(screen, rectangle);
                        break;
                    case "obstacles":
                        new Platform(screen, rectangle);
                        break;
                    case "death":
                        new DeathTile(screen, rectangle);
                        break;
                    case "items":
                        new Item(screen, rectangle);
                        break;
                    case "finish":
                        new Finish(screen, rectangle);
                        break;
                    case "jumpable":
                        new Jumpable(screen, rectangle);
                        break;
                    default:
                        break;

                }
            }
        }
    }
}