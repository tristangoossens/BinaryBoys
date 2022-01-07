package Domain;

import java.util.Date;

public class Module extends ContentItem {   
    private double version;
    private int orderNumber;
    private String contactPerson;

    public Module(int Content_item_ID, String title, Date publicationDate, String status, String description, double version, int orderNumber, String contactPerson){
        super(Content_item_ID, title, status, publicationDate, description);

        this.setVersion(version);
        this.setOrderNumber(orderNumber);
        this.setContactPerson(contactPerson);
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}
