package Domain;

public class Webcast extends ContentItem {
    private String speaker;
    private String organisation;
    private int duration;
    private String url;

    public Webcast(String speaker, String organisation, int duration, String url){
        this.setSpeaker(speaker);
        this.setOrganisation(organisation);
        this.setDuration(duration);
        this.setUrl(url);
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
