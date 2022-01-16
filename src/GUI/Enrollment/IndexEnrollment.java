package GUI.Enrollment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Database.EnrollmentModel;

import Domain.Enrollment;

import GUI.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
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

public class IndexEnrollment {

    public static Scene getView(Stage stage) throws SQLException {
        // Creating object models
        EnrollmentModel enrollmentModel = new EnrollmentModel();

        // Settimg stage title
        stage.setTitle("CodeCademy | Studenten");

        // Title text
        Text titleText = new Text("Inchrijvingen");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        // Creating table view
        TableView<Enrollment> tableView = new TableView<Enrollment>();

        // Setting data table view
        TableColumn<Enrollment, String> column1 = new TableColumn<>("Naam student");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudent().getEmail()));

        TableColumn<Enrollment, String> column2 = new TableColumn<>("Email student");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudent().getName()));

        TableColumn<Enrollment, String> column3 = new TableColumn<>("Naam cursus");
        column3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCourse().getName()));

        TableColumn<Enrollment, Date> column4 = new TableColumn<>("Inschrijfdatum");
        column4.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        // Retrieving all students
        ArrayList<Enrollment> enrollments = enrollmentModel.getEnrollments();

        // Looping through students + adding to table
        for (Enrollment enrollment : enrollments) {
            tableView.getItems().add(enrollment);
        }

        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> stage.setScene(App.getView(stage)));

        // Creating create button + setting event handler
        Button createButton = new Button("Inschrijven");
        createButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        createButton.setOnAction((event) -> createEnrollment(event, stage));

        // Creating delete button + setting event handler
        Button deleteButton = new Button("Verwijder");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        deleteButton.setOnAction((event) -> deleteRowFromTable(event, tableView, enrollmentModel));

        // Creating HBox for buttons
        HBox buttonBox = new HBox(backButton, createButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creatoing VBox for all components
        VBox vbox = new VBox(titleText, tableView, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }

    private static void deleteRowFromTable(ActionEvent event, TableView<Enrollment> tableView, EnrollmentModel enrollmentModel) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt verwijderen");
            warningAlert.show();
        } else {

            // Retrieving selected enrollment object
            Enrollment selectedEnrollment = (Enrollment) tableView.getSelectionModel().getSelectedItem();

            // Deleting student from database
            if (enrollmentModel.deleteEnrollment(selectedEnrollment)) {

                // If delete method returns true, delete row form table
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());

                // Succes message
                Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
                succesfullAlert.setContentText("Record is succesvol verwijderd");
                succesfullAlert.show();

            } else {

                // If delete method returns false, alert to user
                Alert succesfullAlert = new Alert(AlertType.WARNING);
                succesfullAlert.setContentText("Er is iets fout gegaan bij het verwijderen :(");
                succesfullAlert.show();
            }

        }
    }

    private static void createEnrollment(ActionEvent event, Stage stage) {

        // Create page
        CreateEnrollment createEnrollment = new CreateEnrollment();

        // Try to open new page
        try {
            stage.setScene(createEnrollment.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
