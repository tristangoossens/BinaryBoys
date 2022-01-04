package Database;

import Domain.Course;
import java.sql.*;
import java.util.ArrayList;

public class CourseModel extends Conn {
    public CourseModel() {
        super();
    }

    public boolean CreateCourse(Course course) {
        // Create prepared statement
        String query = "INSERT INTO Course VALUES(?, ?, ? , ?, ?)";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            stmt.setString(3, course.getIntroduction() );
            stmt.setString(4, course.getLevel());
            stmt.setArray(5, (Array) course.getContentItems());

            // Execute statement
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch(Exception e) {
            System.out.format("Error while creating Course (createCourse): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return false;
    }

    public Course readCourse(String name) {
        String query = "SELECT * FROM Course WHERE Name = ?";

        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new Course(
                rs.getString("Name"),
                rs.getString("Subject"),
                rs.getString("Introduction"),
                rs.getString("Level"),
                rs.getContentItems("ContentItems")
                );
            }
        }catch (Exception e){
            System.out.format("Error while retrieving course (readcourse): %s", e.toString());
        }

        return null;
    }


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
                    rs.getString("Level"),
                    rs.getContentItems("ContentItems")
                );
            }
            return courses;

        }catch (Exception e) {
            System.out.format("error while getting courses (getcourses): %s", e.toString());
        }
    }

    public boolean updateCourse(Course course) {
        String query = "UPDATE Student SET Name = ?, Subject = ?, Introduction = ?, Level = ?, ContentItems = ? WHERE Name = ?";

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            stmt.setString(3, course.getIntroduction());
            stmt.setString(4, course.getLevel());
            stmt.setContentItems(5, course.getContentItems());

            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            System.out.format("Error while updating course (updateCourse): %s", e.toString());
        }
        return false;
    }

    public boolean deleteCourse(String name) {
        String query = "DELETE FROM Course WHERE Email = ?";

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            stmt.setString(1, name);

            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            System.out.format("Error while deleting course (deleteCourse): %s", e.toString());
        }
        return false;
    }
}
