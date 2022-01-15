package Database;

import java.sql.*;
import java.util.ArrayList;

import Domain.Module;
import Domain.ModuleContactPerson;
import Domain.Webcast;
import Domain.WebcastSpeaker;
import Domain.ContentItem;
import Domain.Course;

public class ContentItemModel extends Conn {
    public ContentItemModel() {
        // Initialize super class where database connection is made
        super();
    }

    public ArrayList<ModuleContactPerson> getContactPersons() {
        // Create prepared statement
        String query = "SELECT * FROM Module_Person";

        // Create list for contact person emails
        ArrayList<ModuleContactPerson> persons = new ArrayList<>();

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute statement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                persons.add(new ModuleContactPerson(rs.getString("Name"), rs.getString("Email")));
            }

            // return list of names
            return persons;
        } catch (Exception e) {
            System.out.format("Error while retrieving contact persons (getContactPersons): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return null;
    }

    public ArrayList<String> getWebcastSpeakers() {
        // Create statement
        String query = "SELECT * FROM Webcast_Speaker";

        // Create list for contact person emails
        ArrayList<String> speakers = new ArrayList<>();

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute statement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                speakers.add(String.format("Spreker: %s Bedrijf: %s", rs.getString("Name"), rs.getString("Organisation")));
            }

            // return list of names
            return speakers;
        } catch (Exception e) {
            System.out.format("Error while retrieving webcast speakers (getWebcastSpeakers): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return null;
    }

    public ModuleContactPerson getContactPersonWithEmail(String email) {
        // Create prepared statement
        String query = "SELECT * FROM Module_Person WHERE Email = ?";

        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set variable data
            stmt.setString(1, email);

            // Execute statement
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ModuleContactPerson(rs.getString("Name"), rs.getString("Email"));
            }
        } catch (Exception e) {
            System.out.format("Error while retrieving contact person emails names (getCourseNames): %s", e.toString());
        }

        // return false on error (nothing is yet returned)
        return null;
    }

    public boolean createModule(Module module, Course course) {
        String contentItemQuery = "INSERT INTO Content_Item VALUES(?, ?, ? ,?, ?)";
        String moduleQuery = "INSERT INTO Module VALUES(?, ? ,?, ?)";

        // Create a prepared statement to prevent SQL injections
        try (PreparedStatement stmt = conn.prepareStatement(contentItemQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement moduleStmt = conn.prepareStatement(moduleQuery)) {
            // Set auto commit off so the executed queries are executes in a transaction.
            conn.setAutoCommit(false);

            // Set data in prepared statement.
            stmt.setString(1, course.getName());
            stmt.setString(2, module.getTitle());
            stmt.setString(3, module.getStatus());
            stmt.setDate(4, new java.sql.Date(module.getPublicationDate().getTime()));
            stmt.setString(5, module.getDescription());

            // Execute first prepared statement
            stmt.executeUpdate();

            // Get the generated key and use it in the next insert
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int insertedID = rs.getInt(1);

                // Set data for second prepared statement
                moduleStmt.setInt(1, insertedID);
                moduleStmt.setString(2, module.getContactPerson().getEmail());
                moduleStmt.setInt(3, module.getOrderNumber());
                moduleStmt.setDouble(4, module.getVersion());

                // Execute the prepared query
                moduleStmt.executeUpdate();

                // Commit changes on success
                conn.commit();
                conn.setAutoCommit(true);

                // Return true on success
                return true;
            }
        } catch (Exception e) {
            try {
                // Error! Rolling the transaction
                conn.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            System.out.format("Error while inserting module (createModule): %s", e.toString());
        }

        // Return false on error (nothing is returned)
        return false;
    }

    public boolean createWebcast(Webcast webcast, String courseName) {
        String contentItemQuery = "INSERT INTO Content_Item VALUES(?, ?, ? ,?, ?)";
        String moduleQuery = "INSERT INTO Webcast VALUES(?, ? ,?, ?)";

        // Create a prepared statement to prevent SQL injections
        try (PreparedStatement stmt = conn.prepareStatement(contentItemQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement moduleStmt = conn.prepareStatement(moduleQuery)) {
            // Set auto commit off so the executed queries are executes in a transaction.
            conn.setAutoCommit(false);

            // Set data in prepared statement.
            stmt.setString(1, courseName);
            stmt.setString(2, webcast.getTitle());
            stmt.setString(3, webcast.getStatus());
            stmt.setDate(4, new java.sql.Date(webcast.getPublicationDate().getTime()));
            stmt.setString(5, webcast.getDescription());

            // Execute first prepared statement
            stmt.executeUpdate();

            // Get the generated key and use it in the next insert
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int insertedID = rs.getInt(1);

                // Set data for second prepared statement
                moduleStmt.setInt(1, insertedID);
                moduleStmt.setInt(2, webcast.getSpeaker().getID());
                moduleStmt.setString(3, webcast.getUrl());
                moduleStmt.setInt(4, webcast.getDuration());

                // Execute the prepared query
                moduleStmt.executeUpdate();

                // Commit changes on success
                conn.commit();
                conn.setAutoCommit(true);

                // Return true on success
                return true;
            }
        } catch (Exception e) {
            try {
                // Error! Rolling the transaction
                conn.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            System.out.format("Error while inserting webcast (createWebcast): %s", e.toString());
        }

        // Return false on error (nothing is returned)
        return false;
    }

    public ArrayList<Module> getModulesForCourse(String courseName) {
        // Query to retrieve all content items for a course
        String query = "SELECT * FROM Content_Item AS CI INNER JOIN Module AS M ON CI.ID = M.Content_Item_ID INNER JOIN Module_Person AS MP ON M.Module_Person_Email = MP.Email WHERE CI.Course_Name = ?";

        // Create an arraylist to store the retrieved data
        ArrayList<Module> modules = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setString(1, courseName);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set
            while (rs.next()) {
                modules.add(new Module(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getDate("Publication_Date"),
                        rs.getString("Status"),
                        rs.getString("Description"),
                        rs.getDouble("Version"),
                        rs.getInt("Sequence_Number"),
                        new ModuleContactPerson(rs.getString("Name"), rs.getString("Email"))));
            }

            return modules;
        } catch (Exception e) {
            System.out.format("Error while retrieving modules for course (getModulesForCourse): %s", e.toString());
        }

        // Return nothing on error (null)
        return null;
    }

    public ArrayList<Webcast> getWebcastsForCourse(String courseName) {
        // Query to retrieve all webcasts for a course
        String query = "SELECT * FROM Content_Item AS CI INNER JOIN Webcast AS W ON CI.ID = W.Content_Item_ID INNER JOIN Webcast_Speaker AS WS ON W.Webcast_Speaker_ID = WS.ID WHERE CI.Course_Name = ?";

        // Create an arraylist to store the retrieved data
        ArrayList<Webcast> webcasts = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set query variable
            stmt.setString(1, courseName);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set
            while (rs.next()) {
                webcasts.add(new Webcast(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getDate("Publication_Date"),
                        rs.getString("Status"),
                        rs.getString("Description"),
                        new WebcastSpeaker(rs.getInt("Webcast_Speaker_ID"), rs.getString("Name"),
                                rs.getString("Organisation")),
                        rs.getInt("Duration"),
                        rs.getString("URL")));
            }

            return webcasts;
        } catch (Exception e) {
            System.out.format("Error while retrieving webcasts for course (getWebcastsForCourse): %s", e.toString());
        }

        // Return null on error (code reached catch block)
        return null;
    }

    public ArrayList<ContentItem> getContentItemsForCourse(String courseName) {
        ArrayList<ContentItem> cItems = new ArrayList<>();
        cItems.addAll(getModulesForCourse(courseName));
        cItems.addAll(getWebcastsForCourse(courseName));

        return cItems;
    }

    public boolean updateModule(Module module) {
        String contentItemQuery = "UPDATE Content_Item SET Title = ?, Status = ?, Publication_Date = ?, Description = ? WHERE ID = ?";
        String moduleQuery = "UPDATE Module SET Module_Person_Email = ?, Sequence_Number = ?, Version = ? WHERE Content_Item_ID = ?";

        // Create a prepared statement to prevent SQL injections
        try (PreparedStatement stmt = conn.prepareStatement(contentItemQuery);
                PreparedStatement moduleStmt = conn.prepareStatement(moduleQuery)) {
            // Set auto commit off so the executed queries are executes in a transaction.
            conn.setAutoCommit(false);

            // Set data in prepared statement.
            stmt.setString(1, module.getTitle());
            stmt.setString(2, module.getStatus());
            stmt.setDate(3, new java.sql.Date(module.getPublicationDate().getTime()));
            stmt.setString(4, module.getDescription());
            stmt.setInt(5, module.getID());

            // Execute first prepared statement
            stmt.executeUpdate();

            // Set data for second prepared statement
            moduleStmt.setString(1, module.getContactPerson().getEmail());
            moduleStmt.setInt(2, module.getOrderNumber());
            moduleStmt.setDouble(3, module.getVersion());
            moduleStmt.setInt(4, module.getID());

            // Execute the prepared query
            moduleStmt.executeUpdate();

            // Commit changes on success
            conn.commit();
            conn.setAutoCommit(true);

            // Return true on success
            return true;
        } catch (Exception e) {
            try {
                // Error! Rolling back the transaction
                conn.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            System.out.format("Error while updating module (updateModule): %s", e.toString());
        }

        // Return false on error (nothing is returned)
        return false;
    }

    public boolean updateWebcast(Webcast webcast){
        return true;
    }

    public boolean deleteContentItem(int ID) {
        // Creating contentitem model
        ProgressModel progressModel = new ProgressModel();

        // Deleting all progress 
        progressModel.deleteProgressContentItem(ID);

        String query = "DELETE FROM Content_Item WHERE ID = ?";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setInt(1, ID);

            // Execute prepared statement
            stmt.executeUpdate();

            // Return true on success
            return true;
        } catch (Exception e) {
            System.out.format("Error while deleting content item (deleteContentItem): %s", e.toString());
        }

        // Return false on error
        return false;
    }

    public ContentItem getContentItem(int ID) {
        // Create prepared statement
        String query = "SELECT * FROM Content_Item WHERE ID = ?";
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Set data in prepared statement
            stmt.setInt(1, ID);

            // Execute statement
            ResultSet rs = stmt.executeQuery();

            // Check if there is a result in the set
            if (rs.next()) {
                return new ContentItem(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("Status"),
                        rs.getDate("Publication_Date"),
                        rs.getString("Description"));
            }
        } catch (Exception e) {
            System.out.format("Error while retrieving content item (getContentItem): %s", e.toString());
        }

        // Return null on error
        return null;
    }
}
