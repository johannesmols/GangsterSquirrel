package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

    World world;
    Body body;

    final int ENEMY_PIXEL_WIDTH = 32;
    final int ENEMY_PIXEL_HEIGHT = 32;

    protected PlayScreen screen;
    public Vector2 velocity;

    // Gameplay relevant variables
    int damage;
    int health;

    public Enemy(PlayScreen screen, float spawnPositionX, float spawnPositionY){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(spawnPositionX * ENEMY_PIXEL_WIDTH, spawnPositionY * ENEMY_PIXEL_HEIGHT);

        defineEnemy();

//        velocity = new Vector2(-1, -2);
//        body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void onPlayerHit();

    // Getter and Setter
    public abstract int getDamage();
    public abstract void setDamage(int newDamage);
    public abstract int getHealth();
    public abstract void setHealth(int newHealth);
}
