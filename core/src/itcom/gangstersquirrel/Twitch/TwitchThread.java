package itcom.gangstersquirrel.Twitch;

import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Settings.Settings;
import org.jibble.pircbot.IrcException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A separate thread to handle the Twitch connectivity
 */
public class TwitchThread implements ChatListener {

    private TwitchChat twitch = new TwitchChat();
    private MainGameClass mainGameClass;
    private TwitchCredentialsObject twitchCredentials = twitch.getTwitchCredentials();

    private ArrayList<String> receivableMessages = new ArrayList<>();

    /**
     * Starts the new thread
     * @param enabled if Twitch is enabled
     * @param mainGameClass the main game class
     */
    public TwitchThread(MainGameClass mainGameClass)
    {
        if (twitchCredentials.isEnabled()) {
            System.out.println("Connecting to Twitch ...");
            new Thread(new Runnable() {
                public void run(){
                    twitchConnect();
                }
            }).start();
        }
        this.mainGameClass = mainGameClass;

        // Set up messages that can be used as an action in the game
        receivableMessages.add("Kreygasm");
    }

    /**
     * Connect with the twitch channel
     */
    private void twitchConnect() {
        twitch.addListener(this);
        try {
            twitch.connect(
                    twitchCredentials.getUrl(),
                    twitchCredentials.getPort(),
                    twitchCredentials.getOauth()
            );
            System.out.println("Connected to Twitch");

            System.out.println("Joining Twitch channel " + twitch.getTwitchCredentials().getChannel());
            twitch.joinChannel(twitch.getTwitchCredentials().getChannel());
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the number of messages in a list that contains a specific message
     * @param list the list
     * @param message the message
     * @return the number of messages
     */
    private static int getNumberOfThisMessagesInLog(List<String> list, String message) {
        int amt = 0;
        for (String tmpMessage : list) {
            if (tmpMessage.trim().equalsIgnoreCase(message)) {
                amt++;
            }
        }
        return amt;
    }

    /**
     * Handles receiving a message
     * @param channel the channel where the message came from
     * @param sender the user that sent the message
     * @param login the login
     * @param hostname the hostname of the channel
     * @param message the actual message
     */
    @Override
    public void messageReceived(String channel, String sender, String login, String hostname, String message) {
        System.out.println(sender + ": " + message);
        System.out.println("Log size: " + twitch.getLog().size());

        for (String receivableMessage : receivableMessages) {
            if (getNumberOfThisMessagesInLog(twitch.getLog(), receivableMessage) >= 3) {
                mainGameClass.receiveActionFromTwitch(receivableMessage);
            }
        }
    }

    /**
     * @return the twitch credentials
     */
    public TwitchCredentialsObject getTwitchCredentials() {
        return twitchCredentials;
    }

    /**
     * @return the twitch chat instance
     */
    public TwitchChat getTwitch() {
        return twitch;
    }
}
