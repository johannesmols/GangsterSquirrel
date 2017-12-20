package itcom.gangstersquirrel.Objects.EnemyObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.Player;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * An enemy of the type boss, extends the abstract Enemy class
 */
public class BossEnemy extends Enemy {

    private PlayScreen playScreen;

    // Animation parameters
    private float stateTime;
    private Animation<TextureRegion> flyAnimation;
    private Array<TextureRegion> flyFrames;
    private String currentAnimation;

    /**
     * Sets up the enemy in the game
     * @param screen the play screen
     * @param spawnPositionX the spawn position as tile index on the x axis
     * @param spawnPositionY the spawn position as tile index on the y axis
     */
    public BossEnemy(PlayScreen screen, float spawnPositionX, float spawnPositionY) {
        super(screen, spawnPositionX, spawnPositionY);

        playScreen = screen;

        // Set gameplay variables of super class for this specific type of enemy
        damageMinMax = new int[] { 10, 20 };
        health = 50;
        horizontalMoveImpulseVelocity = 0.1f;
        horizontalMaxMovementVelocity = 0.5f;

        // Animation set up
        flyFrames = new Array<>();
        for(int i = 0; i < 12; i++) {
            flyFrames.add(new TextureRegion(screen.getEnemyBossTextureAtlas().findRegion("bird"), 0, i * ENEMY_PIXEL_HEIGHT, ENEMY_PIXEL_WIDTH, ENEMY_PIXEL_HEIGHT)
            );
        }

        flyAnimation = new Animation<TextureRegion>(0.2f, flyFrames);

        currentAnimation = "bird_fly";

        stateTime = 0;
        setBounds(getX(), getY(), ENEMY_PIXEL_WIDTH / MainGameClass.PPM, ENEMY_PIXEL_HEIGHT / MainGameClass.PPM + getHeight() / 2);
    }

    /**
     * Gets called once per frame and updates the enemy's texture and moves it
     * @param deltaTime the time between the current and the last frame
     */
    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4); // Set Y position to bottom of fixture circle

        switch (currentAnimation) {
            case "bird_fly":
                setRegion(flyAnimation.getKeyFrame(stateTime, true));
                break;
            default:
                break;
        }
        // Inverted because the bird texture is facing the other direction by default
        setFlip(!isMovingLeftOrRight, false);

        // Move enemy around the map
        moveEnemy(deltaTime);
    }

    @Override
    public void onPlayerBeginContact(Player player) {
        playScreen.log("Bird Enemy : Collision with player");
        screen.setPlayerCurrentHealth(screen.getPlayer().getHealth() - randomDamageValueBetweenMinAndMax());

        // Change movement direction
        setMovingLeftOrRight(!getIsMovingLeftOrRight());
    }

    @Override
    public void onPlayerEndContact(Player player) {

    }

    @Override
    public void onEnemyBeginContact(Enemy enemy) {
        playScreen.log("Bird Enemy : Collision with other enemy");

        // Change moving direction of both enemies, the collided one and itself
        this.setMovingLeftOrRight(!isMovingLeftOrRight);
        enemy.setMovingLeftOrRight(!enemy.getIsMovingLeftOrRight());
    }

    @Override
    public void onEnemyEndContact(Enemy enemy) {

    }

    /* ----- GETTERS AND SETTERS ------------------------------------------------------------------------------------ */

    @Override
    public int[] getDamageMinMax() {
        return damageMinMax;
    }

    @Override
    public void setDamageMinMax(int[] newDamageMinMax) {
        damageMinMax = newDamageMinMax;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int newHealth) {
        // Save inflicted damage to statistics
        playScreen.getStatistics().setDamageInflicted(playScreen.getStatistics().getDamageInflicted() + (health - newHealth));

        health = newHealth;

        if (health <= 0) {
            // Save death of enemy to statistics
            playScreen.getStatistics().setEnemiesKilled(playScreen.getStatistics().getEnemiesKilled() + 1);

            // Destroy Box2D body
            playScreen.destroyBodiesQueue.add(this.body);

            // Remove from play screen
            playScreen.getEnemies().remove(this);

            // Finish the level if this is the boss enemy in the last level
            if (screen.getGameProgress().getCurrentLevel() == 3) {
                screen.levelFinished();
            }
        }
    }

    @Override
    public float getHorizontalMoveImpulseVelocity() {
        return this.horizontalMoveImpulseVelocity;
    }

    @Override
    public void setHorizontalMoveImpulseVelocity(float horizontalMoveImpulseVelocity) {
        this.horizontalMoveImpulseVelocity = horizontalMoveImpulseVelocity;
    }

    @Override
    public float getHorizontalMaxMovementVelocity() {
        return horizontalMaxMovementVelocity;
    }

    @Override
    public void setHorizontalMaxMovementVelocity(float horizontalMaxMovementVelocity) {
        this.horizontalMoveImpulseVelocity = horizontalMaxMovementVelocity;
    }

    @Override
    public boolean getIsMovingLeftOrRight() {
        return isMovingLeftOrRight;
    }

    @Override
    public void setMovingLeftOrRight(boolean movingLeftOrRight) {
        this.isMovingLeftOrRight = movingLeftOrRight;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
}
