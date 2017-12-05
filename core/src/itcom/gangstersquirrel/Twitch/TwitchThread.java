package itcom.gangstersquirrel.Twitch;

import itcom.gangstersquirrel.MainGameClass;
import org.jibble.pircbot.IrcException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwitchThread implements ChatListener {

    private TwitchChat twitch = new TwitchChat();
    private MainGameClass mainGameClass;
    private TwitchCredentialsObject twitchCredentials = twitch.getTwitchCredentials();

    private ArrayList<String> receivableMessages = new ArrayList<>();

    public TwitchThread(boolean enabled, MainGameClass mainGameClass)
    {
        if (enabled) {
            System.out.println("Connecting to Twitch ...");
            new Thread(new Runnable() {
                public void run(){
                    twitchConnect();
                }
            }).start();
        }
        this.mainGameClass = mainGameClass;

        // Set up messages that can be used as an action in the game
        receivableMessages.add("Kappa");
    }

    private void twitchConnect() {
        twitch.addListener(this);
        try {
            twitch.connect(
                    twitchCredentials.getUrl(),
                    twitchCredentials.getPort(),
                    twitchCredentials.getOauth()
            );
            System.out.println("Connected to Twitch");
            twitch.sendMessage(twitch.getTwitchCredentials().getChannel(), "Connected");

            System.out.println("Joining Twitch channel " + twitch.getTwitchCredentials().getChannel());
            twitch.joinChannel(twitch.getTwitchCredentials().getChannel());
            if (twitch.getChannels().length > 0) {
                System.out.println("Joined Twitch channel successfully");
            }
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }
    }

    private static int getNumberOfThisMessagesInLog(List<String> list, String message) {
        int amt = 0;
        for (String tmpMessage : list) {
            if (tmpMessage.equalsIgnoreCase(message)) {
                amt++;
            }
        }
        return amt;
    }

    @Override
    public void messageReceived(String channel, String sender, String login, String hostname, String message) {
        System.out.println(sender + ": " + message);
        System.out.println(twitch.getLog().size());

        for (String receivableMessage : receivableMessages) {
            if (getNumberOfThisMessagesInLog(twitch.getLog(), receivableMessage) >= 3) {
                switch (receivableMessage) {
                    default:
                        break;
                }
            }
        }
    }
}
