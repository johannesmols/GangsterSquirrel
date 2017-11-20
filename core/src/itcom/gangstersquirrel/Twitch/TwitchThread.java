package itcom.gangstersquirrel.Twitch;

import itcom.gangstersquirrel.MainGameClass;
import org.jibble.pircbot.IrcException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TwitchThread extends Thread implements ChatListener {
    private int startIdx, nThreads, maxIdx;
    public TwitchChat twitch = new TwitchChat();

    public TwitchThread(int s, int n, int m)
    {
        this.startIdx = s;
        this.nThreads = n;
        this.maxIdx = m;

    }

    @Override
    public void start() {
        /*twitch.addListener(this);
        try {
            twitch.connect(
                    twitch.getTwitchCredentials().getUrl(),
                    twitch.getTwitchCredentials().getPort(),
                    twitch.getTwitchCredentials().getOauth()
            );
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }
        twitch.joinChannel(twitch.getTwitchCredentials().getTag());*/
    }

    @Override
    public void run()
    {
        for(int i = this.startIdx; i < this.maxIdx; i += this.nThreads)
        {
            System.out.println("[ID " + this.getId() + "] " + i);
        }
    }

    @Override
    public void messageReceived(String channel, String sender, String login, String hostname, String message) {
        System.out.println(sender + ": " + message);
    }
}
