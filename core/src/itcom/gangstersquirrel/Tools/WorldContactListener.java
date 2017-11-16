package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Objects.Player;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        processContact(contact, true);
    }

    @Override
    public void endContact(Contact contact) {
        processContact(contact, false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void processContact(Contact contact, boolean beginOrEndContact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Player collisions
        if (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player) {
            // Get either fixture A or B, depending on which of them is the player fixture
            Fixture player = fixtureA.getUserData() instanceof Player ? fixtureA : fixtureB;
            // Get the colliding object, depending on which of them is the player fixture
            Fixture collidedObject = player == fixtureA ? fixtureB : fixtureA;

            // Determine what kind of object the player collided with and trigger the respectable method
            if (collidedObject.getUserData() instanceof InteractiveMapTileObject) {
                if (beginOrEndContact) {
                    ((InteractiveMapTileObject) collidedObject.getUserData()).onPlayerBeginContact((Player) player.getUserData());
                } else {
                    ((InteractiveMapTileObject) collidedObject.getUserData()).onPlayerEndContact((Player) player.getUserData());
                }
            } else if (collidedObject.getUserData() instanceof Enemy) {
                if (beginOrEndContact) {
                    ((Enemy) collidedObject.getUserData()).onPlayerBeginContact((Player) player.getUserData());
                } else {
                    ((Enemy) collidedObject.getUserData()).onPlayerEndContact((Player) player.getUserData());
                }
            }
        }

        // Enemy collisions
        if (fixtureA.getUserData() instanceof Enemy || fixtureB.getUserData() instanceof Enemy) {
            // Get either fixture A or B, depending on which of them is the player fixture
            Fixture enemy = fixtureA.getUserData() instanceof Enemy ? fixtureA : fixtureB;
            // Get the colliding object, depending on which of them is the player fixture
            Fixture collidedObject = enemy == fixtureA ? fixtureB : fixtureA;

            // Determine what kind of object the player collided with and trigger the respectable method
            if (collidedObject.getUserData() instanceof InteractiveMapTileObject) {
                if (beginOrEndContact) {
                    ((InteractiveMapTileObject) collidedObject.getUserData()).onEnemyBeginContact((Enemy) enemy.getUserData());
                } else {
                    ((InteractiveMapTileObject) collidedObject.getUserData()).onEnemyEndContact((Enemy) enemy.getUserData());
                }
            } else if (collidedObject.getUserData() instanceof  Enemy) {
                if (beginOrEndContact) {
                    ((Enemy) collidedObject.getUserData()).onEnemyBeginContact((Enemy) enemy.getUserData());
                } else {
                    ((Enemy) collidedObject.getUserData()).onEnemyEndContact((Enemy) enemy.getUserData());
                }
            }
        }
    }
}
