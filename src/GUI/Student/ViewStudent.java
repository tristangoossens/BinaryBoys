package GUI.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import Database.ProgressModel;
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

        // Create a tabpane
        TabPane tabPane = new TabPane();

        // Create tab for modules + filling with tableview
        Tab modulesTab = new Tab("Modules", getTabContent(progressModel, student, stage, 'm'));
        modulesTab.setClosable(false);
        
        // Create tab for webcasts + filling with tableview
        Tab webcastsTab = new Tab("Webcasts", getTabContent(progressModel, student, stage, 'w'));
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

    private VBox getTabContent(ProgressModel progressModel, Student student, Stage stage, char typeOfContent) {
        // Creating table view
        TableView<Progress> tableView = new TableView<>();

        // Setting data table view
        TableColumn<Progress, String> column1 = new TableColumn<>("Titel");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContentItem().getTitle()));

        TableColumn<Progress, String> column2 = new TableColumn<>("Status");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContentItem().getStatus().getValue()));

        TableColumn<Progress, String> column3 = new TableColumn<>("Beschrijving");
        column3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContentItem().getDescription()));

        TableColumn<Progress, String> column4 = new TableColumn<>("Percentage voltooid");
        column4.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        // Creating arrayList to store progress
        ArrayList<Progress> progress = new ArrayList<>();

        // Retrieving all progress from student based on char given
        if(typeOfContent == 'm'){
            progress  = progressModel.readProgresStudent(student, typeOfContent);
        } else if(typeOfContent == 'w'){
            progress  = progressModel.readProgresStudent(student, typeOfContent);
        } else{
            System.out.format("Error while retrieving contentitems.");
        }
        
        // Looping through all contentitems from student
        for (Progress item : progress) {
            tableView.getItems().add(item);
        }

        // Creating Hbox with tableview
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        // Returing HBox with tableview
        return vbox;
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
