package com.example.clinicmanagement3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class SecretaryPatientConsulController {

    @FXML private Button backButton;
    @FXML private Button logoutButton;

    @FXML
    protected void handleBackClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SecretaryDashboard.fxml"));
            Parent dashboardRoot = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Secretary Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleLogoutClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent signUpRoot = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(signUpRoot));
            stage.setTitle("Clinic Management - Sign In");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}