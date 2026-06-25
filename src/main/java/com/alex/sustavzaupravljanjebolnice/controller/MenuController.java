package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.WindowManager;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * Controller for managing menu navigation and user logout functionality.
 * This controller is used in the menu.fxml and handles all menu item click events
 * as well as the logout functionality.
 */
public class MenuController {

    private static final Logger logger = Logger.getLogger(MenuController.class.getName());

    /**
     * Navigates to the Hospital view
     */
    @FXML
    public void handleHospital(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/hospital-overview.fxml", "Hospital Overview", 1000);
    }

    /**
     * Navigates to the Doctors view
     */
    @FXML
    public void handleDoctors(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/doctor-overview.fxml", "Doctors Overview", 1000);
    }

    /**
     * Navigates to the Nurses view
     */
    @FXML
    public void handleNurses(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/nurse-view.fxml", "Nurses Overview", 1000);
    }

    /**
     * Navigates to the Patients view
     * (Placeholder for future implementation)
     */
    @FXML
    public void handlePatients() {
        AlertBox.show("Patients", "Patients module coming soon!");
        logger.info("Patients navigation requested");
    }

    /**
     * Navigates to the Appointments view
     */
    @FXML
    public void handleAppointments(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/receptionist-view.fxml", "Appointment Overview", 1000);
    }

    /**
     * Navigates to the Prescriptions view
     * (Placeholder for future implementation)
     */
    @FXML
    public void handlePrescriptions(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/prescription-view.fxml", "Perscriptions", 1000);
    }

    /**
     * Navigates to the Activity Logs view
     */
    @FXML
    public void handleLogs(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/activity-log-view.fxml", "Activity Logs", 1000);
    }

    /**
     * Handles logout functionality:
     * 1. Clears the UserSession
     * 2. Displays confirmation message
     * 3. Redirects to login page
     */
    @FXML
    public void handleLogout(ActionEvent event) {
        logger.info("Logout requested by user: " + (UserSession.getInstance().getLoggedInStaff() != null ? UserSession.getInstance().getLoggedInStaff().getFirstName() + " " + UserSession.getInstance().getLoggedInStaff().getLastName() : "Unknown"));
        UserSession.getInstance().cleanUserSession();

        AlertBox.show("Logout Successful", "You have been logged out successfully.");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.switchScene(stage, "/com/alex/sustavzaupravljanjebolnice/login.fxml", "Hospital Management System - Login", 400, 600);
    }

    /**
     * Generic navigation method that loads a scene and switches to it.
     *
     * @param event    The ActionEvent that triggered the navigation
     * @param fxmlPath The path to the FXML file to load
     * @param title    The title for the stage
     * @param height   The height of the window
     */
    private void navigateTo(ActionEvent event, String fxmlPath, String title, int height) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowManager.switchScene(stage, fxmlPath, title, 1400, height);
            logger.info(() -> "Successfully navigated to: %s".formatted(fxmlPath));
        } catch (Exception _) {
            logger.severe(() -> "Failed to navigate to: %s".formatted(fxmlPath));
            AlertBox.show("Navigation Error", "Could not load screen: " + fxmlPath);
        }
    }
}





