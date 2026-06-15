package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Nurse;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.Prescription;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PrescriptionRepo;
import com.alex.sustavzaupravljanjebolnice.repository.WardRepo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Nurse view controller.
 */
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
    private TableColumn<Patient, String> patientNameColumn;
    @FXML
    private TableColumn<Patient, String> patientOibColumn;
    @FXML
    private TableColumn<Patient, String> patientWardColumn;

    @FXML
    private TableView<Prescription> prescriptionsTable;
    @FXML
    private TableColumn<Prescription, String> prescriptionNameColumn;
    @FXML
    private TableColumn<Prescription, String> prescriptionPatientColumn;
    @FXML
    private TableColumn<Prescription, String> prescriptionDurationColumn;

    @FXML
    private Label nameSurname;
    @FXML
    private Label oib;
    @FXML
    private Label email;
    @FXML
    private Label salary;

    private List<Prescription> allPrescriptions;
    private List<Patient> allPatients;
    private List<Ward> allWards;

    /**
     * Initialize.
     *
     * @throws SQLException the sql exception
     */
    @FXML
    public void initialize() throws SQLException {
        // Load data once to keep memory lookups performant
        allPrescriptions = prescriptionRepo.getAll();
        allPatients = patientRepo.getAll();
        allWards = wardRepo.getAll();
        List<Nurse> nurses = nurseRepo.getAll();

        nurseColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));

        patientNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));

        patientOibColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getOib()));

        patientWardColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(resolveWardName(data.getValue())));

        prescriptionNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));

        prescriptionPatientColumn.setCellValueFactory(data -> {
            Patient p = findPatient(data.getValue().getPatientId());
            return new ReadOnlyStringWrapper(p != null ? p.getFirstName() + " " + p.getLastName() : "Unknown");
        });

        prescriptionDurationColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStartDate() + " - " + data.getValue().getEndDate()));

        nursesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) displayNurse(newV);
        });

        setNurses(nurses);
    }

    /**
     * Sets nurses.
     *
     * @param nurses the nurses
     */
    public void setNurses(List<Nurse> nurses) {
        nursesTable.setItems(FXCollections.observableArrayList(nurses));
        if (!nurses.isEmpty()) {
            nursesTable.getSelectionModel().selectFirst();
        }
    }

    private void displayNurse(Nurse nurse) {
        nameSurname.setText(nurse.getFirstName() + " " + nurse.getLastName());
        oib.setText(nurse.getOib());
        email.setText(nurse.getEmail());
        salary.setText(String.format("%.2f €", nurse.getSalary()));


        List<Patient> patients = new ArrayList<>();
        for (Ward ward : nurse.getWards()) {
            patients.addAll(ward.getPatients());
        }

        patientsTable.setItems(FXCollections.observableArrayList(patients));

        Set<Long> patientIds = patients.stream().map(p -> p.getId().longValue()).collect(Collectors.toSet());

        List<Prescription> prescriptions = allPrescriptions.stream().filter(p -> p.getPatientId() != null && patientIds.contains(p.getPatientId().longValue())).toList();

        prescriptionsTable.setItems(FXCollections.observableArrayList(prescriptions));
    }

    private String resolveWardName(Patient patient) {
        if (patient == null || patient.getAssignedWard() == null || patient.getAssignedWard().getId() == null) {
            return "No Ward Assigned";
        }

        long wardId = patient.getAssignedWard().getId();
        return allWards.stream().filter(w -> w.getId() != null && w.getId() == wardId).map(Ward::getName).findFirst().orElse("Unknown Ward");
    }

    private Patient findPatient(Number id) {
        if (id == null) return null;
        long targetId = id.longValue();
        return allPatients.stream().filter(p -> p.getId() != null && p.getId().longValue() == targetId).findFirst().orElse(null);
    }
}