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
    private float playerJumpImpulseVelocity;
    private float playerWalkImpulseVelocity;
    private float playerClimbImpulseVelocity;
    private float playerMaxWalkVelocity;
    private float playerMaxClimbVelocity;
    private ArrayList<WeaponObject> playerWeaponList;
    private long[] playerHighscoreTimes;

    /**
     * This object stores the overall game progress
     * @param currentLevel the current level which the player is in
     * @param currentTime the current time in seconds, will remain when the player dies and respawns
     * @param playerMaxHealth the maximum health that the player can have
     * @param playerJumpImpulseVelocity the jumping impulse velocity used in Box2D
     * @param playerWalkImpulseVelocity the walking impulse velocity used in Box2D
     * @param playerClimbImpulseVelocity the climbing impulse velocity used in Box2D
     * @param playerMaxWalkVelocity the maximal walking velocity of the player
     * @param playerMaxClimbVelocity the maximal climbing velocity of the player
     * @param playerWeaponList the equipped weapons of the player
     * @param playerHighscoreTimes the highscore list of each level containing times in seconds
     */
    public GameProgressObject(int currentLevel, long currentTime, int playerMaxHealth, float
            playerJumpImpulseVelocity, float playerWalkImpulseVelocity, float playerClimbImpulseVelocity, float
            playerMaxWalkVelocity, float playerMaxClimbVelocity, ArrayList<WeaponObject> playerWeaponList, long[]
            playerHighscoreTimes) {

        this.currentLevel = currentLevel;
        this.currentTime = currentTime;
        this.playerMaxHealth = playerMaxHealth;
        this.playerJumpImpulseVelocity = playerJumpImpulseVelocity;
        this.playerWalkImpulseVelocity = playerWalkImpulseVelocity;
        this.playerClimbImpulseVelocity = playerClimbImpulseVelocity;
        this.playerMaxWalkVelocity = playerMaxWalkVelocity;
        this.playerMaxClimbVelocity = playerMaxClimbVelocity;
        this.playerWeaponList = playerWeaponList;
        this.playerHighscoreTimes = playerHighscoreTimes;
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

    public ArrayList<WeaponObject> getPlayerWeaponList() {
        return playerWeaponList;
    }

    public void setPlayerWeaponList(ArrayList<WeaponObject> playerWeaponList) {
        if (playerWeaponList != null) {
            this.playerWeaponList = playerWeaponList;
        }
    }

    public long[] getPlayerHighscoreTimes() {
        return playerHighscoreTimes;
    }

    public void setPlayerHighscoreTimes(long[] playerHighscoreTimes) {
        if (playerHighscoreTimes != null) {
            this.playerHighscoreTimes = playerHighscoreTimes;
        }
    }
}
