package itcom.gangstersquirrel.KeyBindings;

/**
 * An object which stores a certain action as String and it's corresponding key codes as Integers
 */
public class KeyBindingObject {

    String action;
    int[] keys;

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
