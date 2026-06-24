package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.controller.popup.DoctorDialogController;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.AppointmentRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.WindowManager;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import com.alex.sustavzaupravljanjebolnice.util.boxes.ConfirmationBox;
import com.alex.sustavzaupravljanjebolnice.util.boxes.InfoBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorViewController {
    private static final Logger log = LoggerFactory.getLogger(DoctorViewController.class);
    private final Staff loggedInStaff = UserSession.getInstance().getLoggedInStaff();
    private final DoctorRepo doctorRepo = new DoctorRepo();
    private final PatientRepo patientRepo = new PatientRepo();
    private final AppointmentRepo appointmentRepo = new AppointmentRepo();
    @FXML
    private TableView<Doctor> doctorsTable;
    @FXML
    private TableColumn<Doctor, String> doctorColumn;
    @FXML
    private ImageView picture;
    @FXML
    private Label nameSurname;
    @FXML
    private Label oib;
    @FXML
    private Label role;
    @FXML
    private Label email;
    @FXML
    private Label salary;
    @FXML
    private Label hospital;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label address;

    // Sub-tables
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> patientNameColumn;
    @FXML
    private TableColumn<Patient, String> patientOibColumn;

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, String> appointmentDateColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentPatientColumn;

    private List<Patient> patients = List.of();
    private List<Appointment> allAppointments = List.of();

    @FXML
    public void initialize() {
        doctorColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName() + " " + c.getValue().getLastName()));
        patientNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName() + " " + c.getValue().getLastName()));
        patientOibColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOib()));
        appointmentDateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().dateTime().toString()));
        appointmentPatientColumn.setCellValueFactory(c -> {
            Appointment a = c.getValue();
            String pName = patients.stream().filter(p -> Objects.equals(p.getId(), a.patientId())).map(p -> p.getFirstName() + " " + p.getLastName()).findFirst().orElse("Unknown");
            return new SimpleStringProperty(pName);
        });

        doctorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showDoctorDetails(newSelection);
            } else {
                clearDoctorDetails();
            }
        });

        reload();
    }

    private void loadData() throws SQLException {
        List<Doctor> doctors = doctorRepo.getAll().stream().filter(d -> d.getHospital() != null && Objects.equals(d.getHospital().getId(), loggedInStaff.getHospital().getId())).toList();
        patients = patientRepo.getAll();
        allAppointments = appointmentRepo.getAll();

        Platform.runLater(() -> {
            doctorsTable.setItems(FXCollections.observableArrayList(doctors));
            Doctor selected = doctorsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showDoctorDetails(selected);
            } else {
                clearDoctorDetails();
            }
        });
        log.info("Doctors overview data synchronized securely.");
    }

    private void showDoctorDetails(Doctor doctor) {
        nameSurname.setText(doctor.getFirstName() + " " + doctor.getLastName());
        oib.setText("OIB: " + doctor.getOib());
        role.setText("Role: Doctor");
        email.setText("Email: " + doctor.getEmail());
        salary.setText("Salary: " + doctor.getSalary() + " €");
        hospital.setText("Hospital: " + (doctor.getHospital() != null ? doctor.getHospital().getName() : "N/A"));
        phoneNumber.setText("Phone: " + doctor.getPhoneNumber());
        address.setText("Address: " + doctor.getAddress());

        List<Appointment> filteredAppointments = allAppointments.stream().filter(a -> Objects.equals(a.doctorId(), doctor.getId())).toList();
        appointmentsTable.setItems(FXCollections.observableArrayList(filteredAppointments));
        Set<Integer> assignedPatientIds = filteredAppointments.stream().map(Appointment::patientId).collect(Collectors.toSet());
        List<Patient> filteredPatients = patients.stream().filter(p -> assignedPatientIds.contains(p.getId())).toList();
        patientsTable.setItems(FXCollections.observableArrayList(filteredPatients));
    }

    private void clearDoctorDetails() {
        nameSurname.setText("");
        oib.setText("");
        role.setText("");
        email.setText("");
        salary.setText("");
        hospital.setText("");
        phoneNumber.setText("");
        address.setText("");
        appointmentsTable.getItems().clear();
        patientsTable.getItems().clear();
    }

    @FXML
    private void handleAddDoctor() {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/doctor-dialog.fxml", "Add New Doctor Profile", DoctorDialogController::setNewDoctorContext, c -> {
            if (c.isSaved()) reload();
        });
    }

    @FXML
    private void handleEditDoctor() {
        Doctor selected = doctorsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.show("Warning", "Please select a doctor to edit.");
            return;
        }

        WindowManager.<DoctorDialogController>showModal("/com/alex/sustavzaupravljanjebolnice/doctor-dialog.fxml", "Edit Doctor Profile", c -> c.setDoctor(selected), c -> {
            if (c.isSaved()) reload();
        });
    }

    @FXML
    private void handleDeleteDoctor() {
        Doctor selected = doctorsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.show("Warning", "Please select a doctor record to delete.");
            return;
        }

        boolean confirmed = ConfirmationBox.show("Are you sure?", "Delete doctor: " + selected.getFirstName() + " " + selected.getLastName() + "?\nThis action cannot be undone.");
        if (!confirmed) return;

        Thread.startVirtualThread(() -> {
            try {
                doctorRepo.deleteById((long) selected.getId());
                log.info("Successfully deleted doctor context profile ID: {}", selected.getId());
                Platform.runLater(() -> {
                    reload();
                    InfoBox.show("Success");
                });
            } catch (SQLException e) {
                log.error("Failed to discard doctor record", e);
                Platform.runLater(() -> AlertBox.show("Database Reference Conflict", e.getMessage()));
            }
        });
    }

    private void reload() {
        Thread.startVirtualThread(() -> {
            try {
                loadData();
            } catch (SQLException e) {
                log.error("Failed async reload", e);
                Platform.runLater(() -> AlertBox.show("SQL Processing Failure", e.getMessage()));
            }
        });
    }
}