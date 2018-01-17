package itcom.gangstersquirrel.Settings;

/**
 * A data structure class to define the settings
 */
public class SettingsObject {

    private boolean playSounds;
    private boolean fullscreen;
    private int gameWidth;
    private int gameHeight;
    private String playerName;

    /**
     * @param fullscreen if the game should run in fullscreen mode
     * @param gameWidth the window width
     * @param gameHeight the window height
     * @param playerName the name of the player that will appear on the scoreboard
     */
    public SettingsObject(boolean playSounds, boolean fullscreen, int gameWidth, int gameHeight, String playerName) {
        this.playSounds = playSounds;
        this.fullscreen = fullscreen;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.playerName = playerName;
    }

    public boolean isPlaySounds() {
        return playSounds;
    }

    public void setPlaySounds(boolean playSounds) {
        this.playSounds = playSounds;
    }

    public boolean getFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        if (playerName != null && !playerName.trim().isEmpty()) {
            this.playerName = playerName.trim();
        }
    }
}
