package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Prescription;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.*;
import com.alex.sustavzaupravljanjebolnice.util.HospitalCrudHelper;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The type Patient view controller.
 */
public class PatientViewController {
    private final Staff loggedInStaff = UserSession.getInstance().getLoggedInStaff();
    private final PatientRepo patientRepo = new PatientRepo();
    private final DoctorRepo doctorRepo = new DoctorRepo();
    private final AppointmentRepo appointmentRepo = new AppointmentRepo();
    private final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();
    private final HospitalRepo hospitalRepo = new HospitalRepo();
    private final WardRepo wardRepo = new WardRepo();

    @FXML
    Label detailMbo;
    @FXML
    Label detailBirthDate;
    @FXML
    Label detailStatus;
    @FXML
    Label detailDoctor;
    @FXML
    Label detailWard;
    @FXML
    Label detailHospital;
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> nameColumn;
    @FXML
    private TableColumn<Patient, String> oibColumn;
    @FXML
    private TableColumn<Patient, String> mboColumn;
    @FXML
    private TableColumn<Patient, String> statusColumn;
    @FXML
    private Label detailName;
    @FXML
    private Label detailOib;
    @FXML
    private ListView<String> appointmentsListView;
    @FXML
    private ListView<String> prescriptionsListView;
    @FXML
    private Button addPatientBtn;
    @FXML
    private Button editPatientBtn;
    @FXML
    private Button deletePatientBtn;
    @FXML
    private HBox adminActionBar;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName() + " " + c.getValue().getLastName()));
        oibColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOib()));
        mboColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMbo()));
        statusColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus() != null ? c.getValue().getStatus().name() : "N/A"));

        boolean isAdmin = loggedInStaff != null && loggedInStaff.getRole() != null && "ADMIN".equalsIgnoreCase(loggedInStaff.getRole().toString());

        if (!isAdmin) {
            addPatientBtn.setDisable(true);
            editPatientBtn.setDisable(true);
            deletePatientBtn.setDisable(true);
            adminActionBar.setVisible(false);
            adminActionBar.setManaged(false);
        }

        patientsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) showPatientDetails(newVal);
            else clearPatientDetails();
        });

        reload();
    }

    private void showPatientDetails(Patient patient) {
        detailName.setText(patient.getFirstName() + " " + patient.getLastName());
        detailOib.setText("OIB: " + patient.getOib());
        detailMbo.setText("MBO: " + patient.getMbo());
        detailBirthDate.setText("Birth Date: " + (patient.getBirthDate() != null ? patient.getBirthDate().toString() : "N/A"));
        detailStatus.setText("Status: " + (patient.getStatus() != null ? patient.getStatus().name() : "N/A"));
        detailHospital.setText("Hospital: " + (patient.getHospital() != null && patient.getHospital().getName() != null ? patient.getHospital().getName() : "N/A"));
        detailDoctor.setText("Assigned Doctor: " + (patient.getAssignedDoctor() != null && patient.getAssignedDoctor().getLastName() != null ? "Dr. " + patient.getAssignedDoctor().getFirstName() + " " + patient.getAssignedDoctor().getLastName() : "Unassigned"));
        detailWard.setText("Ward Unit: " + (patient.getAssignedWard() != null && patient.getAssignedWard().getName() != null ? patient.getAssignedWard().getName() : "Outpatient"));

        loadAppointmentsList(patient);
        loadPrescriptionsList(patient);
    }


    private void loadAppointmentsList(Patient patient) {
        appointmentsListView.getItems().clear();
        if (patient.getAppointments() != null && !patient.getAppointments().isEmpty()) {
            for (Appointment appt : patient.getAppointments()) {
                appointmentsListView.getItems().add(appt.dateTime().toString() + " - Clinic Session");
            }
        } else {
            appointmentsListView.getItems().add("No upcoming scheduled clinical appointments.");
        }
    }

    private void loadPrescriptionsList(Patient patient) {
        prescriptionsListView.getItems().clear();
        if (patient.getPrescription() != null && !patient.getPrescription().isEmpty()) {
            for (Prescription rx : patient.getPrescription()) {
                prescriptionsListView.getItems().add(rx.getName() + " [" + rx.getStartDate() + " to " + rx.getEndDate() + "]");
            }
        } else {
            prescriptionsListView.getItems().add("No active medication or prescription cycles.");
        }
    }


    private void clearPatientDetails() {
        Arrays.asList(detailName, detailOib, detailMbo, detailBirthDate, detailStatus, detailDoctor, detailWard, detailHospital).forEach(label -> label.setText(""));
        appointmentsListView.getItems().clear();
        prescriptionsListView.getItems().clear();
    }

    private void reload() {
        Thread.startVirtualThread(() -> {
            try {
                List<Patient> basePatients = patientRepo.getAll();
                List<Doctor> allDoctors = doctorRepo.getAll();
                List<Hospital> allHospitals = hospitalRepo.getAll();
                List<Ward> allWards = wardRepo.getAll();
                List<Appointment> allAppointments = appointmentRepo.getAll();
                List<Prescription> allPrescriptions = prescriptionRepo.getAll();

                for (Patient patient : basePatients) {
                    linkDataToPatient(patient, allDoctors, allHospitals, allWards, allAppointments, allPrescriptions);
                }

                List<Patient> localizedPatients = basePatients.stream().filter(p -> p.getHospital() != null && loggedInStaff != null && loggedInStaff.getHospital() != null && Objects.equals(p.getHospital().getId(), loggedInStaff.getHospital().getId())).toList();

                Platform.runLater(() -> {
                    patientsTable.setItems(FXCollections.observableArrayList(localizedPatients));
                    Patient currentSelection = patientsTable.getSelectionModel().getSelectedItem();
                    if (currentSelection != null) {
                        showPatientDetails(currentSelection);
                    } else {
                        clearPatientDetails();
                    }
                });

            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("Database Loading Failure", "Could not synchronize operational matrix fields: " + e.getMessage()));
            }
        });
    }


    private void linkDataToPatient(Patient patient, List<Doctor> allDoctors, List<Hospital> allHospitals, List<Ward> allWards, List<Appointment> allAppointments, List<Prescription> allPrescriptions) {
        if (patient.getHospital() != null && patient.getHospital().getId() != null) {
            allHospitals.stream().filter(h -> Objects.equals(h.getId(), patient.getHospital().getId())).findFirst().ifPresent(patient::setHospital);
        }

        if (patient.getAssignedDoctor() != null && patient.getAssignedDoctor().getId() != null) {
            allDoctors.stream().filter(d -> Objects.equals(d.getId(), patient.getAssignedDoctor().getId())).findFirst().ifPresent(patient::setAssignedDoctor);
        }

        if (patient.getAssignedWard() != null && patient.getAssignedWard().getId() != null) {
            allWards.stream().filter(w -> Objects.equals(w.getId(), patient.getAssignedWard().getId())).findFirst().ifPresent(patient::setAssignedWard);
        }

        List<Appointment> patientAppts = allAppointments.stream().filter(a -> a.patientId() != null && a.patientId().longValue() == patient.getId().longValue()).toList();
        patient.setAppointments(new ArrayList<>(patientAppts));

        List<Prescription> patientScripts = allPrescriptions.stream().filter(p -> p.getPatientId() != null && p.getPatientId().longValue() == patient.getId().longValue()).toList();
        patient.setPrescriptions(new ArrayList<>(patientScripts));
    }


    @FXML
    private void handleAddPatient() {
        HospitalCrudHelper.addPatient(this::reload);
    }

    @FXML
    private void handleEditPatient() {
        Patient selected = patientsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.show("Selection Missing", "Please select a patient row to modify data.");
            return;
        }
        HospitalCrudHelper.editPatient(selected, this::reload);
    }

    @FXML
    private void handleDeletePatient() {
        Patient selected = patientsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.show("Selection Missing", "Please select a patient row to complete system removal.");
            return;
        }
        HospitalCrudHelper.deletePatient(selected, this::reload);
    }
}