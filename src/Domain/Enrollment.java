package Domain;

import java.util.Date;

public class Enrollment {
    private int ID;
    private Student student;
    private Course course;
    private Date date;


    public Enrollment(Student student, Course course, Date date, int ID) {
        this.student = student;
        this.course = course;
        this.date = date;
        this.ID = ID;
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

    public int getID(){
        return this.ID;
    }
}
