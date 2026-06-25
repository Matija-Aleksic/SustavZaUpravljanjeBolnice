package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Prescription;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Nurse;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PrescriptionRepo;
import com.alex.sustavzaupravljanjebolnice.repository.WardRepo;
import com.alex.sustavzaupravljanjebolnice.util.HospitalCrudHelper; // Our extracted logic
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class NurseViewController {
    private final NurseRepo nurseRepo = new NurseRepo();
    private final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();
    private final PatientRepo patientRepo = new PatientRepo();
    private final WardRepo wardRepo = new WardRepo();

    @FXML
    private TableView<Nurse> nursesTable;
    @FXML
    private TableColumn<Nurse, String> nurseColumn;
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> patientNameColumn, patientOibColumn, patientWardColumn;
    @FXML
    private TableView<Prescription> prescriptionsTable;
    @FXML
    private TableColumn<Prescription, String> prescriptionNameColumn, prescriptionPatientColumn, prescriptionDurationColumn;
    @FXML
    private Label nameSurname, oib, email, salary;

    private List<Prescription> allPrescriptions = List.of();
    private List<Patient> allPatients = List.of();
    private List<Ward> allWards = List.of();

    @FXML
    public void initialize() {
        nurseColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getFirstName() + " " + d.getValue().getLastName()));
        patientNameColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getFirstName() + " " + d.getValue().getLastName()));
        patientOibColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getOib()));
        patientWardColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(resolveWardName(d.getValue())));

        prescriptionNameColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getName()));
        prescriptionPatientColumn.setCellValueFactory(d -> {
            Patient p = findPatient(d.getValue().getPatientId());
            return new ReadOnlyStringWrapper(p != null ? p.getFirstName() + " " + p.getLastName() : "Unknown");
        });
        prescriptionDurationColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getStartDate() + " - " + d.getValue().getEndDate()));

        nursesTable.getSelectionModel().selectedItemProperty().addListener((o, oldV, newV) -> {
            if (newV != null) displayNurse(newV);
        });

        reload();
    }

    private void displayNurse(Nurse nurse) {
        nameSurname.setText(nurse.getFirstName() + " " + nurse.getLastName());
        oib.setText(nurse.getOib());
        email.setText(nurse.getEmail());
        salary.setText(String.format("%.2f €", nurse.getSalary()));

        List<Patient> patients = Objects.requireNonNullElse(nurse.getWards(), Collections.<Ward>emptyList()).stream().flatMap(w -> w.getPatients().stream()).toList();
        patientsTable.setItems(FXCollections.observableArrayList(patients));

        Set<Long> patientIds = patients.stream().map(p -> p.getId().longValue()).collect(Collectors.toSet());
        List<Prescription> prescriptions = allPrescriptions.stream().filter(p -> p.getPatientId() != null && patientIds.contains(p.getPatientId().longValue())).toList();
        prescriptionsTable.setItems(FXCollections.observableArrayList(prescriptions));
    }

    public void reload() {
        Thread.startVirtualThread(() -> {
            try {
                List<Prescription> prs = prescriptionRepo.getAll();
                List<Patient> pts = patientRepo.getAll();
                List<Ward> wrds = wardRepo.getAll();
                List<Nurse> nrs = nurseRepo.getAll();
                Platform.runLater(() -> {
                    this.allPrescriptions = prs;
                    this.allPatients = pts;
                    this.allWards = wrds;
                    int idx = nursesTable.getSelectionModel().getSelectedIndex();
                    nursesTable.setItems(FXCollections.observableArrayList(nrs));
                    nursesTable.getSelectionModel().select(idx >= 0 && idx < nrs.size() ? idx : 0);
                });
            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("Sync Error", e.getMessage()));
            }
        });
    }

    private String resolveWardName(Patient p) {
        if (p == null || p.getAssignedWard() == null || p.getAssignedWard().getId() == null) return "No Ward Assigned";
        return allWards.stream().filter(w -> w.getId() != null && w.getId().equals(p.getAssignedWard().getId())).map(Ward::getName).findFirst().orElse("Unknown Ward");
    }

    private Patient findPatient(Number id) {
        return id == null ? null : allPatients.stream().filter(p -> p.getId() != null && p.getId().longValue() == id.longValue()).findFirst().orElse(null);
    }

    // Extracted Routing Handlers
    @FXML
    private void handleAddNurse() {
        HospitalCrudHelper.addNurse(this::reload);
    }

    @FXML
    private void handleEditNurse() {
        HospitalCrudHelper.editNurse(nursesTable.getSelectionModel().getSelectedItem(), this::reload);
    }

    @FXML
    private void handleDeleteNurse() {
        HospitalCrudHelper.deleteNurse(nursesTable.getSelectionModel().getSelectedItem(), this::reload);
    }

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