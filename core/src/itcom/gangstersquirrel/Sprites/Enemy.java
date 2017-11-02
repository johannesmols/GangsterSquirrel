package itcom.gangstersquirrel.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    private final int ENEMY_PIXEL_WIDTH = 32;
    private final int ENEMY_PIXEL_HEIGHT = 32;

    public World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
//        velocity = new Vector2(-1, -2);
//        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
}
