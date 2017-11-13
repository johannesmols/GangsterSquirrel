package itcom.gangstersquirrel.GameProgress;

import itcom.gangstersquirrel.Items.WeaponObject;

import java.util.ArrayList;

/**
 * An object which stores the game's progress
 */
public class GameProgressObject {

    private int currentLevel;
    private long currentTime;
    private int playerMaxHealth;
    private int playerLifes;
    private int playerMaximumLifes;
    private float playerJumpImpulseVelocity;
    private float playerWalkImpulseVelocity;
    private float playerClimbImpulseVelocity;
    private float playerMaxWalkVelocity;
    private float playerMaxClimbVelocity;
    private int currentlyEquippedWeapon;
    private ArrayList<WeaponObject> playerWeaponList;

    /**
     * @param currentLevel the current level which the player is in
     * @param currentTime the current time in seconds, will remain when the player dies and respawns
     * @param playerMaxHealth the maximum health that the player can have
     * @param playerLifes the current amount of player's lifes left
     * @param playerMaximumLifes the maximum amount of player's lifes
     * @param playerJumpImpulseVelocity the jumping impulse velocity used in Box2D
     * @param playerWalkImpulseVelocity the walking impulse velocity used in Box2D
     * @param playerClimbImpulseVelocity the climbing impulse velocity used in Box2D
     * @param playerMaxWalkVelocity the maximal walking velocity of the player
     * @param playerMaxClimbVelocity the maximal climbing velocity of the player
     * @param currentlyEquippedWeapon the index of the currently equipped weapon in the weapon list of the player
     * @param playerWeaponList the equipped weapons of the player
     */
    public GameProgressObject(int currentLevel, long currentTime, int playerMaxHealth, int playerLifes, int playerMaximumLifes, float
            playerJumpImpulseVelocity, float playerWalkImpulseVelocity, float playerClimbImpulseVelocity, float
            playerMaxWalkVelocity, float playerMaxClimbVelocity, int currentlyEquippedWeapon, ArrayList<WeaponObject> playerWeaponList) {

        this.currentLevel = currentLevel;
        this.currentTime = currentTime;
        this.playerMaxHealth = playerMaxHealth;
        this.playerLifes = playerLifes;
        this.playerMaximumLifes = playerMaximumLifes;
        this.playerJumpImpulseVelocity = playerJumpImpulseVelocity;
        this.playerWalkImpulseVelocity = playerWalkImpulseVelocity;
        this.playerClimbImpulseVelocity = playerClimbImpulseVelocity;
        this.playerMaxWalkVelocity = playerMaxWalkVelocity;
        this.playerMaxClimbVelocity = playerMaxClimbVelocity;
        this.currentlyEquippedWeapon = currentlyEquippedWeapon;
        this.playerWeaponList = playerWeaponList;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        if (currentLevel >= 1) {
            this.currentLevel = currentLevel;
        }
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        if (currentTime >= 0) {
            this.currentTime = currentTime;
        }
    }

    public int getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public void setPlayerMaxHealth(int playerMaxHealth) {
        if (playerMaxHealth >= 1) {
            this.playerMaxHealth = playerMaxHealth;
        }
    }

    public int getPlayerLifes() {
        return playerLifes;
    }

    public void setPlayerLifes(int playerLifes) {
        this.playerLifes = playerLifes;
    }

    public int getPlayerMaximumLifes() {
        return playerMaximumLifes;
    }

    public void setPlayerMaximumLifes(int playerMaximumLifes) {
        this.playerMaximumLifes = playerMaximumLifes;
    }

    public float getPlayerJumpImpulseVelocity() {
        return playerJumpImpulseVelocity;
    }

    public void setPlayerJumpImpulseVelocity(float playerJumpImpulseVelocity) {
        this.playerJumpImpulseVelocity = playerJumpImpulseVelocity;
    }

    public float getPlayerWalkImpulseVelocity() {
        return playerWalkImpulseVelocity;
    }

    public void setPlayerWalkImpulseVelocity(float playerWalkImpulseVelocity) {
        this.playerWalkImpulseVelocity = playerWalkImpulseVelocity;
    }

    public float getPlayerClimbImpulseVelocity() {
        return playerClimbImpulseVelocity;
    }

    public void setPlayerClimbImpulseVelocity(float playerClimbImpulseVelocity) {
        this.playerClimbImpulseVelocity = playerClimbImpulseVelocity;
    }

    public float getPlayerMaxWalkVelocity() {
        return playerMaxWalkVelocity;
    }

    public void setPlayerMaxWalkVelocity(float playerMaxWalkVelocity) {
        this.playerMaxWalkVelocity = playerMaxWalkVelocity;
    }

    public float getPlayerMaxClimbVelocity() {
        return playerMaxClimbVelocity;
    }

    public void setPlayerMaxClimbVelocity(float playerMaxClimbVelocity) {
        this.playerMaxClimbVelocity = playerMaxClimbVelocity;
    }

    public int getCurrentlyEquippedWeapon() {
        return currentlyEquippedWeapon;
    }

    public void setCurrentlyEquippedWeapon(int currentlyEquippedWeapon) {
        this.currentlyEquippedWeapon = currentlyEquippedWeapon;
    }

    public ArrayList<WeaponObject> getPlayerWeaponList() {
        return playerWeaponList;
    }

    public void setPlayerWeaponList(ArrayList<WeaponObject> playerWeaponList) {
        if (playerWeaponList != null) {
            this.playerWeaponList = playerWeaponList;
        }
    }
}
