package GUI.Student;

import java.sql.SQLException;

import Database.StudentModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateStudent {

    public static Scene getView(Stage stage) throws SQLException {
        // Creating student model
        StudentModel studentModel = new StudentModel();

        // Settimg stage title
        stage.setTitle("CodeCademy | Student aanmaken");

        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> {
            try {
                stage.setScene(IndexStudent.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        
        VBox vbox = new VBox(backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }
}
