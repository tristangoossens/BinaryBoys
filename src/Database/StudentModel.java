package Database;

import Domain.Gender;
import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentModel extends Conn {
    public StudentModel(){
        // Initialize super class conn
        super();
    }

    public boolean createStudent(Student student){
        // Create prepared statement
        String query = "INSERT INTO Student VALUES(?, ?, ? ,?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setString(1, student.getEmail());
            stmt.setString(2, student.getName());
            stmt.setDate(3, new java.sql.Date(student.getBirthDate().getTime()));
            stmt.setString(4, student.getGender().getValue());
            stmt.setString(5, student.getAddress());
            stmt.setString(6, student.getCity());
            stmt.setString(7, student.getPostalCode());
            stmt.setString(8, student.getCountry());

            // Execute statement
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch(Exception e) {
            System.out.format("Error while creating student (createStudent): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return false;
    }

    public Student readStudent(String email){
        // Create prepared statement
        String query = "SELECT * FROM Student WHERE Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, email);

            // Execute statement
            ResultSet rs = stmt.executeQuery();

            // Check if there is a result in the set
            if(rs.next()){
                return new Student(
                    rs.getString("Email"),
                    rs.getString("Name"),
                    rs.getDate("Birthdate"),
                    Gender.convertToEnum((rs.getString("Gender"))),
                    rs.getString("Address"),
                    rs.getString("PostalCode"),
                    rs.getString("City"),
                    rs.getString("Country")
                );
            }
        }
        catch(Exception e){
            System.out.format("Error while retrieving student (readStudent): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    public ArrayList<Student> getStudents() {
        // Set query to exectute
        String query = "SELECT * FROM Student";
        ArrayList<Student> students = new ArrayList<>();

        // Create a prepared statement for the SQL query
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute the prepared query
            ResultSet rs = stmt.executeQuery();

            // For every stident in the result set
            while(rs.next()){
                students.add(new Student(
                    rs.getString("Email"),
                    rs.getString("Name"),
                    rs.getDate("Birthdate"),
                    Gender.convertToEnum((rs.getString("Gender"))),
                    rs.getString("Address"),
                    rs.getString("PostalCode"),
                    rs.getString("City"),
                    rs.getString("Country"))
                );
            }

            // Return list of students
            return students;
        } catch (Exception e) {
            System.out.format("Error while getting students (getStudents): %s", e.toString());
        }

        // Return null when nothing is returned yet (error)
        return null;
    }

    public boolean updateStudent(Student student){
        // Set query to exectute
        String query = "UPDATE Student SET Email = ?, Name = ?, Birthdate = ?, Gender = ?, Address = ?, City = ?, PostalCode = ?, Country = ? WHERE Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, student.getEmail());
            stmt.setString(2, student.getName());
            stmt.setDate(3, new java.sql.Date(student.getBirthDate().getTime()));
            stmt.setString(4, student.getGender().getValue());
            stmt.setString(5, student.getAddress());
            stmt.setString(6, student.getCity());
            stmt.setString(7, student.getPostalCode());
            stmt.setString(8, student.getCountry());
            stmt.setString(9, student.getEmail());

            // Execute update query
            stmt.executeUpdate();

            // return true on success
            return true;
        }
        catch(Exception e){
            System.out.format("Error while updating student (updateStudent): %s", e.toString());
        }

        // Return false on error
        return false;
    }

    public boolean deleteStudent(String email){
        String query = "DELETE FROM Student WHERE Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, email);

            // Execute prepared statement
            stmt.executeUpdate();

            // Return true on success
            return true;
        }
        catch(Exception e){
            System.out.format("Error while deleting student (deleteStudent): %s", e.toString());
        }

        // Return false on error
        return false;
    }
}
