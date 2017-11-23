package itcom.gangstersquirrel.Twitch;

import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class TwitchThread implements ChatListener {

    private TwitchChat twitch = new TwitchChat();

    public TwitchThread(boolean enabled)
    {
        if (enabled) {
            System.out.println("Connecting to Twitch ...");
            new Thread(new Runnable() {
                public void run(){
                    twitchConnect();
                }
            }).start();
        }
    }

    private void twitchConnect() {
        twitch.addListener(this);
        try {
            twitch.connect(
                    twitch.getTwitchCredentials().getUrl(),
                    twitch.getTwitchCredentials().getPort(),
                    twitch.getTwitchCredentials().getOauth()
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
    }
}
