package GUI.Student;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.StudentModel;
import Domain.Student;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Index {

    public Scene getView(Stage stage) throws SQLException {
        StudentModel studentModel = new StudentModel();

        stage.setTitle("CodeCademy | Studenten");

        Button backButton = new Button("Ga terug");

        backButton.setOnAction((event) -> stage.setScene(App.getView(stage)));

        TableView tableView = new TableView();

        TableColumn<Student, String> column1 = new TableColumn<>("E-mail");
        column1.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Student, String> column2 = new TableColumn<>("Voornaam");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> column3 = new TableColumn<>("Geboortedatum");
        column3.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<Student, String> column4 = new TableColumn<>("Geslacht");
        column4.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Student, String> column5 = new TableColumn<>("Adres");
        column5.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Student, String> column6 = new TableColumn<>("Stad");
        column6.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Student, String> column7 = new TableColumn<>("Land");
        column7.setCellValueFactory(new PropertyValueFactory<>("country"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        
        ArrayList<Student> students = studentModel.getStudents();

        for (Student student : students) {
            tableView.getItems().add(student);
        }

        Button deleteButton = new Button("Verwijder geselecteerde record");
        deleteButton.setOnAction((event) -> deleteRowFromTable(event, tableView, studentModel));

        VBox vbox = new VBox(backButton, tableView, deleteButton);
        vbox.setSpacing(20);
        Scene scene = new Scene(vbox, 1200, 500);
        vbox.setAlignment(Pos.CENTER);

        return scene;
    }

    private void deleteRowFromTable(ActionEvent event, TableView tableView, StudentModel studentModel) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt verwijderen");
            warningAlert.show();
        } else {
            //Retrieving selected student object
            Student selectedStudent = (Student)tableView.getSelectionModel().getSelectedItem();

            //Deleting student from database
            if(studentModel.deleteStudent(selectedStudent.getEmail())){
                
                //If delete method returns true, delete row form table
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());

                //Succes message
                Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
                succesfullAlert.setContentText("Record is succesvol verwijderd");
                succesfullAlert.show();

            } else{
                
                //If delete method returns false, alert to user
                Alert succesfullAlert = new Alert(AlertType.WARNING);
                succesfullAlert.setContentText("Er is iets fout gegaan bij het verwijderen :(");
                succesfullAlert.show();
            }

        }
    }
}
