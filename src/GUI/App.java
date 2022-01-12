package GUI;

import java.sql.SQLException;

import GUI.Student.IndexStudent;
import GUI.ContentItem.IndexContentItem;
import GUI.Course.IndexCourse;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage window) {
        window.setScene(getView(window));
        window.show();
    }

    public static Scene getView(Stage stage){
        stage.setTitle("CodeCademy | Home");

        Button openStudentWindow = new Button("Studenten");
                
        openStudentWindow.setOnAction((event) -> {
            try {
                stage.setScene(IndexStudent.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button openCourseWindow = new Button("Courses");
                
        openCourseWindow.setOnAction((event) -> {
            try {
                stage.setScene(IndexCourse.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button openContentItemWindow = new Button("Content-items");
                
        openContentItemWindow.setOnAction((event) -> {
            try {
                stage.setScene(IndexContentItem.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(openStudentWindow, openCourseWindow, openContentItemWindow);
        Scene scene = new Scene(vbox, 1200, 500);
        vbox.setAlignment(Pos.CENTER);

        return scene;
    }

    public static void main(String[] args) {
        launch(App.class);
    }
}
