package GUI.Student;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.ProgressModel;
import Domain.ContentItem;
import Domain.Progress;
import Domain.Student;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewStudent {

    public Scene getView(Student student, Stage stage) throws SQLException {

        // Settimg stage title
        stage.setTitle("CodeCademy | " + student.getName() + " voortgang bekijken");

        // Creating table view
        TableView tableView = new TableView<>();

        // Setting data table view
        TableColumn<ContentItem, String> column1 = new TableColumn<>("Titel");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Progress, String> column2 = new TableColumn<>("Percentage");
        column2.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        ProgressModel progressModel = new ProgressModel();

        // Retrieving all students
        ArrayList<Progress> progress = progressModel.readProgressStudent(student);

        // Looping through students + adding to table
        for (Progress item : progress) {
            tableView.getItems().add(item);
        }

        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> goBackToIndex(event, stage));

        // Creating HBox for buttons
        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creating vBox with all components
        VBox vbox = new VBox(tableView, buttonBox);
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
