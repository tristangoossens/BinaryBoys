package GUI.Student;

import java.sql.SQLException;

import Student.Students;
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

        // int y = 1;

        // for (Student student : students.getStudents().values()) {
        //     layout.add(new Label(student.getEmail()), 0, y);
        //     layout.add(new Label(student.getName()), 1, y);
        //     layout.add(new Label(student.getBirthDate().toString()), 2, y);
        //     layout.add(new Label(student.getGender()), 3, y);
        //     y++;
        // }

        VBox vbox = new VBox(layout);
        Scene scene = new Scene(vbox, 800, 500);
        vbox.setAlignment(Pos.CENTER);

        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) throws SQLException {
        Students students = new Students();
        System.out.println(students.toString());
        launch(Index.class);
    }
}
