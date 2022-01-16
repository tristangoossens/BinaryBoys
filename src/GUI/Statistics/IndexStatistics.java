package GUI.Statistics;

import java.util.ArrayList;
import java.util.HashMap;

import Database.StatisticsModel;
import GUI.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IndexStatistics {
    public static Scene getView(Stage stage) {
        // Setting stage title
        stage.setTitle("CodeCademy | Statistieken bekijken");

        // Set title text
        Text scenetitle = new Text("Statistieken codecademy");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Accordion accordion = new Accordion();
        
        TitledPane pane1 = new TitledPane("1: Slagingspercentage per geslacht" , getPane1Content());
        TitledPane pane2 = new TitledPane("2: Per cursus een gemiddelde voortgang van alle modules"  , getPane2Content());
        TitledPane pane3 = new TitledPane("3: Per account en per cursus, de voortgang per module", getPane3Content());
        TitledPane pane4 = new TitledPane("4: Per account de behaalde certificaten tonen" , getPane4Content());
        TitledPane pane5 = new TitledPane("5: Top 3 meest bekeken webcasts"  , getPane5Content());
        TitledPane pane6 = new TitledPane("6: Top 3 cursussen met meeste gecertificeerde studenten", getPane6Content());
        TitledPane pane7 = new TitledPane("7: Aanbevolen cursussen voor cursus" , getPane7Content());
        TitledPane pane8 = new TitledPane("8: Per cursus het totaal aantal keer behaald"  , getPane8Content());

        accordion.getPanes().add(pane1);
        accordion.getPanes().add(pane2);
        accordion.getPanes().add(pane3);
        accordion.getPanes().add(pane4);
        accordion.getPanes().add(pane5);
        accordion.getPanes().add(pane6);
        accordion.getPanes().add(pane7);
        accordion.getPanes().add(pane8);

        // Create a back button
        Button backButton = new Button("Ga terug");
        backButton.setStyle("-fx-background-color: #343a40; -fx-text-fill: white;");
        backButton.setOnAction((event) -> stage.setScene(App.getView(stage)));



        // Creating VBox for all components
        VBox vbox = new VBox(scenetitle, accordion, backButton);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);

        return scene;

    }

    public static VBox getPane1Content() {
        StatisticsModel sm = new StatisticsModel();
        
        // Get statistic data
        HashMap<String, HashMap<String, Integer>> graduationInfo = sm.getGenderCompletionData();
        HashMap<String, Integer> geslaagd = graduationInfo.get("Geslaagd");
        HashMap<String, Integer> totaal = graduationInfo.get("Totaal");

        if(geslaagd.get("Man") == null){
            geslaagd.put("Man", 0);
        }

        if(geslaagd.get("Vrouw") == null){
            geslaagd.put("Vrouw", 0);
        }

        if(totaal.get("Man") == null){
            totaal.put("Man", 0);
        }

        if(totaal.get("Vrouw") == null){
            totaal.put("Vrouw", 0);
        }

        // Title men
        Text menText = new Text("Mannen");
        menText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

        Text menStatistics = new Text(String.format("Totaal ingeschreven : %d\nTotaal geslaagd : %d\nPercentage : %.1f procent", totaal.get("Man"), geslaagd.get("Man"), (double) (geslaagd.get("Man")) / totaal.get("Man") * 100));
        menStatistics.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));

        // Women text
        Text womenText = new Text("Vrouwen");
        womenText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

        Text womenStatistics = new Text(String.format("Totaal ingeschreven : %d\nTotaal geslaagd : %d\nPercentage : %.1f procent", totaal.get("Vrouw"), geslaagd.get("Vrouw"), (double) (geslaagd.get("Vrouw")) / totaal.get("Vrouw") * 100));
        womenStatistics.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));

        // Init vBox
        VBox vbox = new VBox(menText, menStatistics, womenText, womenStatistics);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    public static VBox getPane2Content() {
        StatisticsModel sm = new StatisticsModel();
        ArrayList<String> result = sm.getAverageModuleProgress();

        // Creating table view
        TableView<String> tableView = new TableView<>();

        // Setting data table view
        TableColumn<String, String> column1 = new TableColumn<>("E-mail");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[0]));

        TableColumn<String, String> column2 = new TableColumn<>("Naam cursus");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1].split("->")[0]));

        TableColumn<String, String> column3 = new TableColumn<>("Gemiddeld percentage");
        column3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1].split("->")[1]));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        for (String entry : result) {
            tableView.getItems().add(entry);
        }

        // Init vBox
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    public static VBox getPane3Content() {
        StatisticsModel sm = new StatisticsModel();
        ArrayList<String> result = sm.getStudentCourseModuleProgresses();

        // Creating table view
        TableView<String> tableView = new TableView<>();

        // Setting data table view
        TableColumn<String, String> column1 = new TableColumn<>("E-mail");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[0]));

        TableColumn<String, String> column2 = new TableColumn<>("Naam cursus");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1].split("->")[0]));

        TableColumn<String, String> column3 = new TableColumn<>("Titel module");
        column3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1].split("->")[1]));

        TableColumn<String, String> column4 = new TableColumn<>("Percentage");
        column4.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1].split("->")[2]));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        for (String entry : result) {
            tableView.getItems().add(entry);
        }

        // Init vBox
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    public static VBox getPane4Content() {
        StatisticsModel sm = new StatisticsModel();
        ArrayList<String> result = sm.getStudentCertificates();

        // Creating table view
        TableView<String> tableView = new TableView<>();

        // Setting data table view
        TableColumn<String, String> column1 = new TableColumn<>("E-mail");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[0]));

        TableColumn<String, String> column2 = new TableColumn<>("Naam cursus");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1].split("->")[0]));

        TableColumn<String, String> column3 = new TableColumn<>("Cijfer certificaat");
        column3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1].split("->")[1]));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        for (String entry : result) {
            tableView.getItems().add(entry);
        }

        // Init vBox
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    public static VBox getPane5Content() {
        StatisticsModel sm = new StatisticsModel();
        ArrayList<String> result = sm.getTop3Webcasts();
        
        StringBuilder sb = new StringBuilder();

        for(int i = 1; i <= result.size(); i++){
            sb.append(String.format("%d. %s (%s keer bekeken)\n", i, result.get(i-1).split(":")[0], result.get(i-1).split(":")[1]));
        }

        // Title tab
        Text titleText = new Text("Top 3 webcasts");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Text top3Text = new Text(sb.toString());
        top3Text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));

        // Init vBox
        VBox vbox = new VBox(titleText, top3Text);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    public static VBox getPane6Content() {
        StatisticsModel sm = new StatisticsModel();
        ArrayList<String> result = sm.getTop3CoursesWithMostCertificates();
        
        StringBuilder sb = new StringBuilder();

        for(int i = 1; i <= result.size(); i++){
            sb.append(String.format("%d. %s (%s certificat(en) uitgegeven)\n", i, result.get(i-1).split(":")[0], result.get(i-1).split(":")[1]));
        }

        // Title tab
        Text titleText = new Text("Top 3 meest behaalde cursussen");
        titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Text top3Text = new Text(sb.toString());
        top3Text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));

        // Init vBox
        VBox vbox = new VBox(titleText, top3Text);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    public static VBox getPane7Content() {
        StatisticsModel sm = new StatisticsModel();
        ArrayList<String> result = sm.getCoursesWithRelatedCourses();
        
        // Creating table view
        TableView<String> tableView = new TableView<>();

        // Setting data table view
        TableColumn<String, String> column1 = new TableColumn<>("Cursus");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[0]));

        TableColumn<String, String> column2 = new TableColumn<>("Gerelateerde cursus");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1]));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        for(String entry: result){
            tableView.getItems().add(entry);
        }

        // Init vBox
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }

    public static VBox getPane8Content() {
        StatisticsModel sm = new StatisticsModel();
        ArrayList<String> result = sm.getCourseCompletionCount();
        
        // Creating table view
        TableView<String> tableView = new TableView<>();

        // Setting data table view
        TableColumn<String, String> column1 = new TableColumn<>("Naam cursus");
        column1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[0]));

        TableColumn<String, String> column2 = new TableColumn<>("Aantal certificaten");
        column2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().split(":")[1]));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        for(String entry: result){
            tableView.getItems().add(entry);
        }

        // Init vBox
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);

        return vbox;
    }
}
