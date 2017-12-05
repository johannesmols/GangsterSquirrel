package itcom.gangstersquirrel.Twitch;

import itcom.gangstersquirrel.MainGameClass;
import org.jibble.pircbot.IrcException;

import java.io.IOException;
import java.util.List;

public class TwitchThread implements ChatListener {

    private TwitchChat twitch = new TwitchChat();
    private MainGameClass mainGameClass;
    private TwitchCredentialsObject twitchCredentials = twitch.getTwitchCredentials();

    public static int listCount(List<String> list, String message) {
        int amt = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == message) {
                amt++;
            }
            else {
                amt = amt;
            }
        }
        return amt;
    }

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

    @Override
    public void messageReceived(String channel, String sender, String login, String hostname, String message) {
        System.out.println(sender + ": " + message);
        if (listCount(twitch.getLog(), "Kappa") >= 3) {
            // #TODO: Do something on Kappa
            System.out.println("Kappa kappa kappa chameleon");
        }
    }
}
