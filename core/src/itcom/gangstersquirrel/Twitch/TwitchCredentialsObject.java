package itcom.gangstersquirrel.Twitch;

public class TwitchCredentialsObject {

    private String url;
    private int port;
    private String oauth;
    private String tag;

    public TwitchCredentialsObject(String url, int port, String oauth, String tag) {
        this.url = url;
        this.port = port;
        this.oauth = oauth;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
