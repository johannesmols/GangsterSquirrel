package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class Player extends Sprite {

    private final int PLAYER_PIXEL_WIDTH = 32;
    private final int PLAYER_PIXEL_HEIGHT = 32;
    private final int PLAYER_BOX_WIDTH = 5;
    private final int PLAYER_BOX_HEIGHT = 5;

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

    public void update(float deltaTime) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / MainGameClass.PPM, 64 / MainGameClass.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(PLAYER_BOX_WIDTH / MainGameClass.PPM, PLAYER_BOX_HEIGHT / MainGameClass.PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

}
