package itcom.gangstersquirrel.Objects.EnemyObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class FrogEnemy extends Enemy {

    private PlayScreen playScreen;

    // Animation parameters
    private float stateTime;

    private Animation<TextureRegion> attackAnimation;
    private Array<TextureRegion> attackFrames;
    private Animation<TextureRegion> jumpAnimation;
    private Array<TextureRegion> jumpFrames;

    private boolean setToDestroy;
    private boolean destroyed;
    float angle;

    public FrogEnemy(PlayScreen screen, int spawnPositionX, int spawnPositionY) {
        super(screen, spawnPositionX, spawnPositionY);

        playScreen = screen;

        // Set gameplay variables of super class for this specific type of enemy
        damageMinMax = new int[] { 5, 15 };
        health = 20;

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

        stateTime = 0;
        setBounds(getX(), getY(), ENEMY_PIXEL_WIDTH / MainGameClass.PPM, ENEMY_PIXEL_HEIGHT / MainGameClass.PPM + getHeight() / 2);

        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(attackAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    public void onPlayerBeginContact() {
        Gdx.app.log("Frog Enemy", "Collision");
        screen.setPlayerCurrentHealth(screen.getPlayer().getHealth() - randomDamageValueBetweenMinAndMax());
    }

    @Override
    public void onPlayerEndContact() {
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

            //despawn
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */
}
