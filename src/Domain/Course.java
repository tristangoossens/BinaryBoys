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
}
