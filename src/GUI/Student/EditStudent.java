package GUI.Student;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import Database.StudentModel;

import Domain.Student;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditStudent {

    public static Scene getView(Stage stage, Student student) throws SQLException {
        
        // Setting stage title
        stage.setTitle("CodeCademy | " + student.getName() + " aanpassen");

        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(25, 25, 25, 25));

        // Creating form
        Text scenetitle = new Text(student.getName() + " aanpassen");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);

        // Email
        Label email = new Label("Email (niet aanpasbaar):");
        formGrid.add(email, 0, 1);
        TextField emailTextfield = new TextField();
        emailTextfield.setDisable(true);
        emailTextfield.setPromptText(student.getEmail());
        formGrid.add(emailTextfield, 1, 1);

        // Name
        Label name = new Label("Naam:");
        formGrid.add(name, 0, 2);
        TextField nameTextfield = new TextField();
        nameTextfield.setText(student.getName());
        formGrid.add(nameTextfield, 1, 2);

        // Birthdate
        Label birthdate = new Label("Geboortedatum:");
        formGrid.add(birthdate, 0, 3);
        DatePicker birthdateTextfield = new DatePicker();
        birthdateTextfield.getEditor().setDisable(true);
        
        // Converting student birthdate to localdate
        LocalDate localDate = Instant.ofEpochMilli(student.getBirthDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        birthdateTextfield.setValue(localDate);
        formGrid.add(birthdateTextfield, 1, 3);

        // Gender
        Label gender = new Label("Geslacht:");
        formGrid.add(gender, 0, 4);
        TextField genderTextField = new TextField();
        genderTextField.setText(student.getGender());
        formGrid.add(genderTextField, 1, 4);

        // Address
        Label address = new Label("Adres:");
        formGrid.add(address, 0, 5);
        TextField addressTextField = new TextField();
        addressTextField.setText(student.getAddress());
        formGrid.add(addressTextField, 1, 5);

        // City
        Label city = new Label("Stad:");
        formGrid.add(city, 0, 6);
        TextField cityTextField = new TextField();
        cityTextField.setText(student.getCity());
        formGrid.add(cityTextField, 1, 6);

        // Country
        Label country = new Label("Land:");
        formGrid.add(country, 0, 7);
        TextField countryTextField = new TextField();
        countryTextField.setText(student.getCountry());
        formGrid.add(countryTextField, 1, 7);

        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> cancelButton(event, stage));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // Setting event handler save button
        saveButton.setOnAction((event) -> {
            
            // Updating student object
            student.setName(nameTextfield.getText());
            Date date = Date.valueOf(birthdateTextfield.getValue());
            student.setBirthDate(date);
            student.setGender(genderTextField.getText());
            student.setAddress(addressTextField.getText());
            student.setCity(cityTextField.getText());
            student.setCountry(countryTextField.getText());
            
            // Calling the save method
            saveButton(event, stage, student);
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
            stage.setScene(IndexStudent.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveButton(Event event, Stage stage, Student student) {
        
        // Creating student model
        StudentModel studentModel = new StudentModel();
        
        // Calling the student update method
        if (studentModel.updateStudent(student)) {
            // If succesvol show alert
            Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
            succesfullAlert.setContentText("Record is succesvol aangepast");
            succesfullAlert.show();
            // Going back to student index
            try {
                stage.setScene(IndexStudent.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // If failed show alert
            Alert succesfullAlert = new Alert(AlertType.WARNING);
            succesfullAlert.setContentText("Er is iets fout gegaan bij het updaten :(");
            succesfullAlert.show();
        }

    }
}
