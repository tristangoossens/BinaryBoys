package Database;

import Domain.Course;
import Domain.Enrollment;
import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class EnrollmentModel extends Conn{
    public EnrollmentModel() {
        // Initialize super class conn
        super();
    }

    public boolean createEnrollment(Enrollment enrollment){
        String query = "INSERT INTO Enrollment VALUES(?, ?, ?)";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setString(1, enrollment.getCourse().getName());
            stmt.setString(2, enrollment.getStudent().getEmail());
            stmt.setDate(3, new java.sql.Date(enrollment.getDate().getTime()));

            // Execute statement
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch(Exception e) {
            System.out.format("Error while creating enrollment (createEnrollment): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return false;
    }

    public Enrollment readEnrollment(Student student, Course course){
        // Create prepared statement
        String query = "SELECT Enrollment.Enrollment_Date FROM Enrollment WHERE Course_Name = ? AND Student_Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, course.getName());
            stmt.setString(2, student.getEmail());

            // Execute statement
            ResultSet rs = stmt.executeQuery();

            // Check if there is a result in the set
            if(rs.next()){
                return new Enrollment(
                    student,
                    course,
                    rs.getDate("Enrollment_Date")
                );
            }
        }
        catch(Exception e){
            System.out.format("Error while retrieving enrollment (readEnrollment): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    /*public ArrayList<Enrollment> getEnrollments(StudentModel studentmodel, CourseModel coursemodel) {
        // Set query to exectute
        String query = "SELECT * FROM Enrollment";
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        // Create a prepared statement for the SQL query
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute the prepared query
            ResultSet rs = stmt.executeQuery();

            ArrayList<Student> students = studentmodel.getStudents();
            ArrayList<Course> courses = coursemodel.getCourses();

            int count = 0;
            // For every stident in the result set
            while(rs.next()){
                enrollments.add(new Enrollment(
                    students.get(count),
                    courses.get(count),
                    rs.getDate("Enrollment_Date")
                ));
                count++;
            }

            // Return list of enrollments
            return enrollments;
        } catch (Exception e) {
            System.out.format("Error while getting enrollments (getEnrollments): %s", e.toString());
        }

        // Return null when nothing is returned yet (error)
        return null;
    }*/

    public boolean updateEnrollment(Enrollment enrollment){
        // Set query to exectute
        String query = "UPDATE Enrollment SET Course_Name = ?, Student_Email = ?, Enrollment_Date = ? WHERE Course_Name = ? AND Student_Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, enrollment.getCourse().getName());
            stmt.setString(2, enrollment.getStudent().getEmail());
            stmt.setDate(3, new java.sql.Date(enrollment.getDate().getTime()));
            stmt.setString(4, enrollment.getCourse().getName());
            stmt.setString(5, enrollment.getStudent().getEmail());

            // Execute update query
            stmt.executeUpdate();

            // return true on success
            return true;
        }
        catch(Exception e){
            System.out.format("Error while updating enrollment (updateEnrollment): %s", e.toString());
        }

        // Return false on error
        return false;
    }

    public boolean deleteEnrollment(Enrollment enrollment){
        String query = "DELETE FROM Enrollment WHERE Course_Name = ? AND Student_Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, enrollment.getCourse().getName());
            stmt.setString(2, enrollment.getStudent().getEmail());

            // Execute prepared statement
            stmt.executeUpdate();

            // Return true on success
            return true;
        }
        catch(Exception e){
            System.out.format("Error while deleting enrollment (deleteEnrollment): %s", e.toString());
        }

        // Return false on error
        return false;
    }
}