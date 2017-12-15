package itcom.gangstersquirrel.Twitch;

import itcom.gangstersquirrel.GameProgress.GameProgress;
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
        receivableMessages.add("Kreygasm");
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
            twitch.sendMessage(twitch.getTwitchCredentials().getChannel(), new GameProgress().getPlayerName() + " connected");

            System.out.println("Joining Twitch channel " + twitch.getTwitchCredentials().getChannel());
            twitch.joinChannel(twitch.getTwitchCredentials().getChannel());
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }
    }

    private static int getNumberOfThisMessagesInLog(List<String> list, String message) {
        int amt = 0;
        for (String tmpMessage : list) {
            if (tmpMessage.trim().equalsIgnoreCase(message)) {
                amt++;
            }
        }
        return amt;
    }

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
}
