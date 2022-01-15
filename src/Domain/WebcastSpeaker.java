package Domain;

public class WebcastSpeaker {
    private Integer ID;
    private String name;
    private String organisation;

    public WebcastSpeaker(Integer ID, String name, String organisation){
        this.ID = ID;
        this.setName(name);
        this.setOrganisation(organisation);
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getID() {
        return ID;
    }

    public String getSpeakerAndCompany(){
        return "Spreker: " + this.name + " Bedrijf:" + this.organisation;
    }
}
