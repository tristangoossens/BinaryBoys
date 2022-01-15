package GUI.Webcast;

import java.sql.SQLException;
import java.util.Date;

import Database.ContentItemModel;
import Domain.Course;
import Domain.Webcast;
import Domain.WebcastSpeaker;
import GUI.App;
import GUI.ContentItem.IndexContentItem;


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

public class CreateWebcast {
    public Scene getView(Stage stage, Course course) {
        // Setting stage title
        stage.setTitle("CodeCademy | Webcast aanmaken");

        // Init contentitem model to submit a new record
        ContentItemModel cim = new ContentItemModel();

        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10, 10, 10, 10));

        // Creating form
        Text scenetitle = new Text("Webcast aanmaken");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);

        // Name webcast
        Label name = new Label("Titel:");
        formGrid.add(name, 0, 1);
        TextField nameTextfield = new TextField();
        formGrid.add(nameTextfield, 1, 1);

        // Status webcast
        Label status = new Label("Status:");
        formGrid.add(status, 0, 2);
        TextField statusTextField = new TextField();
        formGrid.add(statusTextField, 1, 2);

        // Description webcast
        Label description = new Label("Beschrijving:");
        formGrid.add(description, 0, 3);
        TextField descriptionTextField = new TextField();
        formGrid.add(descriptionTextField, 1, 3);

        // Sequence number webcast
        Label url = new Label("URL:");
        formGrid.add(url, 0, 4);
        TextField urlTextField = new TextField();
        formGrid.add(urlTextField, 1, 4);

        // Sequence number webcast
        Label duration = new Label("Duur webcast:");
        formGrid.add(duration, 0, 5);
        TextField durationTextfield = new TextField();
        formGrid.add(durationTextfield, 1, 5);

        // Spreker webcast
        Label webcastSpeaker = new Label("Webcast spreker:");
        formGrid.add(webcastSpeaker, 0, 6);
        ComboBox<String> cbSpeaker = new ComboBox<>();
        cbSpeaker.getItems().addAll(cim.getWebcastSpeakers());
        formGrid.add(cbSpeaker, 1, 6);

        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> stage.setScene(App.getView(stage)));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // Setting event handler save button
        saveButton.setOnAction((event) -> {
        Integer webcastSpeakerID = Integer.parseInt(cbSpeaker.getValue().split(":")[0]);
        String webcastSpeakerName = cbSpeaker.getValue().split("->")[0];
        String webcastSpeakerOrg = cbSpeaker.getValue().split("->")[0];

        // Creating object
        Webcast webcast = new Webcast(
            // Content item ID will be set in db so set it to -1
            -1,
            nameTextfield.getText(),
            new Date(),
            statusTextField.getText(),
            descriptionTextField.getText(),
            new WebcastSpeaker(webcastSpeakerID, webcastSpeakerName, webcastSpeakerOrg),
            Integer.parseInt(durationTextfield.getText()),
            urlTextField.getText()
        );

        // Calling the save method
        saveWebcast(event, stage, webcast, course);
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

    private void saveWebcast(Event event, Stage stage, Webcast webcast, Course course){
        // Initializing contentitem model
        ContentItemModel contentItemModel = new ContentItemModel();

        // Calling the insert method for a module
        if (contentItemModel.createWebcast(webcast, "IT")) {
            // If succesvol show alert
            Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
            succesfullAlert.setContentText("Webcast is succesvol toegevoegd");
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
            succesfullAlert.setContentText("Er is iets fout gegaan bij het aanmaken van de webcast :(");
            succesfullAlert.show();
        }
    }
}
