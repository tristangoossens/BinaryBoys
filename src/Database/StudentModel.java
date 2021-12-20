package Database;

import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentModel extends Conn {
    public StudentModel(){
        // Initialize super class conn
        super();
    }

    public void createStudent(Student student){

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO Students VALUES (");
        sb.append(student.getEmail());
        sb.append(",");
        sb.append(student.getName());
        sb.append(",");
        sb.append(student.getBirthDate());
        sb.append(",");
        sb.append(student.getGender());
        sb.append(",");
        sb.append(student.getAdress());
        sb.append(",");
        sb.append(student.getCity());
        sb.append(",");
        sb.append(student.getCountry());
        sb.append(")");

        System.out.println(sb.toString());

        String query = sb.toString();

        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            stmt.executeQuery();
            if(!stmt.executeQuery().wasNull()){
                System.out.println("Student created");
            }
        }
        catch(Exception e){
            System.out.format("Error while creating student (createStudent): %s", e.toString());
        }

    }

    public Student readStudent(Student student){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM Student WHERE Email = ");
        sb.append(student.getEmail());

        System.out.println(sb.toString());

        String query = sb.toString();

        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            stmt.executeQuery();
            if(!stmt.executeQuery().wasNull()){
                System.out.println("Student created");
            }
        }
        catch(Exception e){
            System.out.format("Error while creating student (createStudent): %s", e.toString());
        }
        return 
    }

    public void updateStudent(Student student){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE Students SET (");
        sb.append("Email = ");
        sb.append(student.getEmail());
        sb.append(",");
        sb.append("Name = ");
        sb.append(student.getName());
        sb.append(",");
        sb.append("Birthdate = ");
        sb.append(student.getBirthDate());
        sb.append(",");
        sb.append("Gender = ");
        sb.append(student.getGender());
        sb.append(",");
        sb.append("Address = ");
        sb.append(student.getAdress());
        sb.append(",");
        sb.append("City = ");
        sb.append(student.getCity());
        sb.append(",");
        sb.append("Country = ");
        sb.append(student.getCountry());
        sb.append(" WHERE ");
        sb.append("Email = ");
        sb.append(student.getEmail());
        sb.append(")");

        System.out.println(sb.toString());

        String query = sb.toString();

        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            stmt.executeQuery();
            System.out.println("Student updated");
        }
        catch(Exception e){
            System.out.format("Error while updating student (updateStudent): %s", e.toString());
        }
    }

    public void deleteStudent(Student student){

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Student WHERE Email =");
        sb.append(student.getEmail());

        System.out.println(sb.toString());

        String query = sb.toString();

        try(PreparedStatement stmt = super.conn.prepareStatement(query)){
            stmt.executeQuery();
            if(!stmt.executeQuery().wasNull()){
                System.out.println("Student deleted");
            }
        }
        catch(Exception e){
            System.out.format("Error while deleting student (deleteStudent): %s", e.toString());
        }

    public ArrayList<Student> getStudents() {
        // Set query to exectute
        String query = "SELECT * FROM Student";
        ResultSet rs = null;

        ArrayList<Student> students = new ArrayList<>();

        // Create a prepared statement for the SQL query
        try (PreparedStatement stmt = super.conn.prepareStatement(query)) {
            // Execute the prepared query
            rs = stmt.executeQuery();

            while(rs.next()){
                students.add(new Student(
                    rs.getString("Email"),
                    rs.getString("Name"),
                    rs.getDate("Birthdate"),
                    rs.getString("Gender"),
                    rs.getString("Address"),
                    rs.getString("City"),
                    rs.getString("Country"))
                );
            }

            return students;
        } catch (Exception e) {
            System.out.format("Error while getting students (getStudents): %s", e.toString());
        }

        // Return null when nothing is returned yet (error)
        return null;
    }
}
