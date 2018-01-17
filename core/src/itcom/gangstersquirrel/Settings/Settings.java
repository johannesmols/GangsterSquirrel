package itcom.gangstersquirrel.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import itcom.gangstersquirrel.Tools.JSONFileCreator;

public class Settings {

    private FileHandle fileHandle;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private SettingsObject settings;

    /**
     * The constructor reads the file to a local variable and creates a new file if necessary
     */
    public Settings() {
        // JSON file to store the game progress
        fileHandle = Gdx.files.local("json/settings.json");

        SettingsObject defaultSettings = new SettingsObject(
                true,
                true,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                "player"
        );

        if (fileHandle.exists()) {
            String json = fileHandle.readString();
            if (!json.trim().isEmpty()) {
                settings = deserializeSettings(json);
            } else {
                System.err.println("JSON game progress string is empty, creating default game progress");
                serializeSettings(defaultSettings);
                settings = deserializeSettings(json);
            }
        } else {
            serializeSettings(defaultSettings);
            settings = deserializeSettings(fileHandle.readString());
        }
    }

    /**
     * Writes changes of the settings into a JSON files and loads it afterwards
     * @param settings contains the settings
     */
    private void serializeSettings(SettingsObject settings) {
        String json = gson.toJson(settings);

        // Creates a new JSON file, if it doesn't exist already
        if (JSONFileCreator.createEmptyJSONFileIfItDoesntExist(fileHandle)) {
            fileHandle.writeString(json, false); // false = overwrite instead of append
            this.settings = deserializeSettings(json);
        }
    }

    /**
     * Reads settings from a JSON file and returns it as a SettingsObject
     * @param json the JSON string that will be deserialized
     * @return the SettingsObject
     */
    private SettingsObject deserializeSettings(String json) {
        return gson.fromJson(json, SettingsObject.class);
    }

    /* ----- GETTERS AND SETTERS ----- */

    public SettingsObject getSettings() {
        return settings;
    }

    public void setSettings(SettingsObject settings) {
        serializeSettings(settings);
    }

    public boolean isPlaySounds() {
        return settings.isPlaySounds();
    }

    public void setPlaySounds(boolean playSounds){
        settings.setPlaySounds(playSounds);
    }

    public boolean getFullscreen() {
        return settings.getFullscreen();
    }

    public void setFullscreen(boolean fullscreen) {
        settings.setFullscreen(fullscreen);
        serializeSettings(settings);
    }

    public int getGameWidth() {
        return settings.getGameWidth();
    }

    public void setGameWidth(int gameWidth) {
        settings.setGameWidth(gameWidth);
        serializeSettings(settings);
    }

    public int getGameHeight() {
        return settings.getGameHeight();
    }

    public void setGameHeight(int gameHeight) {
        settings.setGameHeight(gameHeight);
        serializeSettings(settings);
    }

    public String getPlayerName() {
        return settings.getPlayerName();
    }

    public void setPlayerName(String playerName) {
        settings.setPlayerName(playerName);
        serializeSettings(settings);
    }
}
