package Student;

import java.sql.SQLException;
import java.util.ArrayList;
import Database.StudentModel;

public class Students {
    private ArrayList<Student> students = new ArrayList<>();

    public Students() throws SQLException {
        StudentModel dbConn = new StudentModel();

        while (dbConn.getStudents().next()) {
            Student newStudent = new Student(
                    dbConn.getStudents().getString("Email"),
                    dbConn.getStudents().getString("Name"),
                    dbConn.getStudents().getDate("Birthdate"),
                    dbConn.getStudents().getString("Gender"),
                    dbConn.getStudents().getString("Address"),
                    dbConn.getStudents().getString("City"),
                    dbConn.getStudents().getString("Country"));
            students.add(newStudent);
        }
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            sb.append(students.get(i).toString());
            sb.append(",");
        }

        return sb.toString();
    }

}
