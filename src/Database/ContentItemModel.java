package Database;

import java.sql.*;
import java.util.ArrayList;

import Domain.Module;
import Domain.Webcast;
import Domain.Course;

public class ContentItemModel extends Conn {
    public ContentItemModel(){
        // Initialize super class where database connection is made
        super();
    }

    public ArrayList<Module> getModulesForCourse(Course course){
        // Query to retrieve all content items for a course
        String query = "SELECT * FROM Content_Item AS CI INNER JOIN Module AS M ON CI.ID = M.Content_Item_ID INNER JOIN Module_Person AS MP ON M.Module_Person_Email = MP.Email WHERE CI.Course_Name = ?";

        // Create an arraylist to store the retrieved data
        ArrayList<Module> modules = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setString(1, course.getName());

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set
            while(rs.next()){
                modules.add(new Module(
                    rs.getInt("ID"),
                    rs.getString("Title"),
                    rs.getDate("Publication_Date"),
                    rs.getString("Status"),
                    rs.getString("Description"),
                    rs.getDouble("Version"), 
                    rs.getInt("Sequence_Number"), 
                    rs.getString("Email")));
            }

            return modules;
        } catch(Exception e) {
            System.out.format("Error while retrieving modules for course (getModulesForCourse): %s", e.toString());
        }

        // Return nothing on error (null)
        return null;
    }

    public ArrayList<Webcast> getWebcastsForCourse(Course course){
        // Query to retrieve all webcasts for a course
        String query = "SELECT * FROM Content_Item AS CI INNER JOIN Webcast AS W ON CI.ID = W.Content_Item_ID INNER JOIN Webcast_Speaker AS WS ON W.Webcast_Speaker_ID = WS.ID WHERE CI.Course_Name = ?";

        // Create an arraylist to store the retrieved data
        ArrayList<Webcast> webcasts = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setString(1, course.getName());

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set
            while(rs.next()){
                webcasts.add(new Webcast(
                    rs.getInt("ID"),
                    rs.getString("Title"),
                    rs.getDate("Publication_Date"),
                    rs.getString("Status"),
                    rs.getString("Description"),
                    rs.getString("Name"), 
                    rs.getString("Organisation"), 
                    rs.getInt("Duration"), 
                    rs.getString("URL")));
            }

            return webcasts;
        } catch(Exception e) {
            System.out.format("Error while retrieving webcasts for course (getWebcastsForCourse): %s", e.toString());
        }

        // Return null on error (code reached catch block)
        return null;
    }

    public boolean deleteContentItem(int ID){
        String query = "DELETE FROM Content_Item WHERE ID = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setInt(1, ID);

            // Execute prepared statement
            stmt.executeUpdate();

            // Return true on success
            return true;
        }
        catch(Exception e){
            System.out.format("Error while deleting content item (deleteContentItem): %s", e.toString());
        }

        // Return false on error
        return false;
    }
}
