package GUI.Course;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.CourseModel;
import Domain.Course;
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

public class IndexCourse {
    public static Scene getView(Stage stage) throws SQLException {
        CourseModel coursemodel = new CourseModel();

        stage.setTitle("CodeCademy | Studenten");
        
        TableView tableView = new TableView();

        TableColumn<Course, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> column2 = new TableColumn<>("Subject");
        column2.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<Course, String> column3 = new TableColumn<>("Introduction");
        column3.setCellValueFactory(new PropertyValueFactory<>("introduction"));

        TableColumn<Course, String> column4 = new TableColumn<>("Level");
        column4.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<Course, String> column5 = new TableColumn<>("Content Items");
        column5.setCellValueFactory(new PropertyValueFactory<>("contentitems"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);

        ArrayList<Course> course = coursemodel.getCourses();

        for (Course c : course) {
            tableView.getItems().add(c);
        }

         // Creating back button + setting event handler
         Button backButton = new Button("Ga terug");
         backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
         backButton.setOnAction((event) -> stage.setScene(App.getView(stage)));
 
         // Creating index button + setting event handler
         Button detailButton = new Button("Bekijken");
         detailButton.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white;");
         detailButton.setOnAction((event) -> viewCourse(event, tableView, coursemodel, stage));
 
         // Creating edit button + setting event handler
         Button editButton = new Button("Aanpassen");
         editButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
         editButton.setOnAction((event) -> editRowFromTable(event, tableView, stage));
 
         // Creating create button + setting event handler
         Button createButton = new Button("Aanmaken");
         createButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
         createButton.setOnAction((event) -> createCourse(event, stage));
 
         // Creating delete button + setting event handler
         Button deleteButton = new Button("Verwijder");
         deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
         deleteButton.setOnAction((event) -> deleteRowFromTable(event, tableView, coursemodel));
 
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

    
    private static void deleteRowFromTable(ActionEvent event, TableView tableView, CourseModel courseModel) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt verwijderen");
            warningAlert.show();
        } else {

            // Retrieving selected course object
            Course selectedCourse = (Course) tableView.getSelectionModel().getSelectedItem();

            // Deleting course from database
            if (CourseModel.deleteCourse(selectedCourse.getName())) {

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

    private static void editRowFromTable(ActionEvent event, TableView tableView, Stage stage) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt aanpassen");
            warningAlert.show();
        } else {

            // Retrieving Course object from table
            Course selectedCourse = (Course) tableView.getSelectionModel().getSelectedItem();

            // Edit page
            EditCourse editCourse = new EditCourse();

            // Try to open new page
            try {
                stage.setScene(editCourse.getView(stage, selectedCourse));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createCourse(ActionEvent event, Stage stage) {

        // Create page
        CreateCourse createCourse = new CreateCourse();

        // Try to open new page
        try {
            stage.setScene(createCourse.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewCourse(ActionEvent event, TableView tableView, CourseModel courseModel, Stage stage) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een course die je wilt bekijken");
            warningAlert.show();
        } else {

            // Retrieving course object from table
            Course selectedCourse = (Course) tableView.getSelectionModel().getSelectedItem();

            // Edit page
            ViewCourse viewCourse = new ViewCourse();

            // Try to open new page
            try {
                stage.setScene(viewCourse.getView(courseModel, selectedCourse, stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
