package Database;

import Domain.Course;
import java.sql.*;
import java.util.ArrayList;

public class CourseModel extends Conn {

    private ContentItemModel contentItemModel = new ContentItemModel();

    public CourseModel() {
        super();
    }

    public boolean CreateCourse(Course course) {
        // Create prepared statement
        String query = "INSERT INTO Course VALUES(?, ?, ? , ?, ?)";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            stmt.setString(3, course.getIntroduction());
            stmt.setString(4, course.getLevel());
            stmt.setArray(5, (Array) course.getContentItems());

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

    public Course readCourse(String name) {
        String query = "SELECT * FROM Course WHERE Name = ?";

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            // Creating content item model to retrieve content item

            if (rs.next()) {
                return new Course(
                        rs.getString("Name"),
                        rs.getString("Subject"),
                        rs.getString("Introduction"),
                        rs.getString("Level"),
                        contentItemModel.getContentItemsForCourse(name)
                        );
            }
        } catch (Exception e) {
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
                    contentItemModel.getContentItemsForCourse(rs.getString("Name"))
                ));
            }
            return courses;

        }catch (Exception e) {
            System.out.format("error while getting courses (getcourses): %s", e.toString());
        }

        return null;
    }

    public boolean updateCourse(Course course) {
        String query = "UPDATE Course SET Subject = ?, Introduction = ?, Course_Level = ? WHERE Name = ?";

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            stmt.setString(1, course.getSubject());
            stmt.setString(2, course.getIntroduction());
            stmt.setString(3, course.getLevel());
            stmt.setString(4, course.getName());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.format("Error while updating course (updateCourse): %s", e.toString());
        }
        return false;
    }

    public boolean deleteCourse(Course course) {
        String query = "DELETE FROM Course WHERE Email = ?";

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
