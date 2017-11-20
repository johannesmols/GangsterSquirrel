package itcom.gangstersquirrel.Twitch;

import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class TwitchThread extends Thread implements ChatListener {
    public TwitchChat twitch = new TwitchChat();

    @Override
    public void run()
    {
        twitch.addListener(this);
        try {
            twitch.connect(
                    twitch.getTwitchCredentials().getUrl(),
                    twitch.getTwitchCredentials().getPort(),
                    twitch.getTwitchCredentials().getOauth()
            );
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }
        twitch.joinChannel(twitch.getTwitchCredentials().getTag());
    }

    @Override
    public void messageReceived(String channel, String sender, String login, String hostname, String message) {
        System.out.println(sender + ": " + message);
    }
}
