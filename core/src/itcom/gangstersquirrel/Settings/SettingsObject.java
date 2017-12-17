package itcom.gangstersquirrel.Settings;

public class SettingsObject {

    private boolean fullscreen;
    private int gameWidth;
    private int gameHeight;

    /**
     * @param fullscreen if the game should run in fullscreen mode
     * @param gameWidth the window width
     * @param gameHeight the window height
     */
    public SettingsObject(boolean fullscreen, int gameWidth, int gameHeight) {
        this.fullscreen = fullscreen;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
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
}
