package com.alex.sustavzaupravljanjebolnice.controller.popup;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.repository.AppointmentRepo;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AppointmentDialogController {

    private final AppointmentRepo appointmentRepo = new AppointmentRepo();

    @FXML
    private ComboBox<Doctor> doctorCombo;
    @FXML
    private ComboBox<Patient> patientCombo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;

    private Integer currentAppointmentId = null;
    private boolean saved = false;

    @FXML
    public void initialize() {

        doctorCombo.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Doctor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
            }
        });

        doctorCombo.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Doctor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
            }
        });

        patientCombo.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
            }
        });

        patientCombo.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
            }
        });
    }

    public void setData(List<Doctor> doctors, List<Patient> patients) {
        doctorCombo.getItems().setAll(doctors);
        patientCombo.getItems().setAll(patients);
    }

    public void setAppointment(Appointment a) {
        this.currentAppointmentId = a.id();

        doctorCombo.getItems().stream().filter(d -> d.getId() == a.doctorId()).findFirst().ifPresent(doctorCombo::setValue);

        patientCombo.getItems().stream().filter(p -> p.getId() == a.patientId()).findFirst().ifPresent(patientCombo::setValue);

        datePicker.setValue(a.dateTime().toLocalDate());
        timeField.setText(a.dateTime().toLocalTime().toString());
    }

    public void setNewAppointmentContext() {
        currentAppointmentId = null;
        doctorCombo.getSelectionModel().clearSelection();
        patientCombo.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        timeField.clear();
    }

    @FXML
    public void handleSave(ActionEvent event) {

        if (!validate()) return;

        try {
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(timeField.getText().trim());

            Appointment a = new Appointment(currentAppointmentId == null ? 0 : currentAppointmentId, doctorCombo.getValue().getId(), patientCombo.getValue().getId(), LocalDateTime.of(date, time));

            if (currentAppointmentId == null) {
                appointmentRepo.save(a);
            } else {
                appointmentRepo.update(a);
            }

            saved = true;
            close();

        } catch (SQLException e) {
            AlertBox.show("Database Error", e.getMessage());
        } catch (Exception _) {
            AlertBox.show("Format Error", "Use HH:mm format (e.g. 14:30)");
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        close();
    }

    public boolean isSaved() {
        return saved;
    }

    private boolean validate() {
        if (doctorCombo.getValue() == null || patientCombo.getValue() == null || datePicker.getValue() == null || timeField.getText().isBlank()) {

            AlertBox.show("Validation Error", "All fields are required.");
            return false;
        }
        return true;
    }

    private void close() {
        Stage stage = (Stage) doctorCombo.getScene().getWindow();
        stage.close();
    }
}