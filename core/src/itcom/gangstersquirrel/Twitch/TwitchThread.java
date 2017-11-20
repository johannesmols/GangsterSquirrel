package itcom.gangstersquirrel.Twitch;

import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class TwitchThread extends Thread implements ChatListener {
    public TwitchChat twitch = new TwitchChat();

    @Override
    public void run()
    {
        System.out.println("connecting...");
        new Thread(new Runnable() {
            public void run(){
                twitchConnect();
            }
        }).start();
    }

    public void twitchConnect() {
        twitch.addListener(this);
        try {
            twitch.connect(
                    twitch.getTwitchCredentials().getUrl(),
                    twitch.getTwitchCredentials().getPort(),
                    twitch.getTwitchCredentials().getOauth()
            );
            System.out.println("connected");

            System.out.println("joining channel " + twitch.getTwitchCredentials().getTag());
            twitch.joinChannel(twitch.getTwitchCredentials().getTag());
            System.out.println("joined successfully");
        } catch (IOException | IrcException e) {
            e.printStackTrace();
            System.out.println("connection failed");
        }
    }

    @Override
    public void messageReceived(String channel, String sender, String login, String hostname, String message) {
        System.out.println(sender + ": " + message);
    }
}
