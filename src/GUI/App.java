package GUI;

import java.sql.SQLException;

import GUI.Student.IndexStudent;
import GUI.Course.IndexCourse;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage window) {
        window.setScene(getView(window));
        window.show();
    }

    public static Scene getView(Stage stage){
        stage.setTitle("CodeCademy | Home");

        Button openStudentWindow = new Button("😃 Bekijk studenten");
        openStudentWindow.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:20;");
   
        openStudentWindow.setOnAction((event) -> {
            try {
                stage.setScene(IndexStudent.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button openCourseWindow = new Button("🎓 Bekijk cursussen");
        openCourseWindow.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:20;");

        openCourseWindow.setOnAction((event) -> {
            try {
                stage.setScene(IndexCourse.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button openCertificates = new Button("✅ Bekijk certificaten");
        openCertificates.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:20;");

        openCertificates.setOnAction((event) -> {
            // try {
            //     stage.setScene(IndexCourse.getView(stage));
            // } catch (SQLException e) {
            //     e.printStackTrace();
            // }
        });


        Text homeScreenTitle = new Text("Welkom op het CodeCademy portaal");
        homeScreenTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        HBox buttonBox = new HBox(openStudentWindow, openCourseWindow, openCertificates);
        buttonBox.setSpacing(40);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(homeScreenTitle, buttonBox);
        mainBox.setSpacing(60);
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 1200, 500);

        return scene;
    }

    public static void main(String[] args) {
        launch(App.class);
    }
}
