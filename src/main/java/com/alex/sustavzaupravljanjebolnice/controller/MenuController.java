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
    public void handleHospital(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/hospital-overview.fxml", "Hospital Overview", 1000);
    }

    /**
     * Handle doctors.
     *
     * @param event the event
     */
    @FXML
    public void handleDoctors(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/doctor-overview.fxml", "Doctors Overview", 1000);
    }

    /**
     * Handle nurses.
     *
     * @param event the event
     */
    @FXML
    public void handleNurses(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/nurse-view.fxml", "Nurses Overview", 1000);
    }

    /**
     * Handle patients.
     *
     * @param event the event
     */
    @FXML
    public void handlePatients(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/patient-view.fxml", "Patients", 1000);

    }

    /**
     * Handle appointments.
     *
     * @param event the event
     */
    @FXML
    public void handleAppointments(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/receptionist-view.fxml", "Appointment Overview", 1000);
    }

    /**
     * Handle prescriptions.
     *
     * @param event the event
     */
    @FXML
    public void handlePrescriptions(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/prescription-view.fxml", "Perscriptions", 1000);
    }

    /**
     * Handle logs.
     *
     * @param event the event
     */
    @FXML
    public void handleLogs(ActionEvent event) {
        navigateTo(event, "/com/alex/sustavzaupravljanjebolnice/activity-log-view.fxml", "Activity Logs", 1000);
    }

    /**
     * Handle logout.
     *
     * @param event the event
     */
    @FXML
    public void handleLogout(ActionEvent event) {
        logger.info("Logout requested by user: " + (UserSession.getInstance().getLoggedInStaff() != null ? UserSession.getInstance().getLoggedInStaff().getFirstName() + " " + UserSession.getInstance().getLoggedInStaff().getLastName() : "Unknown"));
        UserSession.getInstance().cleanUserSession();

        AlertBox.show("Logout Successful", "You have been logged out successfully.");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.switchScene(stage, "/com/alex/sustavzaupravljanjebolnice/login.fxml", "Hospital Management System - Login", 400, 600);
    }

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





