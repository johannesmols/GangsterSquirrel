package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.Sprites.Enemy;
import itcom.gangstersquirrel.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "player" || fixtureB.getUserData() == "player") {
            // Get either fixture A or B, depending on which of them is the player fixture
            Fixture player = fixtureA.getUserData() == "player" ? fixtureA : fixtureB;
            // Get the colliding object, depending on which of them is the player fixture
            Fixture collidedObject = player == fixtureA ? fixtureB : fixtureA;

            // Determine what kind of object the player collided with and trigger the respectable method
            if (collidedObject.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) collidedObject.getUserData()).onPlayerHit();
            } else if (collidedObject.getUserData() instanceof Enemy) {
                ((Enemy) collidedObject.getUserData()).onPlayerHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
