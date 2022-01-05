package Domain;

import java.util.Date;

public class ContentItem {
    private int ID;
    private String title;
    private Date publicationDate;
    private String status;
    private String description;
    
    public ContentItem(int ID, String title, Date publicationDate, String status, String description){
        this.setID(ID);
        this.setTitle(title);
        this.setPublicationDate(publicationDate);
        this.setStatus(status);
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


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
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
