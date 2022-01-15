package GUI.Student;

import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Validation.PostalCode;

import Database.StudentModel;
import Domain.Gender;
import Domain.Student;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateStudent {

    public Scene getView(Stage stage) throws SQLException {

        // Settimg stage title
        stage.setTitle("CodeCademy | Student aanmaken");

        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(25, 25, 25, 25));

        // Creating form
        Text scenetitle = new Text("Student aanmaken");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);

        // Email
        Label email = new Label("Email:");
        formGrid.add(email, 0, 1);
        TextField emailTextfield = new TextField();
        formGrid.add(emailTextfield, 1, 1);

        // Name
        Label name = new Label("Naam:");
        formGrid.add(name, 0, 2);
        TextField nameTextfield = new TextField();
        formGrid.add(nameTextfield, 1, 2);

        // Birthdate
        Label birthdate = new Label("Geboortedatum:");
        formGrid.add(birthdate, 0, 3);

        // Birtdate
        Label dayLabel = new Label("DD:");
        TextField dayTextField = new TextField();
        dayTextField.setPrefWidth(30);
        Label monthLabel = new Label("MM:");
        TextField monthTextField = new TextField();
        monthTextField.setPrefWidth(30);
        Label yearLabel = new Label("JJJJ:");
        TextField yearTextField = new TextField();
        yearTextField.setPrefWidth(45);

        // Creating Hbox for textfield + adding them
        HBox dateBox = new HBox(dayLabel, dayTextField, monthLabel, monthTextField, yearLabel, yearTextField);
        dateBox.setSpacing(5);

        // Adding datebox to grid
        formGrid.add(dateBox, 1, 3);

        // Gender
        Label gender = new Label("Geslacht:");
        formGrid.add(gender, 0, 4);
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().add(Gender.MALE.getValue());
        genderComboBox.getItems().add(Gender.FEMALE.getValue());
        formGrid.add(genderComboBox, 1, 4);

        // Address
        Label address = new Label("Adres:");
        formGrid.add(address, 0, 5);
        TextField addressTextField = new TextField();
        formGrid.add(addressTextField, 1, 5);

        // Postalcode
        Label postalCode = new Label("Postcode:");
        formGrid.add(postalCode, 0, 6);
        TextField postalCodeTextField = new TextField();
        formGrid.add(postalCodeTextField, 1, 6);

        // City
        Label city = new Label("Stad:");
        formGrid.add(city, 0, 7);
        TextField cityTextField = new TextField();
        formGrid.add(cityTextField, 1, 7);

        // Country
        Label country = new Label("Land:");
        formGrid.add(country, 0, 8);
        TextField countryTextField = new TextField();
        formGrid.add(countryTextField, 1, 8);

        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> cancelButton(event, stage));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // Setting event handler save button
        saveButton.setOnAction((event) -> {

            // Converting and validating date
            Date date = validateAndFormatDate(dayTextField.getText(), monthTextField.getText(),
                    yearTextField.getText());

            // Creating student obj
            Student student = new Student(
                    emailTextfield.getText(),
                    nameTextfield.getText(),
                    date,
                    Gender.convertToEnum(genderComboBox.getValue()),
                    addressTextField.getText(),
                    postalCodeTextField.getText(),
                    cityTextField.getText(),
                    countryTextField.getText());

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
        try{
            // Validate postalcode
            PostalCode.formatPostalCode(student.getPostalCode());

            // Creating student model
            StudentModel studentModel = new StudentModel();

            // Calling the student create method
            if (studentModel.createStudent(student)) {
                // If succesvol show alert
                Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
                succesfullAlert.setContentText("Student is succesvol toegevoegd");
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
                succesfullAlert.setContentText("Er is iets fout gegaan bij het aanmaken van de student :(");
                succesfullAlert.show();
            }
        } catch (Exception nullpointerException) {
            // If failed show alert
            Alert succesfullAlert = new Alert(AlertType.WARNING);
            succesfullAlert.setContentText("De postcode is niet geldig !");
            succesfullAlert.show();
        }
    }

    public static Date validateAndFormatDate(String day, String month, String year) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String str_date = String.format("%s-%s-%s", day, month, year);
        try {
            Date date = formatter.parse(str_date);
            return date;
        } catch (ParseException e) {
            return null;
        }

    }
}
