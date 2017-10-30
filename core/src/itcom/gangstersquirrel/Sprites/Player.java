package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * The class of the player, extending the Sprite class and handling the textures, animations, positions and more of the player
 */
public class Player extends Sprite {

    // Only for testing until we decided if we want to use a circle or box as collision box
    private final boolean USE_CIRCLE_COLLISION_BOX = false;

    // Texture resolution
    private final int PLAYER_PIXEL_WIDTH = 32;
    private final int PLAYER_PIXEL_HEIGHT = 32;

    public World world;
    public Body body;

    private TextureRegion playerStanding;

    public Player(World world, PlayScreen screen) {
        // Get texture region out of the texture atlas for the squirrel
        super(screen.getPlayerTextureAtlas().findRegion("squirrel"));

        this.world = world;
        definePlayer();

        playerStanding = new TextureRegion(getTexture(), 0, 0, PLAYER_PIXEL_WIDTH, PLAYER_PIXEL_HEIGHT);
        setBounds(0, 0, PLAYER_PIXEL_WIDTH / MainGameClass.PPM, PLAYER_PIXEL_HEIGHT / MainGameClass.PPM);
        setRegion(playerStanding);
    }

    /**
     * Update the position of the sprite
     * @param deltaTime the time between the last and current frame in seconds
     */
    public void update(float deltaTime) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    /**
     * Defines the Box2D physics properties of the player, including collision box, body and fixture definition
     */
    public void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / MainGameClass.PPM, 64 / MainGameClass.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        if (USE_CIRCLE_COLLISION_BOX) {
            CircleShape shape = new CircleShape();
            shape.setRadius(PLAYER_PIXEL_WIDTH / 2 / MainGameClass.PPM);
            fixtureDef.shape = shape;
        } else {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(PLAYER_PIXEL_WIDTH / 4 / MainGameClass.PPM, PLAYER_PIXEL_HEIGHT / 2 / MainGameClass.PPM);
            fixtureDef.shape = shape;
        }

        body.createFixture(fixtureDef);

        // Notice collisions on the top of the collision box
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MainGameClass.PPM, 5 / MainGameClass.PPM), new Vector2(2 / MainGameClass.PPM, 5 / MainGameClass.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("head");
    }

}
