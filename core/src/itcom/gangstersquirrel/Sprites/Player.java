package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;

public class Player extends Sprite {

    public World world;
    public Body body;

    public Player(World world) {
        this.world = world;
        definePlayer();
    }

    public void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / MainGameClass.PPM, 64 / MainGameClass.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MainGameClass.PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

}
