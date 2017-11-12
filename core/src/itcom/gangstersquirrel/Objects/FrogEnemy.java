package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class FrogEnemy extends Enemy {

    private PlayScreen playScreen;

    // Animation parameters
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;

    public FrogEnemy(PlayScreen screen, int spawnPositionX, int spawnPositionY) {
        super(screen, spawnPositionX, spawnPositionY);

        playScreen = screen;

        // Set gameplay variables of super class for this specific type of enemy
        damageMinMax = new int[] { 5, 15 };
        health = 20;

        frames = new Array<>();

        for(int i = 0; i < 25; i++) {
            frames.add(new TextureRegion(
                    screen.getEnemyFrogTextureAtlas().findRegion("frog"), i * ENEMY_PIXEL_HEIGHT, 0, ENEMY_PIXEL_WIDTH, ENEMY_PIXEL_HEIGHT)
            );
        }

        walkAnimation = new Animation<>(10f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), ENEMY_PIXEL_WIDTH / MainGameClass.PPM, ENEMY_PIXEL_HEIGHT / MainGameClass.PPM + getHeight() / 2);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((getX() + getWidth() / 2) / MainGameClass.PPM, (getY() + getHeight() / 2) / MainGameClass.PPM);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(ENEMY_PIXEL_WIDTH / 4 / MainGameClass.PPM);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
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
