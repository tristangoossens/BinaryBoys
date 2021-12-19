package GUI.Student;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.StudentModel;
import Domain.Student;
import GUI.App;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Index {

    public Scene getView(Stage stage) throws SQLException {
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

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        
        StudentModel sm = new StudentModel();
        ArrayList<Student> students = sm.getStudents();

        for (Student student : students) {
            tableView.getItems().add(student);
        }

        VBox vbox = new VBox(backButton, tableView);
        vbox.setSpacing(20);
        Scene scene = new Scene(vbox, 800, 500);
        vbox.setAlignment(Pos.CENTER);

        return scene;
    }
}
