package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.exception.PasswordManagerException;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The type Login controller.
 */
public class LoginController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);
    private final Logger logger = Logger.getLogger(LoginController.class.getName());

    private final DoctorRepo doctorRepo = new DoctorRepo();
    private final NurseRepo nurseRepo = new NurseRepo();
    private final AdminRepo adminRepo = new AdminRepo();
    private final ReceptionistRepo receptionistRepo = new ReceptionistRepo();

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    /**
     * Initialize.
     */
    public void initialize() {
        usernameTextField.setText("Zrinka Krpan");
        passwordTextField.setText("Zrinka");
        StaffCredentialSeeder.seedStaffCredentials();
    }

    /**
     * Login.
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    public void login(ActionEvent event) {

        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText();

        if (!validateInput(username, password)) {
            return;
        }
        try {
            PasswordManager pm = new PasswordManager(new File("passwords.properties"));

            if (!pm.verifyPassword(username, password)) {
                handleInvalidLogin();
                return;
            }
        } catch (PasswordManagerException | IOException e) {
            AlertBox.show("Unable to find passwords file", "or unable to save password");
            log.error(e.getMessage());
        }


        Staff loggedInStaff = findStaffByUsername(username);

        if (loggedInStaff == null) {
            handleMissingProfile();
            return;
        }

        completeLogin(event, username, loggedInStaff);
    }

    private boolean validateInput(String username, String password) {

        if (username.isBlank() || password.isBlank()) {
            loginMessageLabel.setText("Please enter a username and password.");
            return false;
        }

        return true;
    }

    private void handleInvalidLogin() {
        AlertBox.show("Login Failed", "Invalid username or password.");
        loginMessageLabel.setText("Invalid username or password.");
    }

    private void handleMissingProfile() {
        AlertBox.show("Login Error", "Credentials verified, but profile details missing from database.");
        loginMessageLabel.setText("Staff profile details not found.");
    }

    private Staff findStaffByUsername(String username) {

        List<Staff> staffList = new ArrayList<>();
        try {
            staffList.addAll(doctorRepo.getAll());
            staffList.addAll(nurseRepo.getAll());
            staffList.addAll(adminRepo.getAll());
            staffList.addAll(receptionistRepo.getAll());
        } catch (SQLException _) {
            AlertBox.show("Login Error", "Database connection error.");
        }


        return staffList.stream().filter(staff -> (staff.getFirstName() + " " + staff.getLastName()).equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    private void completeLogin(ActionEvent event, String username, Staff loggedInStaff) {

        UserSession.getInstance().setLoggedInStaff(loggedInStaff);

        loginMessageLabel.setText("Login successful! Welcome " + username);

        logger.info(() -> "User %s successfully logged in. Role: %s".formatted(username, loggedInStaff.getRole()));

        navigateBasedOnRole(event, loggedInStaff);
    }

    private void navigateBasedOnRole(ActionEvent event, Staff loggedInStaff) {

        String role = loggedInStaff.getRole() != null ? loggedInStaff.getRole().toString().toUpperCase() : "";

        switch (role) {

            case "DOCTOR" ->
                    navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/doctor-overview.fxml", "Doctor Dashboard");

            case "NURSE" ->
                    navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/nurse-view.fxml", "Nurse Dashboard");

            case "RECEPTIONIST" ->
                    navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/receptionist-view.fxml", "Receptionist Dashboard");

            case "ADMIN" ->
                    navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/hospital-overview.fxml", "Hospital Admin Overview");
            default ->
                    navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/hospital-overview.fxml", "Hospital Admin Overview");

        }
    }

    private void navigateTo(ActionEvent event, String fxmlPath, String title) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            com.alex.sustavzaupravljanjebolnice.util.WindowManager.switchScene(stage, fxmlPath, title, 1400, 850);

            stage.centerOnScreen();
            stage.show();

        } catch (Exception _) {
            logger.severe("Failed to transition scenes to fxml path: " + fxmlPath);
            AlertBox.show("Navigation Error", "Could not load screen: " + fxmlPath);
        }
    }
}