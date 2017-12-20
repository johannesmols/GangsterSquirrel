package itcom.gangstersquirrel.Twitch;

/**
 * A class to define the data structure of an object that contains certain twitch credentials
 */
public class TwitchCredentialsObject {

    private String url;
    private int port;
    private String oauth;
    private String channel;

    public TwitchCredentialsObject(String url, int port, String oauth, String channel) {
        this.url = url;
        this.port = port;
        this.oauth = oauth;
        this.channel = channel;
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
