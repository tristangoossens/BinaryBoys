package Database;

import Domain.Course;
import Domain.Level;

import java.sql.*;
import java.util.ArrayList;

public class CourseModel extends Conn {

    private ContentItemModel contentItemModel = new ContentItemModel();

    // Initialize super class conn to create a database connection
    public CourseModel() {
        super();
    }

    // Create a course with the given domain object
    public boolean createCourse(Course course) {
        // Create prepared statement
        String query = "INSERT INTO Course VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            stmt.setString(3, course.getIntroduction());
            stmt.setString(4, course.getLevel().getValue());
            // Execute statement
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch (Exception e) {
            System.out.format("Error while creating Course (createCourse): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return false;
    }

    // Retrieve a singular course with its name
    public Course readCourse(String name) {
        String query = "SELECT * FROM Course WHERE Name = ?";

        // Initialize a prepared statement to prevent SQL injections
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set variable data
            stmt.setString(1, name);

            // Execute prepared statement
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(
                        rs.getString("Name"),
                        rs.getString("Subject"),
                        rs.getString("Introduction"),
                        Level.convertToEnum(rs.getString("Course_Level")),
                        contentItemModel.getContentItemsForCourse(name)
                        );
            }
        } catch (Exception e) {
            System.out.format("Error while retrieving course (readcourse): %s", e.toString());
        }

        // Return null on error (nothing is yet returned)
        return null;
    }

    // Retrieve a list of courses
    public ArrayList<Course> getCourses() {

        String query = "SELECT * FROM Course";
        ArrayList<Course> courses = new ArrayList<>();

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                courses.add(new Course(
                    rs.getString("Name"),
                    rs.getString("Subject"),
                    rs.getString("Introduction"),
                    Level.convertToEnum(rs.getString("Course_Level")),
                    contentItemModel.getContentItemsForCourse(rs.getString("Name"))
                ));
            }
            return courses;

        }catch (Exception e) {
            System.out.format("error while getting courses (getcourses): %s", e.toString());
        }

        return null;
    }

    // Update a course with the given domain object
    public boolean updateCourse(Course course) {
        String query = "UPDATE Course SET Subject = ?, Introduction = ?, Course_Level = ? WHERE Name = ?";

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            stmt.setString(1, course.getSubject());
            stmt.setString(2, course.getIntroduction());
            stmt.setString(3, course.getLevel().getValue());
            stmt.setString(4, course.getName());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.format("Error while updating course (updateCourse): %s", e.toString());
        }
        return false;
    }

    // Delete course with the given domain object
    public boolean deleteCourse(Course course) {
        // Making query to delete course
        String query = "DELETE FROM Course WHERE Name = ?";

        // Excecuting query
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            stmt.setString(1, course.getName());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.format("Error while deleting course (deleteCourse): %s", e.toString());
        }
        return false;
    }
}
