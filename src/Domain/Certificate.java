package Domain;

public class Certificate {
    private int id;
    private Enrollment enrollment;
    private Employee employee;

    public Certificate(int id, Enrollment enrollment, Employee employee) {
        this.id = id;
        this.enrollment = enrollment;
        this.employee = employee;
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

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
