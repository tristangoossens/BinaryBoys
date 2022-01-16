package GUI.Certificate;

import java.util.ArrayList;
import java.util.Date;

import Database.CertificateModel;
import Domain.Enrollment;
import GUI.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IndexCertificate {
    public static Scene getView(Stage stage) {
        // Init certificate database model
        CertificateModel cm = new CertificateModel();

        // Setting stage title
        stage.setTitle("CodeCademy | Certificaten toewijzen");

        // Title tab
        Text titleText = new Text("Certificeerbare inschrijvingen");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        // Creating table view
        TableView<Enrollment> tableView = new TableView<>();

        // Setting data table view
        TableColumn<Enrollment, String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Enrollment, String> column2 = new TableColumn<>("Email student");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudent().getEmail()));

        TableColumn<Enrollment, String> column3 = new TableColumn<>("Naam cursus");
        column3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCourse().getName()));

        TableColumn<Enrollment, Date> column4 = new TableColumn<>("Inschrijfdatum");
        column4.setCellValueFactory(new PropertyValueFactory<>("date"));


        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        // Retrieving all certifible students
        ArrayList<Enrollment> enrollments = cm.getCertifiableEnrollments();

        // Looping through enrollments + adding to table
        for (Enrollment enrollment : enrollments) {
            tableView.getItems().add(enrollment);
        }

        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> stage.setScene(App.getView(stage)));

        // Creating create button + setting event handler
        Button createButton = new Button("Toekennen");
        createButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        createButton.setOnAction((event) -> createCertificate(stage, tableView));
;

        // Creating HBox for buttons
        HBox buttonBox = new HBox(backButton, createButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creatoing VBox for all components
        VBox vbox = new VBox(titleText,tableView, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }

    private static void createCertificate(Stage stage, TableView<Enrollment> tableView) {
        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een enrollment waar je een certificaat aan wil toekennen");
            warningAlert.show();
        } else {

            // Retrieving enrollment object from table
            Enrollment selectedEnrollment = (Enrollment) tableView.getSelectionModel().getSelectedItem();

            // Create page
            CreateCertificate createCertificate = new CreateCertificate();

            // Try to open new page
            try {
                stage.setScene(createCertificate.getView(stage, selectedEnrollment));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
}
