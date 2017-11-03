package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class FrogEnemy extends Enemy {

    // Animation parameters
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;

    public FrogEnemy(PlayScreen screen, int spawnPositionX, int spawnPositionY) {
        super(screen, spawnPositionX, spawnPositionY);

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
}
