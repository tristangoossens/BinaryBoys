package GUI.Webcast;

import java.util.Date;
import java.sql.SQLException;

import Database.ContentItemModel;
import Domain.Course;
import Domain.Status;
import Domain.Webcast;
import Domain.WebcastSpeaker;
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

public class EditWebcast {
    public static Scene getView(Stage stage, Webcast webcast, Course course){
        // Setting stage title
        stage.setTitle("CodeCademy | " + webcast.getTitle() + " aanpassen");

        // Init contentitem model to submit a new record
        ContentItemModel cim = new ContentItemModel();

        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10, 10, 10, 10));

        // Creating form
        Text scenetitle = new Text(webcast.getTitle() + " aanpassen");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);

        // Name webcast
        Label name = new Label("Titel:");
        formGrid.add(name, 0, 1);
        TextField nameTextfield = new TextField();
        nameTextfield.setText(webcast.getTitle());
        formGrid.add(nameTextfield, 1, 1);

        // Status webcast
        Label status = new Label("Status:");
        formGrid.add(status, 0, 2);
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().add(Status.CONCEPT.getValue());
        cbStatus.getItems().add(Status.ACTIVE.getValue());
        cbStatus.getItems().add(Status.ARCHIVED.getValue());
        formGrid.add(cbStatus, 1, 2);


        // Description webcast
        Label description = new Label("Beschrijving:");
        formGrid.add(description, 0, 3);
        TextField descriptionTextField = new TextField();
        descriptionTextField.setText(webcast.getDescription());
        formGrid.add(descriptionTextField, 1, 3);

        // Sequence number webcast
        Label url = new Label("URL:");
        formGrid.add(url, 0, 4);
        TextField urlTextField = new TextField();
        urlTextField.setText(webcast.getUrl());
        formGrid.add(urlTextField, 1, 4);

        // Sequence number webcast
        Label duration = new Label("Duur webcast:");
        formGrid.add(duration, 0, 5);
        TextField durationTextfield = new TextField();
        durationTextfield.setText(String.valueOf(webcast.getDuration()));
        formGrid.add(durationTextfield, 1, 5);

        // Spreker webcast
        Label webcastSpeaker = new Label("Webcast spreker:");
        formGrid.add(webcastSpeaker, 0, 6);
        ComboBox<String> cbSpeaker = new ComboBox<>();
        cbSpeaker.getItems().addAll(cim.getWebcastSpeakers());
        cbSpeaker.setValue(webcast.getSpeaker().getSpeakerAndCompany());
        formGrid.add(cbSpeaker, 1, 6);

        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> cancelButton(event, stage, course));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // Setting event handler save button
        saveButton.setOnAction((event) -> {

        Integer webcastSpeakerID = Integer.parseInt(cbSpeaker.getValue().split(":")[0]);
        String webcastSpeakerName = cbSpeaker.getValue().split("->")[0];
        String webcastSpeakerOrg = cbSpeaker.getValue().split("->")[0];

        // Updating object
        webcast.setTitle(nameTextfield.getText()); 
        webcast.setPublicationDate(new Date()); 
        webcast.setStatus(Status.convertToEnum(cbSpeaker.getValue())); 
        webcast.setDescription(descriptionTextField.getText()); 
        webcast.setSpeaker(new WebcastSpeaker(webcastSpeakerID, webcastSpeakerName, webcastSpeakerOrg));
        webcast.setDuration(Integer.parseInt(durationTextfield.getText())); 
        webcast.setUrl(urlTextField.getText());
        
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

    private static void saveWebcast(Event event, Stage stage, Webcast webcast, Course course){
        // Initializing contentitem model
        ContentItemModel contentItemModel = new ContentItemModel();

        // Calling the insert method for a module
        if (contentItemModel.updateWebcast(webcast)) {
            // If succesvol show alert
            Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
            succesfullAlert.setContentText("Module is succesvol aangepast");
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
            succesfullAlert.setContentText("Er is iets fout gegaan bij het aanpassen van de module :(");
            succesfullAlert.show();
        }
    }

    private static void cancelButton(Event event, Stage stage, Course course) {
        // Returning to student index
        try {
            stage.setScene(IndexContentItem.getView(stage, course));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
