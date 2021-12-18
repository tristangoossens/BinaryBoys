package GUI.Student;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.StudentModel;
import Domain.Student;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Index extends Application {

    @Override
    public void start(Stage window) throws SQLException {
        window.setTitle("CodeCademy | Studenten");

        GridPane layout = new GridPane();
        layout.setHgap(10);

        layout.add(new Label("#Email"), 0, 0);
        layout.add(new Label("Naam"), 1, 0);
        layout.add(new Label("Geboortedatum"), 2, 0);
        layout.add(new Label("Geslacht"), 3, 0);

        StudentModel sm = new StudentModel();
        ArrayList<Student> students = sm.getStudents();

        for (int y = 0; y < students.size(); y++) {
            layout.add(new Label(students.get(y).getEmail()), 0, y+1);
            layout.add(new Label(students.get(y).getName()), 1, y+1);
            layout.add(new Label(students.get(y).getBirthDate().toString()), 2, y+1);
            layout.add(new Label(students.get(y).getGender()), 3, y+1);
        }

        VBox vbox = new VBox(layout);
        Scene scene = new Scene(vbox, 800, 500);
        vbox.setAlignment(Pos.CENTER);

        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(Index.class);
    }
}
