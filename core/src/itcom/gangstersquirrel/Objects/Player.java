package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.Items.WeaponObject;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * The class of the player, extending the Sprite class and handling the textures, animations, positions and more of the player
 */
public class Player extends Sprite {

    private World world;
    public Body body;

    // Player parameters
    private int health;
    private List<WeaponObject> weapons = new ArrayList<>();
    private float jumpImpulseVelocity;
    private float walkImpulseVelocity;
    private float climbImpulseVelocity;
    private float maxWalkVelocity;
    private float maxClimbVelocity;

    // Spawn parameters
    private int spawnTileX = 0;
    private int spawnTileY = 0;

    // Gameplay relevant variables
    private boolean isOnJumpableGround;

    // Texture resolution
    private final int PLAYER_PIXEL_WIDTH = 48;
    private final int PLAYER_PIXEL_HEIGHT = 24;

    // Texture regions
    private TextureRegion playerStanding;

    public Player(PlayScreen screen, int spawnPosition_X, int spawnPosition_Y) {
        // Get texture region out of the texture atlas for the squirrel
        super(screen.getPlayerTextureAtlas().findRegion("squirrel_branch_frame0"));

        this.world = screen.getWorld();
        this.spawnTileX = spawnPosition_X;
        this.spawnTileY = spawnPosition_Y;

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
    private void definePlayer() {

        // Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnTileX * MainGameClass.TILE_PIXEL_SIZE / MainGameClass.PPM, spawnTileY * MainGameClass.TILE_PIXEL_SIZE / MainGameClass.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        // Shape definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(PLAYER_PIXEL_WIDTH / 4 / MainGameClass.PPM, PLAYER_PIXEL_HEIGHT / 2 / MainGameClass.PPM); // divided by 4 to make width of collision box half as wide as the texture

        // Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Create body
        body.createFixture(fixtureDef);

        // Collision sensor fixture definition
        FixtureDef collisionFixtureDef = new FixtureDef();
        collisionFixtureDef.shape = shape;
        collisionFixtureDef.isSensor = true;
        body.createFixture(collisionFixtureDef).setUserData("player");
    }

    /* ----- GETTER AND SETTER -------------------------------------------------------------------------------------- */

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<WeaponObject> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<WeaponObject> weapons) {
        this.weapons = weapons;
    }

    public float getJumpImpulseVelocity() {
        return jumpImpulseVelocity;
    }

    public void setJumpImpulseVelocity(float jumpImpulseVelocity) {
        this.jumpImpulseVelocity = jumpImpulseVelocity;
    }

    public float getWalkImpulseVelocity() {
        return walkImpulseVelocity;
    }

    public void setWalkImpulseVelocity(float walkImpulseVelocity) {
        this.walkImpulseVelocity = walkImpulseVelocity;
    }

    public float getClimbImpulseVelocity() {
        return climbImpulseVelocity;
    }

    public void setClimbImpulseVelocity(float climbImpulseVelocity) {
        this.climbImpulseVelocity = climbImpulseVelocity;
    }

    public float getMaxWalkVelocity() {
        return maxWalkVelocity;
    }

    public void setMaxWalkVelocity(float maxWalkVelocity) {
        this.maxWalkVelocity = maxWalkVelocity;
    }

    public float getMaxClimbVelocity() {
        return maxClimbVelocity;
    }

    public void setMaxClimbVelocity(float maxClimbVelocity) {
        this.maxClimbVelocity = maxClimbVelocity;
    }

    public boolean getIsOnJumpableGround() {
        return isOnJumpableGround;
    }

    public void setIsOnJumpableGround(boolean onJumpableGround) {
        isOnJumpableGround = onJumpableGround;
    }

    /* ---------------------------------------------------------------------------------------------------------------*/
}
