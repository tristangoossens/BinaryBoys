package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage window) {
        window.setTitle("CodeCademy | Home");

        Button openStudentWindow = new Button("Studenten");

        VBox vbox = new VBox(openStudentWindow);
        Scene scene = new Scene(vbox, 800, 500);
        vbox.setAlignment(Pos.CENTER);

        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(App.class);
    }
}
