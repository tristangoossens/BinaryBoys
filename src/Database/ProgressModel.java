package Database;

import Domain.Progress;
import Domain.Student;

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

    public ArrayList<Progress> readProgressStudent(Student student){
        // Query to retrieve all content items for a course
        String query = "SELECT * FROM Progress WHERE Student_Email = ?";

        // Creating content item model to retrieve content item
        ContentItemModel contentItemModel = new ContentItemModel();

        ArrayList<Progress> progress = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        try(PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setString(1, student.getEmail());

            // Execute query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                progress.add(new Progress(
                    contentItemModel.getContentItem(rs.getInt("Content_Item_ID")),
                    student, 
                    rs.getInt("Percentage")));
            }
            // Return list of progress
            return progress;
        } 
        catch(Exception e){
            System.out.format("Error while retrieving progress student (readProgressStudent): %s", e.toString());
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
}
