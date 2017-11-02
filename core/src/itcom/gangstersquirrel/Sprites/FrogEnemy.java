package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class FrogEnemy extends Enemy{

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;

    public FrogEnemy(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 25; i++) {
            frames.add(new TextureRegion(screen.getEnemyFrogTextureAtlas().findRegion("frog"), i * 32, 0, 32, 32));
        }
        walkAnimation = new Animation(10f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 32 / MainGameClass.PPM, 32 / MainGameClass.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MainGameClass.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
    }
}
