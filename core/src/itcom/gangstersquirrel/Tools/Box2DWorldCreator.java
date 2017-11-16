package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Objects.MapObjects.*;
import itcom.gangstersquirrel.Screens.PlayScreen;

import java.util.ArrayList;

/**
 * Creates the Box2D world of a map
 */
public class Box2DWorldCreator {

    private ArrayList<Ground> groundObjects = new ArrayList<>();
    private ArrayList<DeathTile> deathTileObjects = new ArrayList<>();
    private ArrayList<WeaponPickup> weaponPickupObjects = new ArrayList<>();
    private ArrayList<Finish> finishObjects = new ArrayList<>();
    private ArrayList<Jumpable> jumpableObjects = new ArrayList<>();
    private ArrayList<EnemyMoveBorder> enemyMoveBorderObjects = new ArrayList<>();

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
                        groundObjects.add(new Ground(screen, rectangle));
                        break;
                    case "death":
                        deathTileObjects.add(new DeathTile(screen, rectangle));
                        break;
                    case "weapon":
                        weaponPickupObjects.add(new WeaponPickup(screen, rectangle));
                        break;
                    case "finish":
                        finishObjects.add(new Finish(screen, rectangle));
                        break;
                    case "jumpable":
                        jumpableObjects.add(new Jumpable(screen, rectangle));
                        break;
                    case "enemy_move_border":
                        enemyMoveBorderObjects.add(new EnemyMoveBorder(screen, rectangle));
                    default:
                        break;

                }
            }
        }
    }

    public ArrayList<Ground> getGroundObjects() {
        return groundObjects;
    }

    public ArrayList<DeathTile> getDeathTileObjects() {
        return deathTileObjects;
    }

    public ArrayList<WeaponPickup> getWeaponPickupObjects() {
        return weaponPickupObjects;
    }

    public ArrayList<Finish> getFinishObjects() {
        return finishObjects;
    }

    public ArrayList<Jumpable> getJumpableObjects() {
        return jumpableObjects;
    }

    public ArrayList<EnemyMoveBorder> getEnemyMoveBorderObjects() {
        return enemyMoveBorderObjects;
    }
}