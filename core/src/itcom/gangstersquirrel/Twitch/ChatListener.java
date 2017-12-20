package itcom.gangstersquirrel.Twitch;

/**
 * An interface to implement a method called messageReceived to receive messages from Twitch chat
 */
public interface ChatListener {
    void messageReceived(String channel, String sender,
                         String login, String hostname, String message);
}