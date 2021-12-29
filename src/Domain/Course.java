package Domain;

import java.util.ArrayList;

public class Course {
    private String name;
    private String subject;
    private String introduction;
    private String level;
    private ArrayList<ContentItem> contentItems;

    public Course(String name, String subject, ContentItem contentItem){
        this.name = name;
        this.subject = subject;
        this.contentItems = new ArrayList<ContentItem>();
        this.contentItems.add(contentItem);
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<ContentItem> getContentItems() {
        return this.contentItems;
    }

    public void setContentItems(ArrayList<ContentItem> contentItems) {
        this.contentItems = contentItems;
    }

}
