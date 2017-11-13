package itcom.gangstersquirrel.Twitch;

public interface ChatListener {
    void messageRecieved(String channel, String sender,
                              String login, String hostname, String message);
}