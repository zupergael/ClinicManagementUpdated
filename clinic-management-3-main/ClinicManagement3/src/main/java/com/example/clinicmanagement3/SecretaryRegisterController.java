package com.example.clinicmanagement3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SecretaryRegisterController {

    @FXML private TextField fullNameField;
    @FXML private TextField ageField;
    @FXML private DatePicker birthdayPicker;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField departmentField;
    @FXML private TextField emergencyContactField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label warningLabel;
    @FXML private Label ageWarningLabel;
    @FXML private Label emergencyWarningLabel;
    @FXML private Button registerButton;

    private String selectedAccountType;

    @FXML
    public void initialize() {
        genderComboBox.setPromptText("Select Gender");
        genderComboBox.getItems().addAll("Male", "Female", "Other");
    }

    @FXML
    protected void handleRegisterClick() {
        String fullName = fullNameField.getText();
        String age = ageField.getText();
        var birthday = birthdayPicker.getValue();
        String gender = genderComboBox.getValue();
        String department = departmentField.getText();
        String emergencyContact = emergencyContactField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Reset styles and warnings
        warningLabel.setText("");
        ageWarningLabel.setText("");
        emergencyWarningLabel.setText("");

        fullNameField.setStyle("");
        ageField.setStyle("");
        birthdayPicker.setStyle("");
        genderComboBox.setStyle("");
        departmentField.setStyle("");
        emergencyContactField.setStyle("");
        usernameField.setStyle("");
        passwordField.setStyle("");

        boolean hasMissingField = false;
        boolean hasInvalidNumber = false;

        if (fullName == null || fullName.isEmpty()) {
            fullNameField.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }
        if (age == null || age.isEmpty()) {
            ageField.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }
        if (birthday == null) {
            birthdayPicker.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }
        if (gender == null || gender.isEmpty()) {
            genderComboBox.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }
        if (department == null || department.isEmpty()) {
            departmentField.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }
        if (emergencyContact == null || emergencyContact.isEmpty()) {
            emergencyContactField.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }
        if (username == null || username.isEmpty()) {
            usernameField.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }
        if (password == null || password.isEmpty()) {
            passwordField.setStyle("-fx-border-color: red;");
            hasMissingField = true;
        }

        if (hasMissingField) {
            warningLabel.setStyle("-fx-text-fill: red;");
            warningLabel.setText("Please fill in all fields before registering.");
            return;
        }

        if (!age.matches("\\d+")) {
            ageField.setStyle("-fx-border-color: red;");
            ageWarningLabel.setText("Age must be a number.");
            hasInvalidNumber = true;
        }
        if (!emergencyContact.matches("\\d+")) {
            emergencyContactField.setStyle("-fx-border-color: red;");
            emergencyWarningLabel.setText("Emergency contact must be numeric.");
            hasInvalidNumber = true;
        }

        if (hasInvalidNumber) {
            return;
        }

        warningLabel.setText("");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO user_secretary (full_name, age, birthday, gender, department, emergency_contact, username, password, account_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, age);
            stmt.setString(3, birthday.toString());
            stmt.setString(4, gender);
            stmt.setString(5, department);
            stmt.setString(6, emergencyContact);
            stmt.setString(7, username);
            stmt.setString(8, password);
            stmt.setString(9, selectedAccountType);

            stmt.executeUpdate();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent signupRoot = loader.load();
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(signupRoot));
            stage.setTitle("Clinic Management");
            stage.show();

        } catch (SQLException | IOException e) {
            warningLabel.setStyle("-fx-text-fill: red;");
            warningLabel.setText("Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBackToLogin(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Clinic Management");
            stage.show();
        } catch (IOException e) {
            warningLabel.setText("Unable to return to login.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBackToAccountType(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountType.fxml"));
            Parent accountTypeRoot = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(accountTypeRoot));
            stage.setTitle("Clinic Management");
            stage.show();
        } catch (IOException e) {
            warningLabel.setText("Unable to return to Account Type.");
            e.printStackTrace();
        }
    }

    public void setAccountType(String accountType) {
        this.selectedAccountType = accountType;
        System.out.println("Received account type in SecretaryRegisterController: " + accountType);
    }
}