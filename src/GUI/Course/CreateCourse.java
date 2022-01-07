package GUI.Course;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateCourse {
    
    public Scene getView(Stage stage) throws SQLException {

        // Settimg stage title
        stage.setTitle("CodeCademy | Student aanmaken");

        // Creating vBox with all components
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }
}
