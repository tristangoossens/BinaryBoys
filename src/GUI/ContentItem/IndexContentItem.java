package GUI.ContentItem;

import java.sql.SQLException;
import java.util.ArrayList;

import Database.ContentItemModel;
import Domain.ContentItem;
import Domain.Course;
import Domain.Module;
import Domain.Webcast;
import GUI.App;
import GUI.Module.CreateModule;
import GUI.Module.EditModule;
import GUI.Webcast.CreateWebcast;
import GUI.Webcast.EditWebcast;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IndexContentItem {

    public static Scene getView(Stage stage, Course course) throws SQLException {
        // Creating contentItem model
        ContentItemModel contentItemModel = new ContentItemModel();

        // Setting stage title
        stage.setTitle("CodeCademy | Content for IT");

        // Create a tabpane and create tabs for webcasts and modules
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Modules", getModuleTabContent(contentItemModel, course, stage));
        tab1.setClosable(false);
        Tab tab2 = new Tab("Webcasts", getWebcastTabContent(contentItemModel, course, stage));
        tab2.setClosable(false);

        // Add tabs to tabpane children
        tabPane.getTabs().add(tab1);
        
        tabPane.getTabs().add(tab2);


        // Creating back button + setting event handler
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> stage.setScene(App.getView(stage)));

        // Creating HBox the back button
        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        

        // Creatoing VBox for all components
        VBox vbox = new VBox(tabPane, buttonBox);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(20);


        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }

    private static VBox getWebcastTabContent(ContentItemModel cim, Course course, Stage stage){
        // Creating table view
        TableView<Webcast> tableView = new TableView<>();

        // Setting data table view
        TableColumn<Webcast, String> column1 = new TableColumn<>("Titel");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Webcast, String> column2 = new TableColumn<>("Status");
        column2.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Webcast, String> column3 = new TableColumn<>("Beschrijving");
        column3.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Webcast, String> column4 = new TableColumn<>("Publicatie datum");
        column4.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));


        TableColumn<Webcast, String> column5 = new TableColumn<>("URL");
        column5.setCellValueFactory(new PropertyValueFactory<>("url"));

        TableColumn<Webcast, String> column6 = new TableColumn<>("Duur webcast");
        column6.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<Webcast, String> column7 = new TableColumn<>("Naam spreker");
        column7.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSpeaker().getName()));

        TableColumn<Webcast, String> column8 = new TableColumn<>("Organisatie spreker");
        column8.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSpeaker().getOrganisation()));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        tableView.getColumns().add(column8);

        // Retrieving all webcasts for the given course
        ArrayList<Webcast> webcasts = cim.getWebcastsForCourse(course.getName());

        // Looping through webcasts + adding to table
        for (Webcast webcast : webcasts) {
            tableView.getItems().add(webcast);
        }

        // Creating delete button + setting event handler
        Button deleteButton = new Button("Verwijder");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        deleteButton.setOnAction((event) -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                // Send alert, no row selected
                Alert warningAlert = new Alert(AlertType.WARNING);
                warningAlert.setContentText("Selecteer een record die je wilt verwijderen");
                warningAlert.show();

            }else{
                ContentItem contentItem = (ContentItem) tableView.getSelectionModel().getSelectedItem();
                if(deleteContentItem(event, contentItem.getID(), cim)){
                    // If delete method returns true, delete row form table
                    tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
                }
            } 
        });

        // Creating create button + setting event handler
        Button createButton = new Button("Aanmaken");
        createButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        createButton.setOnAction((event) -> createContentItem(stage, false, course));

        // Creating HBox for buttons
        HBox buttonBox = new HBox(deleteButton, createButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        VBox vbox = new VBox(tableView, buttonBox);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    private static VBox getModuleTabContent(ContentItemModel cim, Course course, Stage stage){
        // Creating table view
        TableView<Module> tableView = new TableView<>();

        // Setting data table view
        TableColumn<Module, String> column1 = new TableColumn<>("Titel");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Module, String> column2 = new TableColumn<>("Status");
        column2.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Module, String> column3 = new TableColumn<>("Beschrijving");
        column3.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Module, String> column4 = new TableColumn<>("Publicatie datum");
        column4.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        TableColumn<Module, String> column5 = new TableColumn<>("Versie");
        column5.setCellValueFactory(new PropertyValueFactory<>("version"));

        TableColumn<Module, String> column6 = new TableColumn<>("Volgorde nummer");
        column6.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));

        TableColumn<Module, String> column7 = new TableColumn<>("Naam contactpersoon");
        column7.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContactPerson().getName()));

        TableColumn<Module, String> column8 = new TableColumn<>("Email contactpersoon");
        column8.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContactPerson().getEmail()));

        // Setting columns for data table view
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        tableView.getColumns().add(column8);


        // Retrieving all modules for the given course
        ArrayList<Module> modules = cim.getModulesForCourse(course.getName());

        // Looping through webcasts + adding to table
        for (Module module : modules) {
            tableView.getItems().add(module);
        }

        // Creating delete button + setting event handler
        Button deleteButton = new Button("Verwijder");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        deleteButton.setOnAction((event) -> {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                // Send alert, no row selected
                Alert warningAlert = new Alert(AlertType.WARNING);
                warningAlert.setContentText("Selecteer een record die je wilt verwijderen");
                warningAlert.show();

            }else{
                ContentItem contentItem = (ContentItem) tableView.getSelectionModel().getSelectedItem();
                if(deleteContentItem(event, contentItem.getID(), cim)){
                    // If delete method returns true, delete row form table
                    tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
                }
            } 
        });

        // Creating edit button + setting event handler
        Button editButton = new Button("Aanpassen");
        editButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        editButton.setOnAction((event) -> editModule(stage, tableView, course));

        // Creating create button + setting event handler
        Button createButton = new Button("Aanmaken");
        createButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        createButton.setOnAction((event) -> createContentItem(stage, true, course));

        // Creating HBox for buttons
        HBox buttonBox = new HBox(createButton, editButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        VBox vbox = new VBox(tableView, buttonBox);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    private static boolean deleteContentItem(ActionEvent event, Integer id, ContentItemModel contentItemModel) {
        // Deleting student from database
        if (contentItemModel.deleteContentItem(id)) {
            // Succes message
            Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
            succesfullAlert.setContentText("Record is succesvol verwijderd");
            succesfullAlert.show();

            return true;

        } else {
            // If delete method returns false, alert to user
            Alert succesfullAlert = new Alert(AlertType.WARNING);
            succesfullAlert.setContentText("Er is iets fout gegaan bij het verwijderen :(");
            succesfullAlert.show();
            
        }

        return false;
    }

    private static void createContentItem(Stage stage, boolean isModule, Course course) {
        Scene scene;

        // Check if the content item to be made is a module
        if(isModule){
            scene = CreateModule.getView(stage, course);
        }else{
            CreateWebcast createWebcast = new CreateWebcast();
            scene = createWebcast.getView(stage, course);
        }

        // Open new page
        stage.setScene(scene);
    }

    private static void editModule(Stage stage, TableView<Module> tableView, Course course){
        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt aanpassen");
            warningAlert.show();
        } else {
            // Retrieving module object from table
            Module selectedModule = (Module) tableView.getSelectionModel().getSelectedItem();
            // Init Edit page
            EditModule editModule = new EditModule();

            Scene scene = editModule.getView(stage, selectedModule, course);
            stage.setScene(scene); 
        }
    }

    private static void editWebcast(Stage stage, TableView<Webcast> tableView){
        // Check if row is selected
        if (tableView.getSelectionModel().getSelectedItem() == null) {

            // Send alert, no row selected
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Selecteer een record die je wilt aanpassen");
            warningAlert.show();
        } else {
            // Retrieving webcast object from table
            Webcast selectedWebcast = (Webcast) tableView.getSelectionModel().getSelectedItem();
            Scene scene  = EditWebcast.getView(stage, selectedWebcast);
            stage.setScene(scene); 
        }
    }
}