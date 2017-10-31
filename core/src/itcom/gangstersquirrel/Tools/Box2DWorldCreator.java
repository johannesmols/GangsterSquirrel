package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;
import itcom.gangstersquirrel.Sprites.TestCoin;

/**
 * Creates the Box2D world of a map
 */
public class Box2DWorldCreator {

    /**
     * Sets up the collision boxes of the map objects
     * @param layers all the layers in the map that should get collision boxes
     */
    public Box2DWorldCreator(PlayScreen screen, int[] layers) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bodyDefinition = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDefinition = new FixtureDef();
        Body body;

        for (int layer : layers) {
            for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                switch (layer) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        new TestCoin(screen, rectangle);
                        break;
                    default:
                        break;
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