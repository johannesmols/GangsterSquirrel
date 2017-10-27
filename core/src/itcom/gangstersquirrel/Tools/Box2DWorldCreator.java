package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Sprites.TestCoin;

/**
 * Creates the Box2D world of a map
 */
public class Box2DWorldCreator {

    /**
     * Sets up the collision boxes of the map objects
     * @param world the game world
     * @param map the tiled map
     * @param layers all the layers in the map that should get collision boxes
     */
    public Box2DWorldCreator(World world, TiledMap map, int[] layers) {
        BodyDef bodyDefinition = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDefinition = new FixtureDef();
        Body body;

        for (int layer : layers) {
            for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                // Test coin
                if (layer == 4) {
                    new TestCoin(world, map, rectangle);
                    continue;
                }

                bodyDefinition.type = BodyDef.BodyType.StaticBody;
                bodyDefinition.position.set((rectangle.getX() + rectangle.getWidth() / 2) / MainGameClass.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / MainGameClass.PPM);

                body = world.createBody(bodyDefinition);

                shape.setAsBox(rectangle.getWidth() / 2 / MainGameClass.PPM, rectangle.getHeight() / 2 / MainGameClass.PPM);
                fixtureDefinition.shape = shape;
                body.createFixture(fixtureDefinition);
            }
        }
    }
}