package itcom.gangstersquirrel.GameProgress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import itcom.gangstersquirrel.Items.WeaponList;
import itcom.gangstersquirrel.Items.WeaponObject;
import itcom.gangstersquirrel.Tools.JSONFileCreator;

import java.util.ArrayList;

/**
 * This class loads and saves the overall progress of the game, including levels, highscores, equipped weapons, player attributes, ...
 */
public class GameProgress {

    private FileHandle fileHandle;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private GameProgressObject gameProgress;

    // Default game progress
    private GameProgressObject defaultGameProgress = new GameProgressObject(
            1,
            1,
            0,
            100,
            5,
            5,
            4f,
            0.1f,
            1f,
            2f,
            1f,
            0,
            new ArrayList<WeaponObject>()
    );

    /**
     * The constructor reads the file to a local variable and creates a new file if necessary
     */
    public GameProgress() {
        defaultGameProgress.getPlayerWeaponList().add(new WeaponList().getAllWeapons().get(0));

        // JSON file to store the game progress
        fileHandle = Gdx.files.local("json/gameprogress.json");

        if (fileHandle.exists()) {
            String json = fileHandle.readString();
            if (!json.trim().isEmpty()) {
                gameProgress = deserializeGameProgress(json);
            } else {
                System.err.println("JSON game progress string is empty, creating default game progress");
                serializeGameProgress(defaultGameProgress);
                gameProgress = deserializeGameProgress(json);
            }
        } else {
            serializeGameProgress(defaultGameProgress);
            gameProgress = deserializeGameProgress(fileHandle.readString());
        }
    }

    /**
     * Writes changes of the game progress into a JSON files and loads it afterwards
     * @param gameProgress contains the game progress
     */
    private void serializeGameProgress(GameProgressObject gameProgress) {
        String json = gson.toJson(gameProgress);

        // Creates a new JSON file, if it doesn't exist already
        if (JSONFileCreator.createEmptyJSONFileIfItDoesntExist(fileHandle)) {
            fileHandle.writeString(json, false); // false = overwrite instead of append
            this.gameProgress = deserializeGameProgress(json);
        }
    }

    /**
     * Reads game progress from a JSON file and returns it as a GameProgressObject
     * @param json the JSON string that will be deserialized
     * @return the GameProgressObject
     */
    private GameProgressObject deserializeGameProgress(String json) {
        return new GameProgressObject(gson.fromJson(json, GameProgressObject.class));
    }

    public void resetToDefault() {
        serializeGameProgress(defaultGameProgress);
    }

    /* ----- GETTERS AND SETTERS ----- */

    public GameProgressObject getGameProgress() {
        return gameProgress;
    }

    public void setGameProgress(GameProgressObject gameProgress) {
        serializeGameProgress(gameProgress);
    }

    public int getCurrentLevel() {
        return gameProgress.getCurrentLevel();
    }

    public void setCurrentLevel(int currentLevel) {
        gameProgress.setCurrentLevel(currentLevel);
        serializeGameProgress(gameProgress);
    }

    public int getUnlockedLevels() {
        return gameProgress.getUnlockedLevels();
    }

    public void setUnlockedLevels(int unlockedLevels) {
        gameProgress.setUnlockedLevels(unlockedLevels);
        serializeGameProgress(gameProgress);
    }

    public long getCurrentTime() {
        return gameProgress.getCurrentTime();
    }

    public void setCurrentTime(long currentTime) {
        gameProgress.setCurrentTime(currentTime);
        serializeGameProgress(gameProgress);
    }

    public int getPlayerMaxHealth() {
        return gameProgress.getPlayerMaxHealth();
    }

    public void setPlayerMaxHealth(int playerMaxHealth) {
        gameProgress.setPlayerMaxHealth(playerMaxHealth);
        serializeGameProgress(gameProgress);
    }

    public int getPlayerLifes() {
        return gameProgress.getPlayerLifes();
    }

    public void setPlayerLifes(int playerLifes) {
        gameProgress.setPlayerLifes(playerLifes);
        serializeGameProgress(gameProgress);
    }

    public int getPlayerMaximumLifes() {
        return gameProgress.getPlayerMaximumLifes();
    }

    public void setPlayerMaximumLifes(int playerMaximumLifes) {
        gameProgress.setPlayerMaximumLifes(playerMaximumLifes);
        serializeGameProgress(gameProgress);
    }

    public float getPlayerJumpImpulseVelocity() {
        return gameProgress.getPlayerJumpImpulseVelocity();
    }

    public void setPlayerJumpImpulseVelocity(float jumpImpulseVelocity) {
        gameProgress.setPlayerJumpImpulseVelocity(jumpImpulseVelocity);
        serializeGameProgress(gameProgress);
    }

    public float getPlayerWalkImpulseVelocity() {
        return gameProgress.getPlayerWalkImpulseVelocity();
    }

    public void setPlayerWalkImpulseVelocity(float walkImpulseVelocity) {
        gameProgress.setPlayerWalkImpulseVelocity(walkImpulseVelocity);
        serializeGameProgress(gameProgress);
    }

    public float getPlayerClimbImpulseVelocity() {
        return gameProgress.getPlayerClimbImpulseVelocity();
    }

    public void setPlayerClimbImpulseVelocity(float climbImpulseVelocity) {
        gameProgress.setPlayerClimbImpulseVelocity(climbImpulseVelocity);
        serializeGameProgress(gameProgress);
    }

    public float getPlayerMaxWalkVelocity() {
        return gameProgress.getPlayerMaxWalkVelocity();
    }

    public void setPlayerMaxWalkVelocity(float maxWalkVelocity) {
        gameProgress.setPlayerMaxWalkVelocity(maxWalkVelocity);
        serializeGameProgress(gameProgress);
    }

    public float getPlayerMaxClimbVelocity() {
        return gameProgress.getPlayerMaxClimbVelocity();
    }

    public void setPlayerMaxClimbVelocity(float maxClimbVelocity) {
        gameProgress.setPlayerMaxClimbVelocity(maxClimbVelocity);
        serializeGameProgress(gameProgress);
    }

    public int getCurrentlyEquippedWeapon() {
        return gameProgress.getCurrentlyEquippedWeapon();
    }

    public void setCurrentlyEquippedWeapon(int currentlyEquippedWeapon) {
        gameProgress.setCurrentlyEquippedWeapon(currentlyEquippedWeapon);
        serializeGameProgress(gameProgress);
    }

    public ArrayList<WeaponObject> getPlayerWeaponList() {
        return gameProgress.getPlayerWeaponList();
    }

    public void setPlayerWeaponList(ArrayList<WeaponObject> playerWeaponList) {
        gameProgress.setPlayerWeaponList(playerWeaponList);
        serializeGameProgress(gameProgress);
    }
}