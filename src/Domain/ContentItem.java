package Domain;

import java.util.Date;

public class ContentItem {
    private int ID;
    private String title;
    private Date publicationDate;
    private Status status;
    private String description;
    
    public ContentItem(int ID, String title, Status status, Date publicationDate, String description){
        this.setID(ID);
        this.setTitle(title);
        this.setStatus(status);
        this.setPublicationDate(publicationDate);
        this.setDescription(description);
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
