package GUI.Module;

import Database.ContentItemModel;
import Domain.Course;
import Domain.Module;
import Domain.ModuleContactPerson;
import Domain.Status;
import GUI.ContentItem.IndexContentItem;

import java.sql.SQLException;

import java.util.Date;

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

public class CreateModule {
    public static Scene getView(Stage stage, Course course) {
        // Setting stage title
        stage.setTitle("CodeCademy | Module aanmaken");

        // Init contentitem model to retrieve contact person emails and submit a new record
        ContentItemModel cim = new ContentItemModel();

        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10, 10, 10, 10));

        // Creating form
        Text scenetitle = new Text("Module aanmaken voor " + course.getName());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);
 
        // Titel module
        Label name = new Label("Titel:");
        formGrid.add(name, 0, 1);
        TextField nameTextfield = new TextField();
        formGrid.add(nameTextfield, 1, 1);

        // Status module
        Label status = new Label("Status:");
        formGrid.add(status, 0, 2);
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().add(Status.CONCEPT.getValue());
        cbStatus.getItems().add(Status.ACTIVE.getValue());
        cbStatus.getItems().add(Status.ARCHIVED.getValue());
        formGrid.add(cbStatus, 1, 2);

        // Description module
        Label description = new Label("Beschrijving:");
        formGrid.add(description, 0, 3);
        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setPrefRowCount(5);
        descriptionTextArea.setPrefColumnCount(9);
        formGrid.add(descriptionTextArea, 1, 3);

        // Sequence number module
        Label sequenceNumber = new Label("Volgorde nummer:");
        formGrid.add(sequenceNumber, 0, 4);
        TextField sequenceNumberTextField = new TextField();
        formGrid.add(sequenceNumberTextField, 1, 4);

        // Version module
        Label version = new Label("Versie:");
        formGrid.add(version, 0, 5);
        TextField versionTextField = new TextField();
        formGrid.add(versionTextField, 1, 5);

        // Contactpersoon module
        Label contactPerson = new Label("Email contactpersoon:");
        formGrid.add(contactPerson, 0, 6);
        ComboBox<String> cbContactPerson = new ComboBox<>();
        for(ModuleContactPerson person : cim.getContactPersons()){
            cbContactPerson.getItems().add(person.getEmail());
        }
        formGrid.add(cbContactPerson, 1, 6);
 
        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> cancelButton(event, stage, course));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
 
        // Setting event handler save button
        saveButton.setOnAction((event) -> {
            // Creating 
            Module module = new Module(
                // Content item ID will be set in db so set it to -1
                -1,
                nameTextfield.getText(),
                new Date(),
                Status.convertToEnum(cbStatus.getValue()),
                descriptionTextArea.getText(),
                Double.parseDouble(versionTextField.getText()),
                Integer.parseInt(sequenceNumberTextField.getText()),
                cim.getContactPersonWithEmail(cbContactPerson.getValue())
            );

            // Calling the save method
            saveModule(event, stage, module, course);
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

    private static void saveModule(Event event, Stage stage, Module module, Course course){
        // Initializing contentitem model
        ContentItemModel contentItemModel = new ContentItemModel();

        // Calling the insert method for a module
        if (contentItemModel.createModule(module, course)) {
            // If succesvol show alert
            Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
            succesfullAlert.setContentText("Module is succesvol toegevoegd");
            succesfullAlert.show();

            // Going back to overview page
            try {
                stage.setScene(IndexContentItem.getView(stage, course));
            } catch(SQLException e){
                e.printStackTrace();
            }
            
        } else {
            // If failed show alert
            Alert succesfullAlert = new Alert(AlertType.WARNING);
            succesfullAlert.setContentText("Er is iets fout gegaan bij het aanmaken van de module :(");
            succesfullAlert.show();
        }
    }

    public static void cancelButton(Event event, Stage stage, Course course) {
        // Returning to student index
        try {
            stage.setScene(IndexContentItem.getView(stage, course));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
