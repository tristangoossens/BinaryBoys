package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsModel extends Conn {
    // Init model and database connection
    public StatisticsModel(){
        super();
    }

    // Statistic 1
    public HashMap<String, HashMap<String, Integer>> getGenderCompletionData() {
        String graduatedInfoQuery = "SELECT S.Gender, COUNT(*) AS 'Graduated' FROM Enrollment AS E INNER JOIN Student AS S on E.Student_Email = S.Email WHERE E.ID IN (SELECT Enrollment_ID FROM Certificate) GROUP BY S.Gender";
        String enrolledStudentsInfo = "SELECT S.Gender, COUNT(*) AS 'Amount' FROM Enrollment AS E INNER JOIN Student AS S on E.Student_Email = S.Email GROUP BY S.Gender";

        HashMap<String, HashMap<String, Integer>> info = new HashMap<>();

        try(PreparedStatement stmt = super.conn.prepareStatement(graduatedInfoQuery);
        PreparedStatement totalStmt = super.conn.prepareStatement(enrolledStudentsInfo)) {
            // Execute statement and init Hashmap
            ResultSet rsGraduated = stmt.executeQuery();
            HashMap<String, Integer> genderInfoGraduated = new HashMap<>();

            while(rsGraduated.next()){
                genderInfoGraduated.put(rsGraduated.getString("Gender"), rsGraduated.getInt("Graduated"));
            }

            info.put("Geslaagd", genderInfoGraduated);

            // Execute statement and init hashmap
            ResultSet rsTotal = totalStmt.executeQuery();
            HashMap<String, Integer> genderInfoTotal = new HashMap<>();

            while(rsTotal.next()){
                genderInfoTotal.put(rsTotal.getString("Gender"), rsTotal.getInt("Amount"));
            }

            info.put("Totaal", genderInfoTotal);
        
            // Return hashmap on success
            return info;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (getGenderCompletionData): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Statistic 2
    public ArrayList<String> getAverageModuleProgress(){
        String averageModuleProgressQuery = "SELECT S.Email, C.Name, AVG(P.Percentage) AS 'Average' FROM Progress AS P INNER JOIN Student AS S ON S.Email = P.Student_Email INNER JOIN Content_Item AS CI ON P.Content_Item_ID = CI.ID INNER JOIN Course AS C ON CI.Course_Name = C.Name INNER JOIN Module AS M ON M.Content_Item_ID = CI.ID GROUP BY S.Email, C.Name";

        try(PreparedStatement stmt = super.conn.prepareStatement(averageModuleProgressQuery)) {
            // Execute statement and init Arraylist
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();

            while(rs.next()){
                result.add(String.format("%s:%s->%.2f", rs.getString("Email"), rs.getString("Name"), rs.getDouble("Average")));
            }

            // Return arraylist on succes
            return result;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (getAverageModuleProgress): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Statistic 3
    public ArrayList<String> getStudentCourseModuleProgresses(){
        String studentCourseModuleProgressesQuery = "SELECT S.Email, C.Name, CI.Title, P.Percentage FROM Student AS S INNER JOIN Enrollment AS E ON E.Student_Email = S.Email INNER JOIN Course AS C ON E.Course_Name = C.Name INNER JOIN Content_Item AS CI ON C.Name = CI.Course_Name INNER JOIN Module AS M ON CI.ID = M.Content_Item_ID INNER JOIN Progress AS P ON P.Content_Item_ID = CI.ID ORDER BY S.Email, C.Name";

        try(PreparedStatement stmt = super.conn.prepareStatement(studentCourseModuleProgressesQuery)) {
            // Execute statement and init Arraylist
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();

            while(rs.next()){
                result.add(String.format("%s:%s->%s->%.2f", rs.getString("Email"), rs.getString("Name"), rs.getString("Title"), rs.getDouble("Percentage")));
            }

            // Return arraylist on succes
            return result;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (getStudentCourseModuleProgresses): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Statistic 4
    public ArrayList<String> getStudentCertificates(){
        String studentCertificateQuery = "SELECT S.Email, CS.Name, C.Grade FROM Enrollment AS E INNER JOIN Student AS S ON E.Student_Email = S.Email INNER JOIN Certificate AS C ON E.ID = C.Enrollment_ID INNER JOIN Course AS CS ON E.Course_Name = CS.Name";

        try(PreparedStatement stmt = super.conn.prepareStatement(studentCertificateQuery)) {
            // Execute statement and init Arraylist
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();

            while(rs.next()){
                result.add(String.format("%s:%s->%.1f", rs.getString("Email"), rs.getString("Name"), rs.getDouble("Grade")));
            }

            // Return arraylist on succes
            return result;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (getStudentCertificates): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Statistic 5
    public ArrayList<String> getTop3Webcasts(){
        String top3WebcastsQuery = "SELECT TOP 3 CI.Title, COUNT(*) AS 'Aantal bezocht' FROM Progress AS P INNER JOIN Content_Item AS CI ON P.Content_Item_ID = CI.ID INNER JOIN Webcast AS W ON P.Content_Item_ID = W.Content_Item_ID WHERE P.Percentage > 0 GROUP BY CI.Title ORDER BY 'Aantal bezocht' DESC";

        try(PreparedStatement stmt = super.conn.prepareStatement(top3WebcastsQuery)) {
            // Execute statement and init Arraylist
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();

            while(rs.next()){
                result.add(String.format("%s:%d", rs.getString("Title"), rs.getInt("Aantal bezocht")));
            }

            // Return arraylist on succes
            return result;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (getTop3Webcasts): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Statistic 6
    public ArrayList<String> getTop3CoursesWithMostCertificates(){
        String top3MostCertifiedCoursesQuery = "SELECT TOP 3 E.Course_Name, COUNT(*) 'Aantal certificaten' FROM Certificate AS C INNER JOIN Enrollment AS E ON C.Enrollment_ID = E.ID GROUP BY E.Course_Name ORDER BY 'Aantal certificaten' DESC";

        try(PreparedStatement stmt = super.conn.prepareStatement(top3MostCertifiedCoursesQuery)) {
            // Execute statement and init Arraylist
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();

            while(rs.next()){
                result.add(String.format("%s:%d", rs.getString("Course_Name"), rs.getInt("Aantal certificaten")));
            }

            // Return arraylist on succes
            return result;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (getTop3CoursesWithMostCertificates): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Statistic 7
    public ArrayList<String> getCoursesWithRelatedCourses(){
        String relatedCoursesQuery = "SELECT * FROM Related_Course ORDER BY Course_Name ASC";

        try(PreparedStatement stmt = super.conn.prepareStatement(relatedCoursesQuery)) {
            // Execute statement and init Arraylist
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();

            while(rs.next()){
                result.add(String.format("%s:%d", rs.getString("Course_Name"), rs.getString("Sub_Course_Name")));
            }

            // Return arraylist on succes
            return result;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (getCoursesWithRelatedCourses): %s", e.toString());
        }

        // Return null on error
        return null;
    }

    // Statistic 8
    public ArrayList<String> getCourseCompletionCount(){
        String courseCompletionCountQuery = "SELECT Course_Name, COUNT(*) AS 'Certificaten' FROM Enrollment AS E  INNER JOIN Certificate AS C ON E.ID = C.Enrollment_ID GROUP BY Course_Name";

        try(PreparedStatement stmt = super.conn.prepareStatement(courseCompletionCountQuery)) {
            // Execute statement and init Arraylist
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<>();

            while(rs.next()){
                result.add(String.format("%s:%d", rs.getString("Course_Name"), rs.getInt("Certificaten")));
            }

            // Return arraylist on succes
            return result;
        } catch(Exception e) {
            System.out.format("Error while retrieving statistic info (courseCompletionCountQuery): %s", e.toString());
        }

        // Return null on error
        return null;
    }
}
