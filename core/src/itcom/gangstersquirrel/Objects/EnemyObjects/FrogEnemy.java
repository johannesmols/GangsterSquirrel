package itcom.gangstersquirrel.Objects.EnemyObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.Player;
import itcom.gangstersquirrel.Screens.PlayScreen;

/**
 * An enemy of the type frog
 */
public class FrogEnemy extends Enemy {

    private PlayScreen playScreen;

    // Animation parameters
    private float stateTime;
    private Animation<TextureRegion> attackAnimation;
    private Array<TextureRegion> attackFrames;
    private Animation<TextureRegion> jumpAnimation;
    private Array<TextureRegion> jumpFrames;
    private String currentAnimation;

    /**
     * Sets up the enemy in the game
     * @param screen the play screen
     * @param spawnPositionX the spawn position as tile index on the x axis
     * @param spawnPositionY the spawn position as tile index on the y axis
     */
    public FrogEnemy(PlayScreen screen, int spawnPositionX, int spawnPositionY) {
        super(screen, spawnPositionX, spawnPositionY);

        playScreen = screen;

        // Set gameplay variables of super class for this specific type of enemy
        damageMinMax = new int[] { 5, 15 };
        health = 10;
        horizontalMoveImpulseVelocity = 0.1f;
        horizontalMaxMovementVelocity = 0.5f;

        // Animation set up
        attackFrames = new Array<>();
        for(int i = 0; i < 4; i++) {
            attackFrames.add(new TextureRegion(
                    screen.getEnemyFrogTextureAtlas().findRegion("frog_attack"), i * ENEMY_PIXEL_WIDTH, 0, ENEMY_PIXEL_WIDTH, ENEMY_PIXEL_HEIGHT)
            );
        }

        jumpFrames = new Array<>();
        for(int i = 0; i < 4; i++) {
            jumpFrames.add(new TextureRegion(
                    screen.getEnemyFrogTextureAtlas().findRegion("frog_jump"), i * ENEMY_PIXEL_WIDTH, 0, ENEMY_PIXEL_WIDTH, ENEMY_PIXEL_HEIGHT)
            );
        }

        attackAnimation = new Animation<>(0.4f, attackFrames);
        jumpAnimation = new Animation<>(0.4f, jumpFrames);

        currentAnimation = "frog_jump";

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
            case "frog_jump":
                setRegion(jumpAnimation.getKeyFrame(stateTime, true));
                break;
            case "frog_attack":
                setRegion(attackAnimation.getKeyFrame(stateTime, true));
                break;
            default:
                break;
        }
        setFlip(isMovingLeftOrRight, false);

        // Move enemy around the map
        moveEnemy(deltaTime);
    }

    @Override
    public void onPlayerBeginContact(Player player) {
        playScreen.log("Frog Enemy : Collision with player");
        screen.setPlayerCurrentHealth(screen.getPlayer().getHealth() - randomDamageValueBetweenMinAndMax());

        // Change movement direction
        setMovingLeftOrRight(!getIsMovingLeftOrRight());

        // Change to attack animation
        changeAnimation("frog_attack");
    }

    @Override
    public void onPlayerEndContact(Player player) {
        // Switch back to normal move animation
        changeAnimation("frog_jump");
    }

    @Override
    public void onEnemyBeginContact(Enemy enemy) {
        playScreen.log("Frog Enemy : Collision with other enemy");

        // Change moving direction of both enemies, the collided one and itself
        this.setMovingLeftOrRight(!isMovingLeftOrRight);
        enemy.setMovingLeftOrRight(!enemy.getIsMovingLeftOrRight());
    }

    @Override
    public void onEnemyEndContact(Enemy enemy) {

    }

    private void changeAnimation(String animation) {
        switch (animation) {
            case "frog_jump":
                currentAnimation = "frog_jump";
                break;
            case "frog_attack":
                currentAnimation = "frog_attack";
                break;
            default:
                break;
        }

        stateTime = 0;
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
        return this.horizontalMaxMovementVelocity;
    }

    @Override
    public void setHorizontalMaxMovementVelocity(float horizontalMaxMovementVelocity) {
        this.horizontalMaxMovementVelocity = horizontalMaxMovementVelocity;
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
