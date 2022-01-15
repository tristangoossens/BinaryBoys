package Domain;

import java.util.ArrayList;

public class Course {
    private String name;
    private String subject;
    private String introduction;
    private Level level;
    private ArrayList<ContentItem> contentItems;

    public Course(String name, String subject, String introduction, Level level, ArrayList<ContentItem> contentItems) {
        this.name = name;
        this.subject = subject;
        this.introduction = introduction;
        this.level = level;
        this.contentItems = new ArrayList<ContentItem>();
        this.contentItems.addAll(contentItems);
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

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public ArrayList<ContentItem> getContentItems() {
        return this.contentItems;
    }

    public void setContentItems(ArrayList<ContentItem> contentItems) {
        this.contentItems = contentItems;
    }

}
