package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

/**
 * This is a helper class to create empty files, given a certain FileHandle
 */
public class JSONFileCreator {

    /**
     * Creates a new file, if it doesn't exist already
     * @param fileHandle defines file name, path and type
     * @return if the operation was successful
     */
    public static boolean createEmptyJSONFile(FileHandle fileHandle) {

        boolean successful = false;
        if (!fileHandle.exists()) {
            try {
                successful = fileHandle.file().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            successful = true;
        }

        return successful;
    }
}
