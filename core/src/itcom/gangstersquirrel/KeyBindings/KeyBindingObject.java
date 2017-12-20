package itcom.gangstersquirrel.KeyBindings;

/**
 * An object which stores a certain action as String and it's corresponding key codes as Integers
 */
public class KeyBindingObject {

    private String action;
    private int[] keys;

    /**
     * Assigns the parameters to the local field variables
     * @param action the action in the game
     * @param keys the relevant keys for this action
     */
    public KeyBindingObject(String action, int[] keys) {
        this.action = action;
        this.keys = keys;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int[] getKeys() {
        return keys;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }
}
