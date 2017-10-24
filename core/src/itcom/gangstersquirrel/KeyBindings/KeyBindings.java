package itcom.gangstersquirrel.KeyBindings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class KeyBindings {

    private final String[] actions = new String[] { "jump", "move_left", "move_right" };

    public ArrayList<Integer> JUMP = new ArrayList<>();
    public ArrayList<Integer> MOVE_LEFT = new ArrayList<>();
    public ArrayList<Integer> MOVE_RIGHT = new ArrayList<>();

    private FileHandle fileHandle;

    public KeyBindings() {
        // JSON file to store the key bindings
        fileHandle = Gdx.files.local("json/keybindings.json");

        // Default key bindings
        KeyBindingObject[] defaultKeyBindings = new KeyBindingObject[]{
                new KeyBindingObject(actions[0], new int[]{Input.Keys.UP, Input.Keys.SPACE, Input.Keys.W }),
                new KeyBindingObject(actions[1], new int[]{Input.Keys.LEFT, Input.Keys.A }),
                new KeyBindingObject(actions[2], new int[]{Input.Keys.RIGHT, Input.Keys.D })
        };

        if (fileHandle.exists()) {
            // File already exists, no need to create new
            String json = fileHandle.readString();
            if (!json.trim().isEmpty()) {
                deserializeKeyBindings(fileHandle.readString());
            } else {
                System.err.println("JSON key bindings string is empty, creating default key bindings");
                serializeKeyBindings(defaultKeyBindings);
                // Assign those values to the key map lists afterwards
                deserializeKeyBindings(fileHandle.readString());
            }
        } else {
            // File does not exist, create default file
            serializeKeyBindings(defaultKeyBindings);
            // Assign those values to the key bindings lists afterwards
            deserializeKeyBindings(fileHandle.readString());
        }
    }

    // Serialize key binding objects into JSON and write to the key bindings JSON file
    public void serializeKeyBindings(KeyBindingObject[] keyBindings) {
        Gson gson = new Gson();
        String json = gson.toJson(keyBindings);
        try {
            fileHandle.file().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHandle.writeString(json, false); // false = overwrite instead of append
    }

    // Deserialize JSON keybinding file and write key bindings to the lists, which are used by the application to process input
    private void deserializeKeyBindings(String json) {
        Gson gson = new Gson();
        KeyBindingObject[] keyBindings = gson.fromJson(json, KeyBindingObject[].class);

        // Assign deserialized key bindings to key bindings lists
        for (KeyBindingObject key : keyBindings) {
            if (key.action.equals(actions[0])) {
                JUMP.clear();
                for (int keycode : key.keys) {
                    JUMP.add(keycode);
                }
            } else if (key.action.equals(actions[1])) {
                MOVE_LEFT.clear();
                for (int keycode : key.keys) {
                    MOVE_LEFT.add(keycode);
                }
            } else if (key.action.equals(actions[2])) {
                MOVE_RIGHT.clear();
                for (int keycode : key.keys) {
                    MOVE_RIGHT.add(keycode);
                }
            }
        }
    }
}