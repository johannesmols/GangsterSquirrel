package itcom.gangstersquirrel.KeyMap;

public class KeyAllocation {

    String action;
    int[] keys;

    public KeyAllocation(String action, int[] keys) {
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
