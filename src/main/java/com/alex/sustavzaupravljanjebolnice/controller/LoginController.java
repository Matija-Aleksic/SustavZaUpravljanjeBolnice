package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.AdminRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.repository.ReceptionistRepo;
import com.alex.sustavzaupravljanjebolnice.util.AlertBox;
import com.alex.sustavzaupravljanjebolnice.util.PasswordManager;
import com.alex.sustavzaupravljanjebolnice.util.StaffCredentialSeeder;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

                // TODO: Put your standard JavaFX scene-switching code here to load the dashboard!

            } else {
                AlertBox.show("Login Error", "Credentials verified, but profile details missing from database.");
                loginMessageLabel.setText("Staff profile details not found.");
            }

        } else {
            AlertBox.show("Login Failed", "Invalid username or password.");
            loginMessageLabel.setText("Invalid username or password.");
        }
    }
}