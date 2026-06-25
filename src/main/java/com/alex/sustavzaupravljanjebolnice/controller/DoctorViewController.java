package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Prescription;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.AppointmentRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PrescriptionRepo;
import com.alex.sustavzaupravljanjebolnice.util.HospitalCrudHelper;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorViewController {
    private final Staff loggedInStaff = UserSession.getInstance().getLoggedInStaff();
    private final DoctorRepo doctorRepo = new DoctorRepo();
    private final PatientRepo patientRepo = new PatientRepo();
    private final AppointmentRepo appointmentRepo = new AppointmentRepo();
    private final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();

    @FXML
    private TableView<Doctor> doctorsTable;
    @FXML
    private TableColumn<Doctor, String> doctorColumn;
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> patientNameColumn, patientOibColumn;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, String> appointmentDateColumn, appointmentPatientColumn;

    @FXML
    private TableView<Prescription> prescriptionsTable;
    @FXML
    private TableColumn<Prescription, String> prescriptionNameColumn, prescriptionPatientColumn, prescriptionDurationColumn;

    @FXML
    private Label nameSurname, oib, role, email, salary, hospital, phoneNumber, address;
    @FXML
    private ImageView picture;

    @FXML
    private HBox doctorCrudContainer;
    @FXML
    private Button addDoctorBtn;
    @FXML
    private Button editDoctorBtn;
    @FXML
    private Button deleteDoctorBtn;

    private List<Patient> allPatients = List.of();
    private List<Appointment> allAppointments = List.of();
    private List<Prescription> allPrescriptions = List.of();

    @FXML
    public void initialize() {
        configureRoleBasedAccess();

        doctorColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName() + " " + c.getValue().getLastName()));
        patientNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName() + " " + c.getValue().getLastName()));
        patientOibColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOib()));
        appointmentDateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().dateTime().toString()));

        appointmentPatientColumn.setCellValueFactory(c -> {
            Patient p = allPatients.stream().filter(pt -> Objects.equals(pt.getId(), c.getValue().patientId())).findFirst().orElse(null);
            return new SimpleStringProperty(p != null ? p.getFirstName() + " " + p.getLastName() : "Unknown");
        });

        prescriptionNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        prescriptionPatientColumn.setCellValueFactory(c -> {
            Patient p = allPatients.stream().filter(pt -> pt.getId() != null && pt.getId().longValue() == c.getValue().getPatientId().longValue()).findFirst().orElse(null);
            return new SimpleStringProperty(p != null ? p.getFirstName() + " " + p.getLastName() : "Unknown");
        });
        prescriptionDurationColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStartDate() + " - " + c.getValue().getEndDate()));

        doctorsTable.getSelectionModel().selectedItemProperty().addListener((o, oldV, newV) -> {
            if (newV != null) showDoctorDetails(newV);
            else clearDoctorDetails();
        });
        reload();
    }


    private void configureRoleBasedAccess() {
        boolean isAdmin = loggedInStaff != null && loggedInStaff.getRole() != null && "ADMIN".equalsIgnoreCase(loggedInStaff.getRole().toString());
        if (doctorCrudContainer != null) {
            doctorCrudContainer.setVisible(isAdmin);
            doctorCrudContainer.setManaged(isAdmin);
        } else {
            Arrays.asList(addDoctorBtn, editDoctorBtn, deleteDoctorBtn).forEach(btn -> {
                if (btn != null) {
                    btn.setVisible(isAdmin);
                    btn.setManaged(isAdmin);
                }
            });
        }
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

        List<Appointment> filteredAppts = allAppointments.stream().filter(a -> Objects.equals(a.doctorId(), doctor.getId())).toList();
        appointmentsTable.setItems(FXCollections.observableArrayList(filteredAppts));

        Set<Integer> assignedPatientIds = filteredAppts.stream().map(Appointment::patientId).collect(Collectors.toSet());
        List<Patient> filteredPatients = allPatients.stream().filter(p -> assignedPatientIds.contains(p.getId())).toList();
        patientsTable.setItems(FXCollections.observableArrayList(filteredPatients));

        Set<Long> patientLongIds = filteredPatients.stream().map(p -> p.getId().longValue()).collect(Collectors.toSet());
        List<Prescription> filteredScripts = allPrescriptions.stream().filter(p -> p.getPatientId() != null && patientLongIds.contains(p.getPatientId().longValue())).toList();
        prescriptionsTable.setItems(FXCollections.observableArrayList(filteredScripts));
    }

    private void clearDoctorDetails() {
        Arrays.asList(nameSurname, oib, role, email, salary, hospital, phoneNumber, address).forEach(l -> l.setText(""));
        appointmentsTable.getItems().clear();
        patientsTable.getItems().clear();
        prescriptionsTable.getItems().clear();
    }

    private void reload() {
        Thread.startVirtualThread(() -> {
            try {
                Long currentHospitalId = (loggedInStaff != null && loggedInStaff.getHospital() != null) ? loggedInStaff.getHospital().getId() : null;
                List<Doctor> docs = doctorRepo.getAll().stream().filter(d -> d.getHospital() != null && Objects.equals(d.getHospital().getId(), currentHospitalId)).toList();
                List<Patient> pts = patientRepo.getAll();
                List<Appointment> appts = appointmentRepo.getAll();
                List<Prescription> scripts = prescriptionRepo.getAll();

                Platform.runLater(() -> {
                    this.allPatients = pts;
                    this.allAppointments = appts;
                    this.allPrescriptions = scripts;
                    doctorsTable.setItems(FXCollections.observableArrayList(docs));
                    Doctor currentSelection = doctorsTable.getSelectionModel().getSelectedItem();
                    if (currentSelection != null) showDoctorDetails(currentSelection);
                    else clearDoctorDetails();
                });
            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("SQL Processing Failure", e.getMessage()));
            }
        });
    }

    // Doctor Actions
    @FXML
    private void handleAddDoctor() {
        HospitalCrudHelper.addDoctor(this::reload);
    }

    @FXML
    private void handleEditDoctor() {
        HospitalCrudHelper.editDoctor(doctorsTable.getSelectionModel().getSelectedItem(), this::reload);
    }

    @FXML
    private void handleDeleteDoctor() {
        HospitalCrudHelper.deleteDoctor(doctorsTable.getSelectionModel().getSelectedItem(), this::reload);
    }

    // Patient Actions
    @FXML
    private void handleAddPatient() {
        HospitalCrudHelper.addPatient(this::reload);
    }

    @FXML
    private void handleEditPatient() {
        HospitalCrudHelper.editPatient(patientsTable.getSelectionModel().getSelectedItem(), this::reload);
    }

    @FXML
    private void handleDeletePatient() {
        HospitalCrudHelper.deletePatient(patientsTable.getSelectionModel().getSelectedItem(), this::reload);
    }

    @FXML
    private void handleAddPrescription() {
        HospitalCrudHelper.addPrescription(this::reload);
    }

    @FXML
    private void handleEditPrescription() {
        HospitalCrudHelper.editPrescription(prescriptionsTable.getSelectionModel().getSelectedItem(), this::reload);
    }

    @FXML
    private void handleDeletePrescription() {
        HospitalCrudHelper.deletePrescription(prescriptionsTable.getSelectionModel().getSelectedItem(), this::reload);
    }
}