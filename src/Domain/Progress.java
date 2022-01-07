package Domain;

public class Progress {
    private ContentItem contentItem;
    private Student student;
    private int percentage;

    public Progress(ContentItem contentItem, Student student, int percentage) {
        this.contentItem = contentItem;
        this.student = student;
        this.percentage = percentage;
    }

    public ContentItem getContentItem() {
        return this.contentItem;
    }

    public Student getStudent() {
        return this.student;
    }

    public int getPercentage() {
        return this.percentage;
    }

    public void setPercentage(int value){
        this.percentage = value;
    }
}
