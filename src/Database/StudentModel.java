package Database;

import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentModel extends Conn {
    public StudentModel(){
        // Initialize super class conn
        super();
    }

    public ArrayList<Student> getStudents() {
        // Set query to exectute
        String query = "SELECT * FROM Student";
        ResultSet rs = null;
        
        ArrayList<Student> students = new ArrayList<>();

        // Create a prepared statement for the SQL query
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute the prepared query
            rs = stmt.executeQuery();

            while(rs.next()){
                students.add(new Student(
                    rs.getString("Email"),
                    rs.getString("Name"),
                    rs.getDate("Birthdate"),
                    rs.getString("Gender"),
                    rs.getString("Address"),
                    rs.getString("City"),
                    rs.getString("Country"))
                );
            }

            return students;
        } catch (Exception e) {
            System.out.format("Error while getting students (getStudents): %s", e.toString());
        }

        // Return null when nothing is returned yet (error)
        return null;
    }
}
