package GUI.Certificate;

import Database.CertificateModel;
import Domain.Certificate;
import Domain.Employee;
import Domain.Enrollment;
import Validation.GradeValidation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateCertificate {
    public Scene getView(Stage stage, Enrollment enrollment) {
        // Initializing certificate model
        CertificateModel cm = new CertificateModel();

        // Settimg stage title
        stage.setTitle("CodeCademy | Certificaat toekennen");

        // Creating grid to put in form
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(25, 25, 25, 25));
        
        // Creating title
        Text scenetitle = new Text("Certificaat toekennen");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        formGrid.add(scenetitle, 0, 0);

        // Email and course name
        Label emailCourse = new Label("Student (Niet aanpasbaar): ");
        formGrid.add(emailCourse, 0, 1);
        TextField emailTextfield = new TextField();
        emailTextfield.setDisable(true);
        emailTextfield.setPromptText(enrollment.getStudent().getEmail());
        formGrid.add(emailTextfield, 1, 1);

        // Email and course name
        Label course = new Label("Cursus (Niet aanpasbaar): ");
        formGrid.add(course, 0, 2);
        TextField courseTextField = new TextField();
        courseTextField.setDisable(true);
        courseTextField.setPromptText(enrollment.getCourse().getName());
        formGrid.add(courseTextField, 1, 2);

        // Employee number
        Label employeeLabel = new Label("Werknemersnummer: ");
        formGrid.add(employeeLabel, 0, 3);
        ComboBox<String> cbEmployeeNumber = new ComboBox<>();
        for(Employee employee : cm.getEmployees()){
            cbEmployeeNumber.getItems().add(String.valueOf(employee.getNumber()));
        }
        formGrid.add(cbEmployeeNumber, 1, 3);

        // Grade
        Label grade = new Label("Cijfer: ");
        formGrid.add(grade, 0, 4);
        TextField gradTextField = new TextField();
        formGrid.add(gradTextField, 1, 4);

        // Creating cancel button + setting event handler
        Button cancelButton = new Button("Annuleren");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cancelButton.setOnAction((event) -> cancelButton(stage));

        // Creating save button
        Button saveButton = new Button("Opslaan");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // Setting event handler save button
        saveButton.setOnAction((event) -> {
            Certificate c = new Certificate(
                -1, 
                enrollment, 
                cm.getEmployeeByNumber(Integer.parseInt(cbEmployeeNumber.getValue())), 
                Integer.parseInt(gradTextField.getText())
            );

            System.out.println(c.getGrade());

            saveCertificate(stage, c);
        });

        // Creating HBox for buttons
        HBox buttonBox = new HBox(cancelButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Creating vBox with all components
        VBox vbox = new VBox(formGrid, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        // Returning scene
        Scene scene = new Scene(vbox, 1200, 500);
        return scene;
    }

    public static void cancelButton(Stage stage) {
        // Returning to student index
        try {
            stage.setScene(IndexCertificate.getView(stage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveCertificate(Stage stage, Certificate certificate){
        // Initializing contentitem model
        CertificateModel cm = new CertificateModel();

        if(GradeValidation.validateGrade(certificate.getGrade())){
            // Calling the insert method for a module
            if (cm.createCertificate(certificate)) {
                // If succesvol show alert
                Alert succesfullAlert = new Alert(AlertType.CONFIRMATION);
                succesfullAlert.setContentText("Het certificaat is successvol toegewezen! :)");
                succesfullAlert.show();

                // Going back to overview page
                try {
                    stage.setScene(IndexCertificate.getView(stage));
                } catch(Exception e){
                    e.printStackTrace();
                }
                
            } else {
                // If failed show alert
                Alert succesfullAlert = new Alert(AlertType.WARNING);
                succesfullAlert.setContentText("Er is iets fout gegaan bij het toewijzen van het certificaat :(");
                succesfullAlert.show();
            }
        }else{
            Alert warningAlert = new Alert(AlertType.WARNING);
            warningAlert.setContentText("Het ingevulde cijfer is incorrect (1-10)");
            warningAlert.show();
        }
    }
}
