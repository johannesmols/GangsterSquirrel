package itcom.gangstersquirrel.Twitch;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.jibble.pircbot.*;

public class TwitchChat extends PircBot {

    /*private static FileHandle fileHandle = Gdx.files.local("json/twitch.json");
    String json = fileHandle.readString();*/

    // TODO: @JohannesMols help me with this ^

    private List<testListener> listeners = new ArrayList<testListener>();

    public void addListener(testListener toAdd) {
        listeners.add(toAdd);
    }

    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {
        this.test();
    }

    public void test() {
        for (testListener l : listeners) {
            l.someoneSaidSomething("wa!");
        }
    }

}
