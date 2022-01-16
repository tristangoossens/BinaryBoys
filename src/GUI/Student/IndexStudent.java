package GUI.Student;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Database.StudentModel;
import Domain.Student;
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

public class IndexStudent {

    public static Scene getView(Stage stage) throws SQLException {
        // Creating student model
        StudentModel studentModel = new StudentModel();

        // Settimg stage title
        stage.setTitle("CodeCademy | Studenten");

        // Title tab
        Text titleText = new Text("Studenten");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        // Creating table view
        TableView<Student> tableView = new TableView<>();

        // Setting data table view
        TableColumn<Student, String> column1 = new TableColumn<>("E-mail");
        column1.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Student, String> column2 = new TableColumn<>("Voornaam");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, LocalDate> column3 = new TableColumn<>("Geboortedatum");
        column3.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<Student, String> column4 = new TableColumn<>("Geslacht");
        column4.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGender().getValue()));

        TableColumn<Student, String> column5 = new TableColumn<>("Adres");
        column5.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Student, String> column6 = new TableColumn<>("Stad");
        column6.setCellValueFactory(new PropertyValueFactory<>("city"));
        
        TableColumn<Student, String> column7 = new TableColumn<>("Postcode");
        column7.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<Student, String> column8 = new TableColumn<>("Land");
        column8.setCellValueFactory(new PropertyValueFactory<>("country"));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        tableView.getColumns().add(column8);

        // Retrieving all students
        ArrayList<Student> students = studentModel.getStudents();

        // Looping through students + adding to table
        for (Student student : students) {
            tableView.getItems().add(student);
        }

        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> stage.setScene(App.getView(stage)));

        // Creating index button + setting event handler
        Button detailButton = new Button("Voortgang");
        detailButton.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white;");
        detailButton.setOnAction((event) -> viewStudent(event, tableView, stage));

        // Creating edit button + setting event handler
        Button editButton = new Button("Aanpassen");
        editButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        editButton.setOnAction((event) -> editStudent(event, tableView, stage));

        // Creating create button + setting event handler
        Button createButton = new Button("Aanmaken");
        createButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        createButton.setOnAction((event) -> createStudent(event, stage));

        // Creating delete button + setting event handler
        Button deleteButton = new Button("Verwijder");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        deleteButton.setOnAction((event) -> deleteStudent(event, tableView, studentModel));

        // Creating HBox for buttons
        HBox buttonBox = new HBox(backButton, detailButton, editButton, createButton, deleteButton);
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

    private static void deleteStudent(ActionEvent event, TableView<Student> tableView, StudentModel studentModel) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt verwijderen");
            warningAlert.show();
        } else {

            // Retrieving selected student object
            Student selectedStudent = (Student) tableView.getSelectionModel().getSelectedItem();

            // Deleting student from database
            if (studentModel.deleteStudent(selectedStudent.getEmail())) {

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

    private static void editStudent(ActionEvent event, TableView<Student> tableView, Stage stage) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt aanpassen");
            warningAlert.show();
        } else {

            // Retrieving student object from table
            Student selectedStudent = (Student) tableView.getSelectionModel().getSelectedItem();

            // Edit page
            EditStudent editStudent = new EditStudent();

            // Try to open new page
            try {
                stage.setScene(editStudent.getView(stage, selectedStudent));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createStudent(ActionEvent event, Stage stage) {

        // Create page
        CreateStudent createStudent = new CreateStudent();

        // Try to open new page
        try {
            stage.setScene(createStudent.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewStudent(ActionEvent event, TableView<Student> tableView, Stage stage) {

        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een student die je wilt bekijken");
            warningAlert.show();
        } else {

            // Retrieving student object from table
            Student selectedStudent = (Student) tableView.getSelectionModel().getSelectedItem();

            // Edit page
            ViewStudent viewStudent = new ViewStudent();

            // Try to open new page
            try {
                stage.setScene(viewStudent.getView(selectedStudent, stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}