package Domain;

import java.util.Date;

public class Webcast extends ContentItem {
    private WebcastSpeaker speaker;
    private int duration;
    private String url;

    public Webcast(int content_item_ID, String title, Date publicationDate, String status, String description, WebcastSpeaker speaker, int duration, String url){
        super(content_item_ID, title, status, publicationDate, description);
        this.setSpeaker(speaker);
        this.setDuration(duration);
        this.setUrl(url);
    }

    public WebcastSpeaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(WebcastSpeaker speaker) {
        this.speaker = speaker;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
