package itcom.gangstersquirrel.Twitch;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import itcom.gangstersquirrel.Tools.JSONFileCreator;
import org.jibble.pircbot.*;

/**
 * Handles setting up the connection to Twitch by reading and writing to the relevant JSON file
 */
public class TwitchChat extends PircBot {

    private FileHandle fileHandle;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<String> lastTenMessages = new ArrayList<String>();

    /**
     * Add a received message to the logs
     * @param message the message
     */
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

    /**
     * Read the twitch credentials from the JSON file
     * @return the twitch credentials
     */
    public TwitchCredentialsObject getTwitchCredentials() {
        // JSON file to store the twitch credentials
        fileHandle = Gdx.files.local("json/twitchcredentials.json");

        TwitchCredentialsObject defaultCredentials = new TwitchCredentialsObject(
                false,
                "irc.chat.twitch.tv",
                6667,
                "",
                ""
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

    /**
     * Writes changes of the twitch credentials into a JSON file
     * @param twitchCredentials contains the new twitch credentials
     */
    private void serializeTwitchCredentials(TwitchCredentialsObject twitchCredentials) {
        String json = gson.toJson(twitchCredentials);

        // Creates new JSON file, if it doesn't exist already
        if (JSONFileCreator.createEmptyJSONFileIfItDoesntExist(fileHandle)) {
            fileHandle.writeString(json, false); // false = overwrite instead of append
        }
    }

    /**
     * Reads twitch credentials from a JSON file and returns it as a TwitchCredentialsObject
     * @param json the JSON string that will be deserialized
     * @return the twitch credentials
     */
    private TwitchCredentialsObject deserializeTwitchCredentials(String json) {
        return gson.fromJson(json, TwitchCredentialsObject.class);
    }

    private List<ChatListener> listeners = new ArrayList<ChatListener>();

    /**
     * Add a chat listener
     * @param toAdd the chat listener object
     */
    public void addListener(ChatListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Handles receiving a message
     * @param channel the channel where the message came from
     * @param sender the user that sent the message
     * @param login the login
     * @param hostname the hostname of the channel
     * @param message the actual message
     */
    public void onMessage(String channel, String sender,
                          String login, String hostname, String message) {
        this.logMessage(message);
        this.messageReceived(channel, sender, login, hostname, message);
    }

    /**
     * Handles receiving a message
     * @param channel the channel where the message came from
     * @param sender the user that sent the message
     * @param login the login
     * @param hostname the hostname of the channel
     * @param message the actual message
     */
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