package Domain;

import java.util.Date;

public class Webcast extends ContentItem {
    private Integer speakerID;
    private String speaker;
    private String organisation;
    private int duration;
    private String url;

    public Webcast(int content_item_ID, String title, Date publicationDate, String status, String description, Integer speakerID, String speaker,  String organisation, int duration, String url){
        super(content_item_ID, title, status, publicationDate, description);
        this.speakerID = speakerID;
        this.setSpeaker(speaker);
        this.setOrganisation(organisation);
        this.setDuration(duration);
        this.setUrl(url);
    }

    public Integer getSpeakerID() {
        return speakerID;
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

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
