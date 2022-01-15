package GUI.Course;

import java.sql.SQLException;

import Database.CourseModel;
import Domain.Course;
import Domain.Level;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditCourse {
    public Scene getView(Stage stage, Course course) throws SQLException {
        
        // Setting stage title
        stage.setTitle("CodeCademy | " + course.getName() + " aanpassen");

        stage.setTitle("CodeCademy | Cursus aanmaken");
        
        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(25, 25, 25, 25));

        // Creating form
        Text scenetitle = new Text(course.getName() + " aanpassen");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);

        // Name
        Label name = new Label("Naam (niet aanpasbaar):");
        formGrid.add(name, 0, 1);
        TextField nameTextfield = new TextField();
        nameTextfield.setDisable(true);
        nameTextfield.setPromptText(course.getName());
        formGrid.add(nameTextfield, 1, 1);

        // Subject
        Label subject = new Label("Onderwerp:");
        formGrid.add(subject, 0, 2);
        TextField subjectTextField = new TextField();
        subjectTextField.setText(course.getSubject());
        formGrid.add(subjectTextField, 1, 2);

        // Introduction
        Label introduction = new Label("Beschrijving:");
        formGrid.add(introduction, 0, 3);
        TextArea introductionTextArea = new TextArea();
        introductionTextArea.setText(course.getIntroduction());
        introductionTextArea.setMaxSize(300, 100);
        formGrid.add(introductionTextArea, 1, 3);

        // Course level
        Label level = new Label("Niveau:");
        formGrid.add(level, 0, 4);
        ComboBox<String> levelCombobox = new ComboBox<>();
        levelCombobox.getItems().add(Level.BEGINNER.getValue());
        levelCombobox.getItems().add(Level.ADVANCED.getValue());
        levelCombobox.getItems().add(Level.EXPERT.getValue());
        levelCombobox.setValue(course.getLevel().getValue());
        formGrid.add(levelCombobox, 1, 4);

        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> cancelButton(event, stage));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // Setting event handler save button
        saveButton.setOnAction((event) -> {

            // Creating course object
            course.setSubject(subjectTextField.getText());
            course.setIntroduction(introductionTextArea.getText());
            course.setLevel(Level.convertToEnum(levelCombobox.getValue()));

            // Calling the save method
            saveButton(event, stage, course);
        });

        // Creating HBox for buttons
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
            stage.setScene(IndexCourse.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveButton(Event event, Stage stage, Course course) {
        try{        
            // Creating student model
            CourseModel courseModel = new CourseModel();

            System.out.println(course.getName());

            // Calling the student update method
            if (courseModel.updateCourse(course)) {
                // If succesvol show alert
                Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
                succesfullAlert.setContentText("Record is succesvol aangepast");
                succesfullAlert.show();
                // Going back to student index
                try {
                    stage.setScene(IndexCourse.getView(stage));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                // If failed show alert
                Alert succesfullAlert = new Alert(AlertType.WARNING);
                succesfullAlert.setContentText("Er is iets fout gegaan bij het updaten :(");
                succesfullAlert.show();
            }
        } catch (Exception nullpointerException) {
            // If failed show alert
            Alert succesfullAlert = new Alert(AlertType.WARNING);
            succesfullAlert.setContentText("De postcode is niet geldig !");
            succesfullAlert.show();
        }
    }
}
