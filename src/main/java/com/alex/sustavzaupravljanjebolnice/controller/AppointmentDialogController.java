package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.repository.AppointmentRepo;
import com.alex.sustavzaupravljanjebolnice.util.AlertBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class AppointmentDialogController {

    private final AppointmentRepo appointmentRepo = new AppointmentRepo();
    @FXML
    private ComboBox<Doctor> doctorCombo;
    @FXML
    private ComboBox<Patient> patientCombo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField; // Format HH:mm
    private boolean isSaved = false;
    private Integer currentAppointmentId = null;

    @FXML
    public void initialize() {
        doctorCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Doctor d) {
                return d == null ? "" : d.getFirstName() + " " + d.getLastName();
            }

            @Override
            public Doctor fromString(String s) {
                return null;
            }
        });

        patientCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Patient p) {
                return p == null ? "" : p.getFirstName() + " " + p.getLastName() + " (" + p.getOib() + ")";
            }

            @Override
            public Patient fromString(String s) {
                return null;
            }
        });
    }

    public void setMaps(Map<Integer, Doctor> doctorMap, Map<Integer, Patient> patientMap) {
        doctorCombo.setItems(FXCollections.observableArrayList(doctorMap.values()));
        patientCombo.setItems(FXCollections.observableArrayList(patientMap.values()));
    }

    public void setAppointment(Appointment appointment) {
        this.currentAppointmentId = appointment.id();

        doctorCombo.getItems().stream().filter(d -> d.getId() == appointment.doctorId()).findFirst().ifPresent(doctorCombo::setValue);

        patientCombo.getItems().stream().filter(p -> p.getId() == appointment.patientId()).findFirst().ifPresent(patientCombo::setValue);

        datePicker.setValue(appointment.dateTime().toLocalDate());
        timeField.setText(appointment.dateTime().toLocalTime().toString());
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if (!validateInput()) return;

        try {
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(timeField.getText().trim());
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            int doctorId = doctorCombo.getValue().getId();
            int patientId = patientCombo.getValue().getId();

            Appointment newAppointment = new Appointment(currentAppointmentId == null ? 0 : currentAppointmentId, doctorId, patientId, dateTime);

            if (currentAppointmentId == null) {
                appointmentRepo.save(newAppointment);
            } else {
                appointmentRepo.update(newAppointment);
            }

            isSaved = true;
            closeStage();

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to save appointment: " + e.getMessage());
        } catch (Exception _) {
            showAlert("Format Error", "Please ensure the time is in HH:mm format (e.g., 14:30)");
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        closeStage();
    }

    public boolean isSaved() {
        return isSaved;
    }

    private boolean validateInput() {
        if (doctorCombo.getValue() == null || patientCombo.getValue() == null || datePicker.getValue() == null || timeField.getText().isBlank()) {
            showAlert("Validation Error", "All fields must be filled out.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        AlertBox.show(title, content);
    }

    private void closeStage() {
        Stage stage = (Stage) doctorCombo.getScene().getWindow();
        stage.close();
    }
}