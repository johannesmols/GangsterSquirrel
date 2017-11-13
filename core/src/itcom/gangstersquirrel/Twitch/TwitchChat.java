package itcom.gangstersquirrel.Twitch;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.jibble.pircbot.*;

public class TwitchChat extends PircBot {

    /*private static FileHandle fileHandle = Gdx.files.local("json/twitch.json");
    String json = fileHandle.readString();*/

    // TODO: @JohannesMols help me with this ^

    private List<ChatListener> listeners = new ArrayList<ChatListener>();

    public void addListener(ChatListener toAdd) {
        listeners.add(toAdd);
    }

    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {
        this.messageRecieved(channel, sender, login, hostname, message);
    }

    public void messageRecieved(String channel, String sender,
                                String login, String hostname, String message) {
        for (ChatListener l : listeners) {
            l.messageRecieved(channel, sender, login, hostname, message);
        }
    }

}