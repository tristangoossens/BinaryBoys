package Domain;

import java.util.Date;
import java.util.HashMap;

public class Enrollment {
    private Student student;
    private Course course;
    private Date date;
    private HashMap<ContentItem, Integer> progress = new HashMap<ContentItem, Integer>();


    public Enrollment(Student student, Course course, Date date) {
        this.student = student;
        this.course = course;
        this.date = date;
    }


    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "{" +
            " student='" + getStudent() + "'" +
            ", course='" + getCourse() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
