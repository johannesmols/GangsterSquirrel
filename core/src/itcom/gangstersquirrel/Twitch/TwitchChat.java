package itcom.gangstersquirrel.Twitch;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import itcom.gangstersquirrel.Tools.JSONFileCreator;
import org.jibble.pircbot.*;

public class TwitchChat extends PircBot {

    private FileHandle fileHandle;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<String> lastTenMessages = new ArrayList<String>();

    public void logMessage(String message) {
        if (lastTenMessages.size() < 10) {
            lastTenMessages.add(message);
        } else {
            lastTenMessages.remove(0);
            lastTenMessages.add(message);
        }
    }

    public List<String> getLog() {
        return lastTenMessages;
    }

    public TwitchCredentialsObject getTwitchCredentials() {
        // JSON file to store the twitch credentials
        fileHandle = Gdx.files.local("json/twitchcredentials.json");

        TwitchCredentialsObject defaultCredentials = new TwitchCredentialsObject(
                "irc.chat.twitch.tv",
                6667,
                "OAUTH",
                "#aalborguniversity"
        );

        if (fileHandle.exists()) {
            String json = fileHandle.readString();
            if (!json.trim().isEmpty()) {
                return deserializeTwitchCredentials(json);
            } else {
                System.err.println("JSON twitch credentials is empty, creating default credentials");
                serializeTwitchCredentials(defaultCredentials);
                return deserializeTwitchCredentials(json);
            }
        } else {
            serializeTwitchCredentials(defaultCredentials);
            return deserializeTwitchCredentials(fileHandle.readString());
        }
    }

    private void serializeTwitchCredentials(TwitchCredentialsObject twitchCredentials) {
        String json = gson.toJson(twitchCredentials);

        // Creates new JSON file, if it doesn't exist already
        if (JSONFileCreator.createEmptyJSONFileIfItDoesntExist(fileHandle)) {
            fileHandle.writeString(json, false); // false = overwrite instead of append
        }
    }

    private TwitchCredentialsObject deserializeTwitchCredentials(String json) {
        return gson.fromJson(json, TwitchCredentialsObject.class);
    }

    private List<ChatListener> listeners = new ArrayList<ChatListener>();

    public void addListener(ChatListener toAdd) {
        listeners.add(toAdd);
    }

    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {
        this.logMessage(message);
        this.messageReceived(channel, sender, login, hostname, message);
    }

    public void messageReceived(String channel, String sender,
                                String login, String hostname, String message) {
        for (ChatListener l : listeners) {
            l.messageReceived(channel, sender, login, hostname, message);
        }
    }

    /* ----- GETTERS AND SETTERS ----- */

    public String getUrl() {
        return getTwitchCredentials().getUrl();
    }

    public void setUrl(String url) {
        TwitchCredentialsObject tmp = getTwitchCredentials();
        tmp.setUrl(url);
        serializeTwitchCredentials(tmp);
    }

    public int getTwitchPort() {
        return getTwitchCredentials().getPort();
    }

    public void setPort(int port) {
        TwitchCredentialsObject tmp = getTwitchCredentials();
        tmp.setPort(port);
        serializeTwitchCredentials(tmp);
    }

    public String getOAuth() {
        return getTwitchCredentials().getOauth();
    }

    public void setOAuth(String oAuth) {
        TwitchCredentialsObject tmp = getTwitchCredentials();
        tmp.setOauth(oAuth);
        serializeTwitchCredentials(tmp);
    }

    public String getTwitchChannel() {
        return getTwitchCredentials().getChannel();
    }

    public void setTwitchChannel(String channel) {
        TwitchCredentialsObject tmp = getTwitchCredentials();
        tmp.setChannel(channel);
        serializeTwitchCredentials(tmp);
    }
}