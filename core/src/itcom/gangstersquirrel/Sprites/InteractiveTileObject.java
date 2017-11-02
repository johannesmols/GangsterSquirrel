package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * Abstract class for interactive tile objects in the game world
 */
public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, Rectangle bounds, boolean isSensor) {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MainGameClass.PPM, (bounds.getY() + bounds.getHeight() / 2) / MainGameClass.PPM);
        body = world.createBody(bodyDef);

        shape.setAsBox(bounds.getWidth() / 2 / MainGameClass.PPM, bounds.getHeight() / 2 / MainGameClass.PPM);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;
        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onPlayerHit();
}
