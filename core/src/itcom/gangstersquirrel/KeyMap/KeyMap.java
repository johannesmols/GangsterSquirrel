package itcom.gangstersquirrel.KeyMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class KeyMap {

    private final String[] actions = new String[] { "jump", "move_left", "move_right" };

    public ArrayList<Integer> JUMP = new ArrayList<>();
    public ArrayList<Integer> MOVE_LEFT = new ArrayList<>();
    public ArrayList<Integer> MOVE_RIGHT = new ArrayList<>();

    private FileHandle fileHandle;

    public KeyMap() {
        // JSON file to store the key allocations
        fileHandle = Gdx.files.local("json/keymap.json");

        // Default key allocations
        KeyAllocation[] defaultKeyAllocations = new KeyAllocation[]{
                new KeyAllocation(actions[0], new int[]{Input.Keys.UP, Input.Keys.SPACE, Input.Keys.W}),
                new KeyAllocation(actions[1], new int[]{Input.Keys.LEFT, Input.Keys.A}),
                new KeyAllocation(actions[2], new int[]{Input.Keys.RIGHT, Input.Keys.D})
        };

        if (fileHandle.exists()) {
            // File already exists, no need to create new
            String json = fileHandle.readString();
            if (!json.trim().isEmpty()) {
                deserializeKeymap(fileHandle.readString());
            } else {
                System.err.println("JSON keymap string is empty, creating default keymap");
                serializeKeymap(defaultKeyAllocations);
                // Assign those values to the key map lists afterwards
                deserializeKeymap(fileHandle.readString());
            }
        } else {
            // File does not exist, create default file
            serializeKeymap(defaultKeyAllocations);
            // Assign those values to the key map lists afterwards
            deserializeKeymap(fileHandle.readString());
        }
    }

    // Serialize key allocation objects into JSON and write to the keymap JSON file
    public void serializeKeymap(KeyAllocation[] keyAllocations) {
        Gson gson = new Gson();
        String json = gson.toJson(keyAllocations);
        try {
            fileHandle.file().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHandle.writeString(json, false); // false = overwrite instead of append
    }

    // Deserialize JSON keymap file and write key allocations to the lists, which are used by the application to process input
    private void deserializeKeymap(String json) {
        Gson gson = new Gson();
        KeyAllocation[] keyAllocations = gson.fromJson(json, KeyAllocation[].class);

        // Assign deserialized keymap to keymap lists
        for (KeyAllocation key : keyAllocations) {
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