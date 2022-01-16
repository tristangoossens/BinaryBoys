package Database;

import Domain.Course;
import Domain.Enrollment;
import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class EnrollmentModel extends Conn {

    private StudentModel studentModel = new StudentModel();
    private CourseModel courseModel = new CourseModel();

    // Initialize super class conn to set database connection
    public EnrollmentModel() {
        super();
    }

    // Create a enrollment with the given domain object
    public boolean createEnrollment(Enrollment enrollment) {
        String query = "INSERT INTO Enrollment VALUES(?, ?, ?)";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setString(1, enrollment.getCourse().getName());
            stmt.setString(2, enrollment.getStudent().getEmail());
            stmt.setDate(3, new java.sql.Date(enrollment.getDate().getTime()));

            // Execute statement
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch (Exception e) {
            System.out.format("Error while creating enrollment (createEnrollment): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return false;
    }

    // Retrieve a single enrollment with the given domain objects
    public Enrollment readEnrollment(Enrollment enrollment, Student student, Course course) {
        // Create prepared statement
        String query = "SELECT Enrollment.Enrollment_Date FROM Enrollment WHERE ID = ?";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setInt(1, enrollment.getID());

            // Execute statement
            ResultSet rs = stmt.executeQuery();

            // Check if there is a result in the set
            if (rs.next()) {
                return new Enrollment(
                        student,
                        course,
                        rs.getDate("Enrollment_Date"),
                        rs.getInt("ID")
                        );
            }
        } catch (Exception e) {
            System.out.format("Error while retrieving enrollment (readEnrollment): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Retrieve a list of enrollments
    public ArrayList<Enrollment> getEnrollments() {
        // Set query to exectute
        String query = "SELECT * FROM Enrollment";
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        // Create a prepared statement for the SQL query
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute the prepared query
            ResultSet rs = stmt.executeQuery();

            // For every stident in the result set
            while (rs.next()) {
                enrollments.add(new Enrollment(
                        studentModel.readStudent(rs.getString("Student_Email")),
                        courseModel.readCourse(rs.getString("Course_Name")),
                        rs.getDate("Enrollment_Date"),
                        rs.getInt("ID")
                        ));
            }

            // Return list of enrollments
            return enrollments;
        } catch (Exception e) {
            System.out.format("Error while getting enrollments (getEnrollments): %s", e.toString());
        }

        // Return null when nothing is returned yet (error)
        return null;
    }

    // Update an enrollment with the given domain object
    public boolean updateEnrollment(Enrollment enrollment) {
        // Set query to exectute
        String query = "UPDATE Enrollment SET Course_Name = ?, Student_Email = ?, Enrollment_Date = ? WHERE ID = ?";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setString(1, enrollment.getCourse().getName());
            stmt.setString(2, enrollment.getStudent().getEmail());
            stmt.setDate(3, new java.sql.Date(enrollment.getDate().getTime()));
            stmt.setInt(4, enrollment.getID());

            // Execute update query
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch (Exception e) {
            System.out.format("Error while updating enrollment (updateEnrollment): %s", e.toString());
        }

        // Return false on error
        return false;
    }

    // Delete an enrollment with the given domain object
    public boolean deleteEnrollment(Enrollment enrollment) {
        String query = "DELETE FROM Enrollment WHERE ID = ?";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setInt(1, enrollment.getID());
            // Execute prepared statement
            stmt.executeUpdate();

            // Return true on success
            return true;
        } catch (Exception e) {
            System.out.format("Error while deleting enrollment (deleteEnrollment): %s", e.toString());
        }

        // Return false on error
        return false;
    }
}