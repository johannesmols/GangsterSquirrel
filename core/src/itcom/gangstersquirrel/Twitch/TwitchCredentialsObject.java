package itcom.gangstersquirrel.Twitch;

/**
 * A class to define the data structure of an object that contains certain twitch credentials
 */
public class TwitchCredentialsObject {

    private boolean enabled;
    private String url;
    private int port;
    private String oauth;
    private String channel;

    public TwitchCredentialsObject(boolean enabled, String url, int port, String oauth, String channel) {
        this.enabled = enabled;
        this.url = url;
        this.port = port;
        this.oauth = oauth;
        this.channel = channel;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
