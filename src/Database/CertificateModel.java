package Database;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.*;


import Domain.Certificate;
import Domain.Enrollment;
import Domain.Student;

public class CertificateModel extends Conn{
    public CertificateModel(){
        // Initialize super class conn
        super();
    }

    public boolean createCertificate(Certificate certificate){
        // Create prepared statement
        String query = "INSERT INTO Certificate VALUES(?, ?, ?)";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setInt(1, certificate.getEnrollment().getID());
            stmt.setInt(2, certificate.getEmployee().getNumber());
            stmt.setInt(3, certificate.getGrade());

            // Execute statement
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch(Exception e) {
            System.out.format("Error while creating certificate (createCertificate): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return false;
    }

    public ArrayList<Certificate> getCertificatesStudent(Student student){
        // Create prepared statement
        String query = "SELECT * FROM Enrollment INNER JOIN Certificate ON Enrollment.ID = Certificate.Enrollment_ID WHERE Enrollment.Student_Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, student.getEmail());

            // Execute statement
            ResultSet rs = stmt.executeQuery();

            // Create arraylist certificates
            ArrayList<Certificate> certificates = new ArrayList<>();

            // Check if there is a result in the set
            while(rs.next()){
                new Certificate(
                    rs.getInt("ID"),
                    new Enrollment(rs.getString(""))
                );
            }

            // Return certificates
            return certificates;
        }
        catch(Exception e){
            System.out.format("Error while retrieving student (readStudent): %s", e.toString());
        }

        // Return null on error
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
