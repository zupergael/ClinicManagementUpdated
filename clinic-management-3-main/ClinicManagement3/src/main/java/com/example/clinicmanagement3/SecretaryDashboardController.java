package com.example.clinicmanagement3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class SecretaryDashboardController {

    @FXML private Button appointmentsButton;
    @FXML private Button patientConsultationButton;
    @FXML private Button logoutButton;

    @FXML
    protected void handlePatientConsultationClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SecretaryPatientConsultation.fxml"));
            Parent consultationRoot = loader.load();
            Stage stage = (Stage) patientConsultationButton.getScene().getWindow();
            stage.setScene(new Scene(consultationRoot));
            stage.setTitle("Clinic Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleAppointmentsClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SecretaryAppointments.fxml"));
            Parent appointmentsRoot = loader.load();
            Stage stage = (Stage) appointmentsButton.getScene().getWindow();
            stage.setScene(new Scene(appointmentsRoot));
            stage.setTitle("Clinic Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent signUpRoot = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(signUpRoot));
            stage.setTitle("Clinic Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}