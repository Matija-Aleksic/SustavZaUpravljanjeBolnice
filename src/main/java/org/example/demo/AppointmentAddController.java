package org.example.demo;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DataManager;
import util.DialogUtils;
import util.XmlLogger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * The type Appointment add controller.
 */
public class AppointmentAddController {
    @FXML
    private ComboBox<Doctor> doctorComboBox;
    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        List<Doctor> doctors = DataManager.loadAllData().doctors();
        List<Patient> patients = DataManager.loadAllData().patients();
        doctorComboBox.setItems(FXCollections.observableArrayList(doctors));
        patientComboBox.setItems(FXCollections.observableArrayList(patients));
        doctorComboBox.setCellFactory(cb -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Doctor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullName());
            }
        });
        doctorComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Doctor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullName());
            }
        });
        patientComboBox.setCellFactory(cb -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullName());
            }
        });
        patientComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullName());
            }
        });
    }

    /**
     * On save.
     */
    @FXML
    protected void onSave() {
        Doctor doctor = doctorComboBox.getValue();
        Patient patient = patientComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String timeStr = timeField.getText();
        if (doctor == null || patient == null || date == null || timeStr.isEmpty()) {
            DialogUtils.showError("Validation Error", "All fields are required.");
            return;
        }
        try {
            int id = getNextAppointmentId();
            LocalTime time = LocalTime.parse(timeStr);
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            Appointment appointment = new Appointment(id, doctor, patient, dateTime);
            DataManager.addAppointment(appointment);
            XmlLogger.logAction("ADD_APPOINTMENT", "Added appointment: " + id);
            DialogUtils.showInfo("Success", "Appointment added successfully.");
            closeWindow();
        } catch (Exception e) {
            DialogUtils.showError("Error", "Failed to add appointment: " + e.getMessage());
        }
    }

    private int getNextAppointmentId() {
        List<Appointment> appointments = DataManager.loadAllData().appointments();
        return appointments.stream().mapToInt(Appointment::getId).max().orElse(0) + 1;
    }

    /**
     * On cancel.
     */
    @FXML
    protected void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) doctorComboBox.getScene().getWindow();
        stage.close();
    }
}
