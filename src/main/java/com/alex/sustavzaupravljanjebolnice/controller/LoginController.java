package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.AdminRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.repository.ReceptionistRepo;
import com.alex.sustavzaupravljanjebolnice.util.PasswordManager;
import com.alex.sustavzaupravljanjebolnice.util.StaffCredentialSeeder;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class LoginController {
    private final Logger logger = Logger.getLogger(String.valueOf(LoginController.class));

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;

    private final DoctorRepo doctorRepo = new DoctorRepo();
    private final NurseRepo nurseRepo = new NurseRepo();
    private final AdminRepo adminRepo = new AdminRepo();
    private final ReceptionistRepo receptionistRepo = new ReceptionistRepo();

    public void initialize() {
        usernameTextField.setText("Alice Smith");
        StaffCredentialSeeder.seedStaffCredentials();
    }

    @FXML
    public void login(ActionEvent event) throws Exception {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText();

        if (username.isBlank() || password.isBlank()) {
            loginMessageLabel.setText("Please enter a username and password.");
            return;
        }

        File credentialStorage = new File("passwords.properties");
        PasswordManager pm = new PasswordManager(credentialStorage);

        if (pm.verifyPassword(username, password)) {

            List<Staff> staffList = new ArrayList<>();
            staffList.addAll(doctorRepo.getAll());
            staffList.addAll(nurseRepo.getAll());
            staffList.addAll(adminRepo.getAll());
            staffList.addAll(receptionistRepo.getAll());

            Staff loggedInStaff = null;
            for (Staff staff : staffList) {
                String fullName = staff.getFirstName() + " " + staff.getLastName();
                if (fullName.equalsIgnoreCase(username)) {
                    loggedInStaff = staff;
                    break;
                }
            }

            if (loggedInStaff != null) {
                UserSession.getInstance().setLoggedInStaff(loggedInStaff);
                loginMessageLabel.setText("Login successful! Welcome " + username);
                logger.info("User " + username + " successfully logged in. Role: " + loggedInStaff.getRole());

                String role = loggedInStaff.getRole() != null ? loggedInStaff.getRole().toString().toUpperCase() : "";

                switch (role) {
                    case "DOCTOR":
                        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/doctor-overview.fxml", "Doctor Dashboard");
                        break;
                    case "NURSE":
                        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/nurse-view.fxml", "Nurse Dashboard");
                        break;
                    case "RECEPTIONIST":
                        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/receptionist-view.fxml", "Receptionist Dashboard");
                        break;
                    case "ADMIN":
                        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/hospital-overview.fxml", "Hospital Admin Overview");
                        break;
                    default:
                        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/hello-view.fxml", "Hospital Management System");
                        break;
                }

            } else {
                AlertBox.show("Login Error", "Credentials verified, but profile details missing from database.");
                loginMessageLabel.setText("Staff profile details not found.");
            }

        } else {
            AlertBox.show("Login Failed", "Invalid username or password.");
            loginMessageLabel.setText("Invalid username or password.");
        }
    }

    private void navigateTo(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            logger.severe("Failed to transition scenes to fxml path: " + fxmlPath);
            e.printStackTrace();
            AlertBox.show("Navigation Error", "Could not load screen: " + fxmlPath);
        }
    }
}