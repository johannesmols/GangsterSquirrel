package itcom.gangstersquirrel.Objects.EnemyObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.Player;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class MonkeyEnemy extends Enemy {

    private PlayScreen playScreen;

    // Animation parameters
    private float stateTime;
    private Animation<TextureRegion> attackAnimation;
    private Array<TextureRegion> attackFrames;
    private Animation<TextureRegion> jumpAnimation;
    private Array<TextureRegion> jumpFrames;

    public MonkeyEnemy(PlayScreen screen, int spawnPositionX, int spawnPositionY) {
        super(screen, spawnPositionX, spawnPositionY);

        playScreen = screen;

        // Set gameplay variables of super class for this specific type of enemy
        damageMinMax = new int[] { 5, 15 };
        health = 15;
        // horizontalMoveImpulseVelocity = 0.1f;
        // horizontalMaxMovementVelocity = 0.5f;

        // Animation set up
        attackFrames = new Array<>();
        for(int i = 0; i < 4; i++) {
            attackFrames.add(new TextureRegion(
                    screen.getEnemyFrogTextureAtlas().findRegion("monkey_attack"), i * ENEMY_PIXEL_WIDTH, 0, ENEMY_PIXEL_WIDTH, ENEMY_PIXEL_HEIGHT)
            );
        attackAnimation = new Animation<>(0.4f, attackFrames);

        stateTime = 0;
        setBounds(getX(), getY(), ENEMY_PIXEL_WIDTH / MainGameClass.PPM, ENEMY_PIXEL_HEIGHT / MainGameClass.PPM + getHeight() / 2);
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        // setRegion(jumpAnimation.getKeyFrame(stateTime, true));
        // setFlip(isMovingLeftOrRight, false);

        // Move enemy around the map
        // moveEnemy(deltaTime);
    }

    @Override
    public void onPlayerBeginContact(Player player) {
        playScreen.log("Frog Enemy : Collision with player");
        screen.setPlayerCurrentHealth(screen.getPlayer().getHealth() - randomDamageValueBetweenMinAndMax());

        // Change movement direction
        setMovingLeftOrRight(!getIsMovingLeftOrRight());
    }

    @Override
    public void onPlayerEndContact(Player player) {

    }

//    @Override
//    public void onEnemyBeginContact(Enemy enemy) {
//        playScreen.log("Monkey Enemy : Collision with other enemy");
//
//        // Change moving direction of both enemies, the collided one and itself
//        this.setMovingLeftOrRight(!isMovingLeftOrRight);
//        enemy.setMovingLeftOrRight(!enemy.getIsMovingLeftOrRight());
//    }
//
//    @Override
//    public void onEnemyEndContact(Enemy enemy) {
//
//    }

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

/*
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
*/

    /* -------------------------------------------------------------------------------------------------------------- */
}
