package GUI.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import Database.ProgressModel;
import Domain.ContentItem;
import Domain.Progress;
import Domain.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewStudent {

    public Scene getView(Student student, Stage stage) throws SQLException {
        // Creating progress model
        ProgressModel progressModel = new ProgressModel();

        // Setting stage title
        stage.setTitle("CodeCademy | " + student.getName() + " bekijken");

        // Create a tabpane and create tabs for webcasts and modules
        TabPane tabPane = new TabPane();
        Tab modulesTab = new Tab("Modules", getModuleTabContent(progressModel, student, stage));
        modulesTab.setClosable(false);
        Tab webcastsTab = new Tab("Webcasts", getWebcastTabContent(progressModel, student));
        webcastsTab.setClosable(false);

        // Add tabs to tabpane children
        tabPane.getTabs().add(modulesTab);

        tabPane.getTabs().add(webcastsTab);

        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> goBackToIndex(event, stage));

        // Creating HBox for the back button
        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creating VBox for all components
        VBox vbox = new VBox(tabPane, buttonBox);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }

    private HBox getWebcastTabContent(ProgressModel progressModel, Student student) {
        // Creating table view
        TableView<ContentItem> tableView = new TableView<>();

        // Setting data table view
        TableColumn<ContentItem, String> column1 = new TableColumn<>("Titel");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<ContentItem, String> column2 = new TableColumn<>("Status");
        column2.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<ContentItem, String> column3 = new TableColumn<>("Beschrijving");
        column3.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        // Retrieving all webcasts from student
        ArrayList<Progress> webcasts = progressModel.readProgresStudent(student, 'w');

        // Looping through webcasts and adding them to tableview
        for (Progress webcast : webcasts) {
            tableView.getItems().add(webcast.getContentItem());
        }

        HBox hbox = new HBox(tableView, getProgressContent(webcasts));
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setSpacing(10);

        return hbox;
    }

    private HBox getModuleTabContent(ProgressModel progressModel, Student student, Stage stage) {
        // Creating table view
        TableView<Progress> tableView = new TableView<>();

        // Setting data table view
        TableColumn<Progress, String> column1 = new TableColumn<>("Titel");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContentItem().getTitle()));

        TableColumn<Progress, String> column2 = new TableColumn<>("Status");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContentItem().getStatus()));

        TableColumn<Progress, String> column3 = new TableColumn<>("Beschrijving");
        column3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContentItem().getDescription()));

        TableColumn<Progress, String> column4 = new TableColumn<>("Percentage");
        column4.setCellValueFactory(new PropertyValueFactory<>("percentage"));


        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);


        // Retrieving all modules from student
        ArrayList<Progress> modules = progressModel.readProgresStudent(student, 'm');

        // Looping through all modules from student
        for (Progress module : modules) {
            tableView.getItems().add(module);
        }

        HBox hbox = new HBox(tableView);
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setSpacing(10);

        return hbox;
    }

    private TableView<Progress> getProgressContent(ArrayList<Progress> progressList) {
        // Creating table view
        TableView<Progress> tableView = new TableView<>();

        // Setting data table view
        TableColumn<Progress, String> column1 = new TableColumn<>("Percentage voltooid");
        column1.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        // Setting columns for data table view
        tableView.getColumns().add(column1);

        for (Progress progress : progressList) {
            tableView.getItems().add(progress);
        }

        return tableView;
    }

    private static void goBackToIndex(Event event, Stage stage) {
        // Returning to student index
        try {
            stage.setScene(IndexStudent.getView(stage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
