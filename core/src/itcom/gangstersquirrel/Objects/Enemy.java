package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

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

    public Enemy(PlayScreen screen, float spawnPositionX, float spawnPositionY){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(spawnPositionX * MainGameClass.TILE_PIXEL_SIZE, spawnPositionY * MainGameClass.TILE_PIXEL_SIZE);

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

    protected int randomDamageValueBetweenMinAndMax() {
        return new Random().nextInt((damageMinMax[1] - damageMinMax[0]) + 1) + damageMinMax[0]; // ((max - min) + 1) + min
    }

    public abstract void update(float dt);
    public abstract void onPlayerBeginContact();
    public abstract void onPlayerEndContact();

    // Getter and Setter
    public abstract int[] getDamageMinMax();
    public abstract void setDamageMinMax(int[] newDamageMinMax);
    public abstract int getHealth();
    public abstract void setHealth(int newHealth);
}
