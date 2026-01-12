package org.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.BackupManager;
import util.DataManager;
import util.DialogUtils;
import util.XmlLogger;

import java.io.IOException;

/**
 * The type Menubar controller.
 */
public class MenubarController {
    @FXML
    private javafx.scene.layout.BorderPane mainBorderPane;

    /**
     * On search hospitals.
     */
    @FXML
    protected void onSearchHospitals() {
        XmlLogger.logAction("MENU_SEARCH_HOSPITALS", "User selected search hospitals");
        openWindow("hospital-search.fxml", "Search Hospitals");
    }

    /**
     * On search doctors.
     */
    @FXML
    protected void onSearchDoctors() {
        XmlLogger.logAction("MENU_SEARCH_DOCTORS", "User selected search doctors");
        openWindow("doctor-search.fxml", "Search Doctors");
    }

    /**
     * On search patients.
     */
    @FXML
    protected void onSearchPatients() {
        XmlLogger.logAction("MENU_SEARCH_PATIENTS", "User selected search patients");
        openWindow("patient-search.fxml", "Search Patients");
    }

    /**
     * On search appointments.
     */
    @FXML
    protected void onSearchAppointments() {
        XmlLogger.logAction("MENU_SEARCH_APPOINTMENTS", "User selected search appointments");
        openWindow("appointment-search.fxml", "Search Appointments");
    }

    /**
     * On add hospital.
     */
    @FXML
    protected void onAddHospital() {
        XmlLogger.logAction("MENU_ADD_HOSPITAL", "User selected add hospital");
        openWindow("hospital-add.fxml", "Add Hospital");
    }

    /**
     * On add doctor.
     */
    @FXML
    protected void onAddDoctor() {
        XmlLogger.logAction("MENU_ADD_DOCTOR", "User selected add doctor");
        openWindow("doctor-add.fxml", "Add Doctor");
    }

    /**
     * On add patient.
     */
    @FXML
    protected void onAddPatient() {
        XmlLogger.logAction("MENU_ADD_PATIENT", "User selected add patient");
        openWindow("patient-add.fxml", "Add Patient");
    }

    /**
     * On add appointment.
     */
    @FXML
    protected void onAddAppointment() {
        XmlLogger.logAction("MENU_ADD_APPOINTMENT", "User selected add appointment");
        openWindow("appointment-add.fxml", "Add Appointment");
    }

    /**
     * On create backup.
     */
    @FXML
    protected void onCreateBackup() {
        XmlLogger.logAction("MENU_BACKUP", "User requested backup creation");
        try {
            DataManager.AllData data = DataManager.loadAllData();
            BackupManager.createBackup(data.hospitals(), data.doctors(), data.patients(), data.appointments());
            DialogUtils.showInfo("Backup", "Backup created successfully!");
        } catch (Exception e) {
            DialogUtils.showError("Backup Error", "Failed to create backup: " + e.getMessage());
        }
    }

    /**
     * On restore backup.
     */
    @FXML
    protected void onRestoreBackup() {
        XmlLogger.logAction("MENU_RESTORE", "User requested backup restoration");
        if (DialogUtils.showConfirmation("Restore Backup", "This will replace all current data. Continue?")) {
            try {
                BackupManager.BackupData backup = BackupManager.restoreBackup();
                if (backup != null) {
                    DataManager.saveAllData(backup.hospitals(), backup.doctors(), backup.patients(), backup.appointments());
                    DialogUtils.showInfo("Restore", "Backup restored successfully!");
                } else {
                    DialogUtils.showError("Restore Error", "Backup file not found or empty");
                }
            } catch (Exception e) {
                DialogUtils.showError("Restore Error", "Failed to restore backup: " + e.getMessage());
            }
        }
    }

    /**
     * On view logs.
     */
    @FXML
    protected void onViewLogs() {
        XmlLogger.logAction("MENU_VIEW_LOGS", "User requested to view logs");
        openWindow("logs-view.fxml", "Logs");


    }

    /**
     * On delete logs.
     */
    @FXML
    protected void onDeleteLogs() {
        XmlLogger.logAction("MENU_DELETE_LOGS", "User requested to delete logs");
        XmlLogger.deleteLogs();
        DialogUtils.showInfo("Logs Deleted", "All logs have been deleted.");
    }

    /**
     * On exit.
     */
    @FXML
    protected void onExit() {
        XmlLogger.logAction("MENU_EXIT", "User exited application");
        Platform.exit();
    }

    private void openWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            DialogUtils.showError("Error", "Failed to open window: " + e.getMessage());
        }
    }
}

