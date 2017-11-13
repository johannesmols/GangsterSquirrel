package itcom.gangstersquirrel.Twitch;

public interface ChatListener {
    void messageReceived(String channel, String sender,
                         String login, String hostname, String message);
}