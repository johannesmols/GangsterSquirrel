package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Objects.Player;
import itcom.gangstersquirrel.Screens.PlayScreen;

import java.util.HashMap;

/**
 * This class listens to all collisions happening in the physics simulation and calls the respective methods
 */
public class WorldContactListener implements ContactListener {

    PlayScreen playScreen;

    public WorldContactListener(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

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

    /**
     * Process contacts of all kinds and
     * @param contact the contact object containing information about both collided objects
     * @param beginOrEndContact if the contact started or ended
     */
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
            } else if (collidedObject.getUserData() instanceof HashMap && ((HashMap) collidedObject.getUserData()).containsKey("class") && ((HashMap) collidedObject.getUserData()).containsKey("type")) {

                HashMap userData = (HashMap) collidedObject.getUserData();
                if (userData.get("type").equals("enemy_head")) {
                    Enemy enemy = (Enemy) userData.get("class");
                    if (beginOrEndContact) {
                        enemy.setHealth(-1);

                        // Make player jump automatically when hitting an enemies head
                        playScreen.getPlayer().body.setLinearVelocity(new Vector2(playScreen.getPlayer().body.getLinearVelocity().x, 0f));
                        playScreen.getPlayer().body.applyLinearImpulse(new Vector2(0, playScreen.getPlayer().getJumpImpulseVelocity()), playScreen.getPlayer().body.getWorldCenter(), true);
                    }
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
            } else if (collidedObject.getUserData() == "player_attack_right") {
                if (beginOrEndContact) {
                    if (!playScreen.getEnemiesCurrentlyInRightAttackRange().contains((Enemy) enemy.getUserData())) {
                        playScreen.getEnemiesCurrentlyInRightAttackRange().add((Enemy) enemy.getUserData());
                        playScreen.log("Enemy moved into right attack hitbox");
                    }
                } else {
                    if (playScreen.getEnemiesCurrentlyInRightAttackRange().contains((Enemy) enemy.getUserData())) {
                        playScreen.getEnemiesCurrentlyInRightAttackRange().remove((Enemy) enemy.getUserData());
                        playScreen.log("Enemy moved out of right attack hitbox");
                    }
                }
            } else if (collidedObject.getUserData() == "player_attack_left") {
                if (beginOrEndContact) {
                    if (!playScreen.getEnemiesCurrentlyInLeftAttackRange().contains((Enemy) enemy.getUserData())) {
                        playScreen.getEnemiesCurrentlyInLeftAttackRange().add((Enemy) enemy.getUserData());
                        playScreen.log("Enemy moved into left attack hitbox");
                    }
                } else {
                    if (playScreen.getEnemiesCurrentlyInLeftAttackRange().contains((Enemy) enemy.getUserData())) {
                        playScreen.getEnemiesCurrentlyInLeftAttackRange().remove((Enemy) enemy.getUserData());
                        playScreen.log("Enemy moved out of left attack hitbox");
                    }
                }
            }
        }
    }
}
