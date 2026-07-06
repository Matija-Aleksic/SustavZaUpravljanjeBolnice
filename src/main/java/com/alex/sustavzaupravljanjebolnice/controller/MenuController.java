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
 * The type Menu controller.
 */
public class MenuController {

    private static final Logger logger = Logger.getLogger(MenuController.class.getName());

    /**
     * Handle hospital.
     *
     * @param event the event
     */
    @FXML
    public void hospitals(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/hospital-overview.fxml", "Hospital Overview");
    }

    /**
     * Handle doctors.
     *
     * @param event the event
     */
    @FXML
    public void doctors(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/doctor-overview.fxml", "Doctors Overview");
    }

    /**
     * Handle nurses.
     *
     * @param event the event
     */
    @FXML
    public void nurses(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/nurse-view.fxml", "Nurses Overview");
    }

    /**
     * Handle patients.
     *
     * @param event the event
     */
    @FXML
    public void patients(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/patient-view.fxml", "Patients");

    }

    /**
     * Handle appointments.
     *
     * @param event the event
     */
    @FXML
    public void appointments(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/receptionist-view.fxml", "Appointment Overview");
    }

    /**
     * Handle prescriptions.
     *
     * @param event the event
     */
    @FXML
    public void prescriptions(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/prescription-view.fxml", "Perscriptions");
    }

    /**
     * Handle logs.
     *
     * @param event the event
     */
    @FXML
    public void logs(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/activity-log-view.fxml", "Activity Logs");
    }

    /**
     * Handle logout.
     *
     * @param event the event
     */
    @FXML
    public void logout(ActionEvent event) {
        logger.info("Logout requested by user: " + (UserSession.getInstance().getLoggedInStaff().getFirstName() + " " + UserSession.getInstance().getLoggedInStaff().getLastName()));
        UserSession.getInstance().cleanUserSession();
        AlertBox.show("Logout Successful", "You have been logged out successfully.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.switchScene(stage, "/com/alex/sustavzaupravljanjebolnice/login.fxml", "Hospital Management System - Login", 400, 600);
    }

    private void navigateTo(ActionEvent event, String fxmlPath, String title) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowManager.switchScene(stage, fxmlPath, title, 1400, 1000);
            logger.info(() -> "Successfully navigated to: %s".formatted(fxmlPath));
        } catch (Exception _) {
            logger.severe(() -> "Failed to navigate to: %s".formatted(fxmlPath));
            AlertBox.show("Navigation Error", "Could not load screen: " + fxmlPath);
        }
    }
}