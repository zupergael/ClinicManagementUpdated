package com.example.clinicmanagement3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SecretaryAppointmentsController implements Initializable {

    @FXML private TableView<SecretaryAppointment> appointmentTable;
    @FXML private TableColumn<SecretaryAppointment, String> timeColumn;
    @FXML private TableColumn<SecretaryAppointment, String> patientColumn;
    @FXML private TableColumn<SecretaryAppointment, String> serviceColumn;
    @FXML private TableColumn<SecretaryAppointment, String> statusColumn;
    @FXML private TableColumn<SecretaryAppointment, Void> actionColumn;

    @FXML private Button backButton;
    @FXML private Button logoutButton;

    private final ObservableList<SecretaryAppointment> appointments = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadAppointments();
        addManageButtonToTable();
    }

    private void loadAppointments() {
        appointments.clear();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
                SELECT a.appointment_time, a.service, a.status, u.full_name
                FROM appointments a
                JOIN user u ON a.user_id = u.id
                ORDER BY a.appointment_time ASC
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                appointments.add(new SecretaryAppointment(
                        rs.getString("appointment_time"),
                        rs.getString("service"),
                        rs.getString("status"),
                        rs.getString("full_name")
                ));
            }

            appointmentTable.setItems(appointments);

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addManageButtonToTable() {
        Callback<TableColumn<SecretaryAppointment, Void>, TableCell<SecretaryAppointment, Void>> cellFactory = param -> new TableCell<>() {
            private final Button manageBtn = new Button("Manage");

            {
                manageBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                manageBtn.setOnAction(event -> {
                    SecretaryAppointment appointment = getTableView().getItems().get(getIndex());
                    handleManage(appointment);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : manageBtn);
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void handleManage(SecretaryAppointment appointment) {
        System.out.println("Managing appointment for: " + appointment.getFullName());
    }

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