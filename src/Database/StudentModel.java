package Database;

import java.sql.*;

public class StudentModel extends Conn {
    public StudentModel(){
        // Initialize super class conn
        super();
    }

    public ResultSet getStudents() {
        // Set query to exectute
        String query = "SELECT * FROM Student";
        ResultSet rs = null;

        // Create a prepared statement for the SQL query
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute the prepared query
            rs = stmt.executeQuery();
        } catch (Exception e) {
            // Catch any exeptions
            System.out.format("Error while getting students (getStudents): %s", e.toString());
        }

        // Return
        return rs;
    }
}
