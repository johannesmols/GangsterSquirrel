/*
package itcom.gangstersquirrel.Sprites;

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
            frames.add(new TextureRegion(screen.getAtlas().findRegion("frog"), i * 32, 0, 32, 32));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 32 / MainGameClass.PPM, 32 / MainGameClass.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    @Override
    protected void defineEnemy() {

    }

    @Override
    public void update(float dt) {

    }
}
*/
