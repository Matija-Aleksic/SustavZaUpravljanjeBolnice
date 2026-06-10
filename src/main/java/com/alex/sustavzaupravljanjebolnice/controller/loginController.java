package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.util.AlertBox;
import com.alex.sustavzaupravljanjebolnice.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;


    public void initialize() {
        usernameTextField.setText("Alice Smith");

    }

    @FXML
    public void login(ActionEvent event) throws Exception {
        if (usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank()) {
            loginMessageLabel.setText("Please enter a username and password.");
        } else {
            PasswordManager pm = new PasswordManager("src/main/resources/passwords.properties");
            if (pm.verifyPassword(usernameTextField.getText(), passwordTextField.getText())) {
                loginMessageLabel.setText("Login successful!");

//                Db.setUserRole(usernameTextField.getText());

//                System.out.println("Logged in as: " + Db.user);
//                System.out.println("Is Manager? " + Db.isManager);

                //               MenuBarController mbc = new MenuBarController();
                //               mbc.showOverview();
            } else {
                AlertBox.show("Login Failed", "Invalid username or password.");
                loginMessageLabel.setText("Invalid username or password.");
                return;
            }
            loginMessageLabel.setText("Login attempt successful! (Placeholder)");
//            System.out.println("Username: " + usernameTextField.getText());
//            System.out.println("Password: " + passwordTextField.getText());
        }
    }
}
