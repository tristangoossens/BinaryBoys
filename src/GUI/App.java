package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage window) {
        window.setTitle("CodeCademy | Home");
        window.show();
    }

    public static void main(String[] args) {
        launch(App.class);
    }
}
