package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;

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
            if (collidedObject.getUserData() instanceof InteractiveMapTileObject) {
                ((InteractiveMapTileObject) collidedObject.getUserData()).onPlayerBeginContact();
            } else if (collidedObject.getUserData() instanceof Enemy) {
                ((Enemy) collidedObject.getUserData()).onPlayerBeginContact();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "player" || fixtureB.getUserData() == "player") {
            // Get either fixture A or B, depending on which of them is the player fixture
            Fixture player = fixtureA.getUserData() == "player" ? fixtureA : fixtureB;
            // Get the colliding object, depending on which of them is the player fixture
            Fixture collidedObject = player == fixtureA ? fixtureB : fixtureA;

            // Determine what kind of object the player collided with and trigger the respectable method
            if (collidedObject.getUserData() instanceof InteractiveMapTileObject) {
                ((InteractiveMapTileObject) collidedObject.getUserData()).onPlayerEndContact();
            } else if (collidedObject.getUserData() instanceof Enemy) {
                ((Enemy) collidedObject.getUserData()).onPlayerEndContact();
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
