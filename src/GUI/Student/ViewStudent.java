package GUI.Student;

import java.sql.SQLException;

import Database.StudentModel;
import Domain.Student;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewStudent {

    public static Scene getView(StudentModel studentModel, Student student, Stage stage) throws SQLException {

        // Settimg stage title
        stage.setTitle("CodeCademy | " + student.getName() + " bekijken");

        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> goBackToIndex(event, stage));

        // Creating HBox for buttons
        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creating vBox with all components
        VBox vbox = new VBox(buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        
        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }

    public static void goBackToIndex(Event event, Stage stage) {
        // Returning to student index
        try {
            stage.setScene(IndexStudent.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}