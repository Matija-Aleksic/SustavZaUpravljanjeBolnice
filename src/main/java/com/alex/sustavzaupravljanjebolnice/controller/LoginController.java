package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Staff;
import com.alex.sustavzaupravljanjebolnice.util.AlertBox;
import com.alex.sustavzaupravljanjebolnice.util.PasswordManager;
import com.alex.sustavzaupravljanjebolnice.util.StaffCredentialSeeder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
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
    private Staff loggedInStaff;

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

            loginMessageLabel.setText("Login successful!");
        } else {
            AlertBox.show("Login Failed", "Invalid username or password.");
            loginMessageLabel.setText("Invalid username or password.");
        }
    }
}