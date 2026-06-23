package com.alex.sustavzaupravljanjebolnice.controller;

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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ReceptionistViewController {

    private static final Logger log = LoggerFactory.getLogger(ReceptionistViewController.class);

    private final Staff loggedInStaff = UserSession.getInstance().getLoggedInStaff();

    private final DoctorRepo doctorRepo = new DoctorRepo();
    private final PatientRepo patientRepo = new PatientRepo();
    private final AppointmentRepo appointmentRepo = new AppointmentRepo();

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> colId;
    @FXML
    private TableColumn<Appointment, String> colDate;
    @FXML
    private TableColumn<Appointment, String> colDoctor;
    @FXML
    private TableColumn<Appointment, String> colPatient;
    @FXML
    private ComboBox<String> sortComboBox;

    private List<Doctor> doctors = List.of();
    private List<Patient> patients = List.of();
    private List<Appointment> appointments = List.of();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().id()).asObject());
        colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().dateTime().toString()));
        colDoctor.setCellValueFactory(c -> {
            Appointment a = c.getValue();
            String name = doctors.stream().filter(d -> Objects.equals(d.getId(), a.doctorId())).map(d -> d.getFirstName() + " " + d.getLastName()).findFirst().orElse("Unknown");
            return new SimpleStringProperty(name);
        });

        colPatient.setCellValueFactory(c -> {
            Appointment a = c.getValue();
            String name = patients.stream().filter(p -> Objects.equals(p.getId(), a.patientId())).map(p -> p.getFirstName() + " " + p.getLastName()).findFirst().orElse("Unknown");
            return new SimpleStringProperty(name);
        });

        Thread.startVirtualThread(() -> {
            try {
                loadData();
            } catch (SQLException e) {
                log.error("Load error", e);
                Platform.runLater(() -> AlertBox.show("SQL Error", e.getMessage()));
            }
        });
    }

    private void loadData() throws SQLException {

        doctors = doctorRepo.getAll().stream().filter(d -> Objects.equals(d.getHospital().getId(), loggedInStaff.getHospital().getId())).toList();
        patients = patientRepo.getAll();
        Set<Integer> doctorIds = doctors.stream().map(Doctor::getId).collect(Collectors.toSet());
        appointments = appointmentRepo.getAll().stream().filter(a -> doctorIds.contains(a.doctorId())).toList();

        Platform.runLater(() -> {
            appointmentsTable.setItems(FXCollections.observableArrayList(appointments));
        });
        log.info("Receptionist data loaded");
    }

    @FXML
    private void handleAddAppointment() {

        WindowManager.<AppointmentDialogController>showModal("/com/alex/sustavzaupravljanjebolnice/appointment-dialog.fxml", "Add Appointment", c -> {
            c.setData(doctors, patients);
            c.setNewAppointmentContext();
        }, c -> {
            if (c.isSaved()) reload();
        });
    }

    @FXML
    private void handleEditAppointment() {

        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AlertBox.show("Warning", "No selection");
            return;
        }

        WindowManager.<AppointmentDialogController>showModal("/com/alex/sustavzaupravljanjebolnice/appointment-dialog.fxml", "Edit Appointment", c -> {
            c.setData(doctors, patients);
            c.setAppointment(selected);
        }, c -> {
            if (c.isSaved()) reload();
        });
    }

    @FXML
    private void handleDeleteAppointment() {

        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AlertBox.show("Warning", "Select something");
            return;
        }

        boolean confirmed = ConfirmationBox.show("Are you sure?", "This cannot be undone");

        if (!confirmed) return;

        Thread.startVirtualThread(() -> {
            try {
                appointmentRepo.deleteById((long) selected.id());

                log.info("Deleted appointment {}", selected.id());

                Platform.runLater(() -> {
                    reload();
                    InfoBox.show("Appointment deleted");
                });

            } catch (SQLException e) {

                log.error("Delete failed", e);

                Platform.runLater(() -> AlertBox.show("Error", e.getMessage()));
            }
        });
    }

    private void reload() {
        Thread.startVirtualThread(() -> {
            try {
                loadData();
            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("SQL Error", e.getMessage()));
            }
        });
    }
}