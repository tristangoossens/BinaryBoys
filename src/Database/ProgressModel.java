package Database;

import Domain.Progress;
import Domain.Status;
import Domain.Student;
import Domain.Webcast;
import Domain.WebcastSpeaker;
import Domain.Module;
import Domain.ModuleContactPerson;

import java.sql.*;
import java.util.ArrayList;

public class ProgressModel extends Conn{
    public ProgressModel(){
        // Initialize super class conn
        super();
    }

    public boolean createProgress(Progress progress){
        // Create prepared statement
        String query = "INSERT INTO Progress VALUES(?, ?, ?)";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setInt(1, progress.getContentItem().getID());
            stmt.setString(2, progress.getStudent().getEmail());
            stmt.setInt(3, progress.getPercentage());

            // Execute statement
            stmt.executeUpdate();

            // return true on success
            return true;
        } catch(Exception e) {
            System.out.format("Error while creating progress (createProgress): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return false;
    }

    public ArrayList<Progress> readProgresStudent(Student student, char webcastOrModule){
        // Query to retrieve all content items for a course
        String query = "SELECT * FROM Progress WHERE Student_Email = ?";

        ArrayList<Progress> progress = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setString(1, student.getEmail());

            // Execute query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if(webcastOrModule == 'w'){
                    if(getWebcastForProgress(rs.getInt("Content_Item_ID")) != null){
                        progress.add(new Progress(
                            getWebcastForProgress(rs.getInt("Content_Item_ID")),
                            student, 
                            rs.getInt("Percentage")));
                    }
                } else{
                    if(getModuleForProgress(rs.getInt("Content_Item_ID")) != null){
                        progress.add(new Progress(
                            getModuleForProgress(rs.getInt("Content_Item_ID")),
                            student, 
                            rs.getInt("Percentage")));
                    }
                }
            }
            // Return list of progress
            return progress;
        } 
        catch(Exception e){
            System.out.format("Error while retrieving progress student (readProgresWebcastsStudent): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    public boolean updateProgressStudent(Progress progress){
        // Set query to exectute
        String query = "UPDATE Progress SET Percentage = ? WHERE Content_Item_ID = ? AND Student_Email = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setInt(1, progress.getPercentage());
            stmt.setInt(2, progress.getContentItem().getID());
            stmt.setString(3, progress.getStudent().getEmail());

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

    public boolean deleteProgressStudent(Progress progress){
        String query = "DELETE FROM Progress WHERE Email_Student = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setString(1, progress.getStudent().getEmail());

            // Execute prepared statement
            stmt.executeUpdate();

            // Return true on success
            return true;
        }
        catch(Exception e){
            System.out.format("Error while deleting progress student (deleteProgressStudent): %s", e.toString());
        }

        // Return false on error
        return false;
    }

    public Webcast getWebcastForProgress(Integer content_Item_ID){
        // Query to retrieve all webcasts for a course
        String query = "SELECT * FROM Content_Item AS CI INNER JOIN Webcast AS W ON CI.ID = W.Content_Item_ID INNER JOIN Webcast_Speaker AS WS ON W.Webcast_Speaker_ID = WS.ID WHERE CI.ID = ?";

        // Create a prepared statement to prevent SQL injections
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setInt(1, content_Item_ID);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set
            if(rs.next()){
               return new Webcast(
                    rs.getInt("ID"),
                    rs.getString("Title"),
                    rs.getDate("Publication_Date"),
                    Status.convertToEnum(rs.getString("Status")),
                    rs.getString("Description"),
                    new WebcastSpeaker(rs.getInt("Webcast_Speaker_ID"), rs.getString("Name"), rs.getString("Organisation")),
                    rs.getInt("Duration"), 
                    rs.getString("URL"));
            }

        } catch(Exception e) {
            System.out.format("Error while retrieving webcasts for progress (getWebcastForProgress): %s", e.toString());
        }

        // Return null on error (code reached catch block)
        return null;
    }
    

    public Module getModuleForProgress(Integer content_Item_ID){
        // Query to retrieve all content items for a course
        String query = "SELECT * FROM Content_Item AS CI INNER JOIN Module AS M ON CI.ID = M.Content_Item_ID INNER JOIN Module_Person AS MP ON M.Module_Person_Email = MP.Email WHERE CI.ID = ?";

        // Create a prepared statement to prevent SQL injections
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setInt(1, content_Item_ID);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set
            if(rs.next()){
                return new Module(
                    rs.getInt("ID"),
                    rs.getString("Title"),
                    rs.getDate("Publication_Date"),
                    Status.convertToEnum(rs.getString("Status")),
                    rs.getString("Description"),
                    rs.getDouble("Version"), 
                    rs.getInt("Sequence_Number"), 
                    new ModuleContactPerson(rs.getString("Name"), rs.getString("Email")));
            }

        } catch(Exception e) {
            System.out.format("Error while retrieving modules for progress (getModuleForProgress): %s", e.toString());
        }

        // Return nothing on error (null)
        return null;
    }

    public boolean deleteProgressContentItem(Integer content_Item_ID){
        String query = "DELETE FROM Progress WHERE Content_Item_ID = ?";
        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            // Set data in prepared statement
            stmt.setInt(1, content_Item_ID);

            // Execute prepared statement
            stmt.executeUpdate();

            // Return true on success
            return true;
        }
        catch(Exception e){
            System.out.format("Error while deleting progress contentitem (deleteProgressContentItem): %s", e.toString());
        }

        // Return false on error
        return false;
    }
}
