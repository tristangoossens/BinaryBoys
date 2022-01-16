package GUI;

import java.sql.SQLException;

import GUI.Student.IndexStudent;
import GUI.Certificate.IndexCertificate;
import GUI.Course.IndexCourse;
import GUI.Enrollment.IndexEnrollment;
import GUI.Statistics.IndexStatistics;
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
    // Start the application
    public void start(Stage window) {
        window.setScene(getView(window));
        window.show();
    }

    // Return a Scene for this view
    public static Scene getView(Stage stage){
        stage.setTitle("CodeCademy | Home");

        Button openStudentWindow = new Button("😃 Bekijk studenten");
        openStudentWindow.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:15;");
   
        openStudentWindow.setOnAction((event) -> {
            try {
                stage.setScene(IndexStudent.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button openCourseWindow = new Button("🎓 Bekijk cursussen");
        openCourseWindow.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:15;");

        openCourseWindow.setOnAction((event) -> {
            try {
                stage.setScene(IndexCourse.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button openCertificates = new Button("✅ Toewijzen certificaten");
        openCertificates.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:15;");

        openCertificates.setOnAction((event) -> {
            try {
                stage.setScene(IndexCertificate.getView(stage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button openEnrollemnts = new Button("💻 Bekijk inschrijvingen");
        openEnrollemnts.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:15;");

        openEnrollemnts.setOnAction((event) -> {
            try {
                stage.setScene(IndexEnrollment.getView(stage));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button openStatistics = new Button("📈 Bekijk overzichten");
        openStatistics.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size:15;");

        openStatistics.setOnAction((event) -> {
            try {
                stage.setScene(IndexStatistics.getView(stage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        Text homeScreenTitle = new Text("Welkom op het CodeCademy portaal");
        homeScreenTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        HBox buttonBox = new HBox(openStudentWindow, openCourseWindow, openCertificates, openEnrollemnts, openStatistics);
        buttonBox.setSpacing(40);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(homeScreenTitle, buttonBox);
        mainBox.setSpacing(60);
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 1200, 500);

        return scene;
    }

    // Main class to run application (entry point)
    public static void main(String[] args) {
        launch(App.class);
    }
}
