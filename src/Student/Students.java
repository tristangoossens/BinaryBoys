package Student;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.text.html.FormView;

import Database.StudentModel;

public class Students {
    HashMap<String, Student> students = new HashMap<>();

    public Students() throws SQLException {
        StudentModel dbConn = new StudentModel();

        while(dbConn.getStudents().next()){
            Student newStudent = new Student(
                dbConn.getStudents().getString("Email"),
                dbConn.getStudents().getString("Name"),
                dbConn.getStudents().getDate("Birthdate"),
                dbConn.getStudents().getString("Gender"),
                dbConn.getStudents().getString("Address"),
                dbConn.getStudents().getString("City"),
                dbConn.getStudents().getString("Country"));
            students.put(dbConn.getStudents().getString("Email"), newStudent);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < students.size(); i++){
            sb.append(students.get(i).toString());
            sb.append(",");
        }
        return sb.toString();
    }

}
