package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

import java.util.HashMap;
import java.util.Random;

public abstract class Enemy extends Sprite {

    protected World world;
    protected Body body;

    protected final int ENEMY_PIXEL_WIDTH = 32;
    protected final int ENEMY_PIXEL_HEIGHT = 32;

    protected PlayScreen screen;

    // Gameplay relevant variables
    protected int[] damageMinMax;
    protected int health;
    protected float horizontalMoveImpulseVelocity;
    protected float horizontalMaxMovementVelocity;

    // Moving
    protected boolean isMovingLeftOrRight;

    public Enemy(PlayScreen screen, float spawnPositionX, float spawnPositionY){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(spawnPositionX * MainGameClass.TILE_PIXEL_SIZE, spawnPositionY * MainGameClass.TILE_PIXEL_SIZE);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((getX() + getWidth() / 2) / MainGameClass.PPM, (getY() + getHeight() / 2) / MainGameClass.PPM);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = MainGameClass.CATEGORY_ENEMY;
        fixtureDef.filter.maskBits = MainGameClass.MASK_ENEMY;
        CircleShape shape = new CircleShape();
        shape.setRadius(ENEMY_PIXEL_WIDTH / 4 / MainGameClass.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

        FixtureDef headFixtureDef = new FixtureDef();
        headFixtureDef.shape = getHeadShape();
        headFixtureDef.isSensor = true;
        headFixtureDef.filter.categoryBits = MainGameClass.CATEGORY_ENEMY_HEAD;
        headFixtureDef.filter.maskBits = MainGameClass.MASK_ENEMY_HEAD;
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("class", this);
        userData.put("type", "enemy_head");
        body.createFixture(headFixtureDef).setUserData(userData);
    }

    /**
     * Creates a shape that hovers over the head of enemies, so when the player collides with it, the enemy dies
     * @return the shape
     */
    private PolygonShape getHeadShape() {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                ENEMY_PIXEL_WIDTH / 6 / MainGameClass.PPM,
                0,
                new Vector2(0,ENEMY_PIXEL_HEIGHT / 2 / MainGameClass.PPM),
                0f
        );

        return shape;
    }

    protected int randomDamageValueBetweenMinAndMax() {
        return new Random().nextInt((damageMinMax[1] - damageMinMax[0]) + 1) + damageMinMax[0]; // ((max - min) + 1) + min
    }

    protected void moveEnemy(float deltaTime) {
        if (isMovingLeftOrRight) {
            if (body.getLinearVelocity().x >= - horizontalMaxMovementVelocity) {
                body.applyLinearImpulse(new Vector2(- horizontalMoveImpulseVelocity, 0f), body.getWorldCenter(), true);
            }
        } else {
            if (body.getLinearVelocity().x <= horizontalMaxMovementVelocity) {
                body.applyLinearImpulse(new Vector2(horizontalMoveImpulseVelocity, 0f), body.getWorldCenter(), true);
            }
        }
    }

    public abstract void update(float dt);
    public abstract void onPlayerBeginContact(Player player);
    public abstract void onPlayerEndContact(Player player);
    public abstract void onEnemyBeginContact(Enemy enemy);
    public abstract void onEnemyEndContact(Enemy enemy);

    // Getter and Setter
    public abstract int[] getDamageMinMax();
    public abstract void setDamageMinMax(int[] newDamageMinMax);
    public abstract int getHealth();
    public abstract void setHealth(int newHealth);
    public abstract float getHorizontalMoveImpulseVelocity();
    public abstract void setHorizontalMoveImpulseVelocity(float horizontalMoveImpulseVelocity);
    public abstract float getHorizontalMaxMovementVelocity();
    public abstract void setHorizontalMaxMovementVelocity(float horizontalMaxMovementVelocity);
    public abstract boolean getIsMovingLeftOrRight();
    public abstract void setMovingLeftOrRight(boolean movingLeftOrRight);
}
