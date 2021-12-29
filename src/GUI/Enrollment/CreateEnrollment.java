package GUI.Enrollment;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.EnrollmentModel;
import Database.StudentModel;
import Database.CourseModel;
import Domain.Course;
import Domain.Enrollment;
import Domain.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateEnrollment{


    public static Scene getView(Stage stage) throws SQLException {

        // setting stage title
        stage.setTitle("CodeCademy | Enrollment aanmaken");

        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(25, 25, 25, 25));

        // Creating form
        Text scenetitle = new Text("Enrollment aanmaken");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);

        // getting Courses
        CourseModel course = new CourseModel();
        ArrayList<Course> courses = CourseModel.getCourses();

        // Course
        Label courseLabel = new Label("Course:");
        formGrid.add(courseLabel, 0, 1);
        ObservableList<Course> courseOptions = FXCollections.observableArrayList(

        );
        formGrid.add(courseOptions, 1, 1);

        // getting students
        StudentModel studentmodel = new StudentModel();
        ArrayList<Student> students = studentmodel.getStudents();

        // Student
        Label studentLabel = new Label("Student:");
        formGrid.add(studentLabel, 0, 2);
        ObservableList<Student> studentOptions = FXCollections.observableArrayList();
        ComboBox<Student> studentCombo = new ComboBox<Student>();
        for(Student student : students){
            studentCombo.getItems().add(student);
        }


        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> cancelButton(event, stage));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

            // Setting event handler save button
        saveButton.setOnAction((event) -> {


            // Creating enrollment obj
            Enrollment enrollment = new Enrollment(
                enrollment.setCourse(courseOptions.getSelectionModel().getSelectedItem()),
                enrollment.setStudent(studentOptions.getSelectionModel().getSelectedItem()),
                enrollment.setDate(DateTime.now());
            );

            // Calling the save method
            saveButton(event, stage, student);
        });

        HBox buttonBox = new HBox(cancelButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creating vBox with all components
        VBox vbox = new VBox(formGrid, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }

    public static void cancelButton(Event event, Stage stage) {
        // Returning to student index
        try {
            stage.setScene(IndexEnrollment.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveButton(Event event, Stage stage, Enrollment enrollment) {

        // Creating student model
        StudentModel studentModel = new StudentModel();

        // Calling the student create method
        if (EnrollmentModel.createEnrollment(enrollment)) {
            // If succesvol show alert
            Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
            succesfullAlert.setContentText("Enrollment is succesvol toegevoegd");
            succesfullAlert.show();
            // Going back to student index
            try {
                stage.setScene(IndexEnrollment.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // If failed show alert
            Alert succesfullAlert = new Alert(AlertType.WARNING);
            succesfullAlert.setContentText("Er is iets fout gegaan bij het aanmaken van de Enrollment :(");
            succesfullAlert.show();
        }

    }
}
