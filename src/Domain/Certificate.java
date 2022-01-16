package Domain;

public class Certificate {
    private int id;
    private Enrollment enrollment;
    private Employee employee;
    private int grade;

    public Certificate(int id, Enrollment enrollment, Employee employee, int grade) {
        this.id = id;
        this.enrollment = enrollment;
        this.employee = employee;
        this.grade = grade;
    }

    public int getId() {
        return this.id;
    }

    public Enrollment getEnrollment() {
        return this.enrollment;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
