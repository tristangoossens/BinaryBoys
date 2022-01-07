package GUI.Enrollment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Database.EnrollmentModel;

import Domain.Enrollment;

import GUI.App;

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
import javafx.stage.Stage;

public class IndexEnrollment {

    public static Scene getView(Stage stage) throws SQLException {
        // Creating object models
        EnrollmentModel enrollmentModel = new EnrollmentModel();

        // Settimg stage title
        stage.setTitle("CodeCademy | Studenten");

        // Creating table view
        TableView<Enrollment> tableView = new TableView<Enrollment>();

        // Setting data table view
        TableColumn<Enrollment, String> column1 = new TableColumn<>("Student");
        column1.setCellValueFactory(new PropertyValueFactory<>("student"));

        TableColumn<Enrollment, String> column2 = new TableColumn<>("Cursus");
        column2.setCellValueFactory(new PropertyValueFactory<>("course"));

        TableColumn<Enrollment, Date> column3 = new TableColumn<>("Datum");
        column3.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

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

        // Creating index button + setting event handler
        Button detailButton = new Button("Bekijken");
        detailButton.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white;");
        detailButton.setOnAction((event) -> viewEnrollment(event, tableView, enrollmentModel, stage));

        // Creating edit button + setting event handler
        Button editButton = new Button("Aanpassen");
        editButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        editButton.setOnAction((event) -> editRowFromTable(event, tableView, stage));

        // Creating create button + setting event handler
        Button createButton = new Button("Aanmaken");
        createButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        createButton.setOnAction((event) -> createEnrollment(event, stage));

        // Creating delete button + setting event handler
        Button deleteButton = new Button("Verwijder");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        deleteButton.setOnAction((event) -> deleteRowFromTable(event, tableView, enrollmentModel));

        // Creating HBox for buttons
        HBox buttonBox = new HBox(backButton, detailButton, editButton, createButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creatoing VBox for all components
        VBox vbox = new VBox(tableView, buttonBox);
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

    private static void editRowFromTable(ActionEvent event, TableView<Enrollment> tableView, Stage stage) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt aanpassen");
            warningAlert.show();
        } else {

            // Retrieving enrollment object from table
            Enrollment selectedEnrollment = (Enrollment) tableView.getSelectionModel().getSelectedItem();

            // Edit page
            EditEnrollment editEnrollment = new EditEnrollment();

            // Try to open new page
            try {
                stage.setScene(editEnrollment.getView(stage, selectedEnrollment));
            } catch (SQLException e) {
                e.printStackTrace();
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

    private static void viewEnrollment(ActionEvent event, TableView<Enrollment> tableView, EnrollmentModel enrollmentModel, Stage stage) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een student die je wilt bekijken");
            warningAlert.show();
        } else {

            // Retrieving Enrollment object from table
            Enrollment selectedEnrollment = (Enrollment) tableView.getSelectionModel().getSelectedItem();

            // Edit page
            ViewEnrollment viewEnrollment = new ViewEnrollment();

            // Try to open new page
            try {
                stage.setScene(viewEnrollment.getView(enrollmentModel, selectedEnrollment, stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
