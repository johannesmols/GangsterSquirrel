package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * Abstract class for interactive tile objects in the game world
 */
public abstract class InteractiveMapTileObject {

    protected PlayScreen playScreen;
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveMapTileObject(PlayScreen screen, Rectangle bounds, boolean isSensor) {
        this.playScreen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        PolygonShape shape = new PolygonShape();
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MainGameClass.PPM, (bounds.getY() + bounds.getHeight() / 2) / MainGameClass.PPM);
        body = world.createBody(bodyDef);

        shape.setAsBox(bounds.getWidth() / 2 / MainGameClass.PPM, bounds.getHeight() / 2 / MainGameClass.PPM);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;
        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onPlayerBeginContact(Player player);
    public abstract void onPlayerEndContact(Player player);
    public abstract void onEnemyBeginContact(Enemy enemy);
    public abstract void onEnemyEndContact(Enemy enemy);

    /**
     * Change the appropriate filter mask
     */
    public abstract void createFilterMask();

    /**
     * Gets the cell of a graphical map layer
     * @param layerIndex the index of the map layer
     * @return the cell
     */
    protected TiledMapTileLayer.Cell getCell(int layerIndex) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);
        return layer.getCell(
                (int)(body.getPosition().x * MainGameClass.PPM / MainGameClass.TILE_PIXEL_SIZE),
                (int)(body.getPosition().y * MainGameClass.PPM / MainGameClass.TILE_PIXEL_SIZE)
        );
    }

    /**
     * This method returns the properties of an object in a collision layer by checking the player rectangle and object rectangle for an intersection
     * @param layerIndex the index of the layer in which to search for objects
     * @return the collided object
     */
    protected MapObject getCollidingMapObject(int layerIndex) {
        MapObjects mapObjects = map.getLayers().get(layerIndex).getObjects();

        for (MapObject mapObject : mapObjects) {
            MapProperties mapProperties = mapObject.getProperties();

            float width, height, x, y;
            Rectangle objectRectangle = new Rectangle();
            Rectangle playerRectangle = new Rectangle();

            if (mapProperties.containsKey("width") && mapProperties.containsKey("height") && mapProperties.containsKey("x") && mapProperties.containsKey("y")) {
                width = (float) mapProperties.get("width");
                height = (float) mapProperties.get("height");
                x = (float) mapProperties.get("x");
                y = (float) mapProperties.get("y");
                objectRectangle.set(x, y, width, height);
            }

            playerRectangle.set(
                    playScreen.getPlayer().getX() * MainGameClass.PPM,
                    playScreen.getPlayer().getY() * MainGameClass.PPM,
                    playScreen.getPlayer().getWidth() * MainGameClass.PPM,
                    playScreen.getPlayer().getHeight() * MainGameClass.PPM
            );

            // If the player rectangle and the object rectangle is colliding, return the object
            if (Intersector.overlaps(objectRectangle, playerRectangle)) {
                return mapObject;
            }
        }

        // If no colliding object was found in that layer
        return null;
    }
}