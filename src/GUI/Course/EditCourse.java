package GUI.Course;

import java.sql.SQLException;

import Domain.Course;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditCourse {
    public Scene getView(Stage stage, Course course) throws SQLException {
        
        // Setting stage title
        stage.setTitle("CodeCademy | " + course.getName() + " aanpassen");

        // Creating vBox with all components
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }
}
