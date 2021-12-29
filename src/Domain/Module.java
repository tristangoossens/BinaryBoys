package Domain;

public class Module extends ContentItem {   
    private int version;
    private int orderNumber;
    private String contactPerson;

    public Module(int version, int orderNumber, String contactPerson){
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
