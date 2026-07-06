package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Nurse;
import com.alex.sustavzaupravljanjebolnice.repository.DepartmentRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.HospitalRepo;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Hospital overview controller.
 */
public class HospitalOverviewController {

    private final HospitalRepo hospitalRepo = new HospitalRepo();
    private final DepartmentRepo departmentRepo = new DepartmentRepo();
    private final DoctorRepo doctorRepo = new DoctorRepo();
    private final NurseRepo nurseRepo = new NurseRepo();

    @FXML
    private TableView<Hospital> hospitalsTable;
    @FXML
    private TableColumn<Hospital, String> hospitalNameColumn;

    @FXML
    private Label lblHospitalName;
    @FXML
    private Label lblHospitalAddress;
    @FXML
    private Label lblHospitalPhone;

    @FXML
    private TableView<Department> departmentsTable;
    @FXML
    private TableColumn<Department, String> departmentIdColumn;
    @FXML
    private TableColumn<Department, String> departmentNameColumn;

    @FXML
    private TableView<Doctor> doctorsTable;
    @FXML
    private TableColumn<Doctor, String> doctorNameColumn;
    @FXML
    private TableColumn<Doctor, String> doctorEmailColumn;

    @FXML
    private TableView<Nurse> nursesTable;
    @FXML
    private TableColumn<Nurse, String> nurseNameColumn;
    @FXML
    private TableColumn<Nurse, String> nurseEmailColumn;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        hospitalNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        departmentIdColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getId())));
        departmentNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        doctorNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));
        doctorEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
        nurseNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));
        nurseEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));

        Thread.startVirtualThread(() -> {
            try {
                load();
            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("Sql Fail", e.getMessage()));
            }
        });
    }

    private void load() throws SQLException {
        List<Hospital> hospitals = hospitalRepo.getAll();
        List<Department> departments = List.copyOf(departmentRepo.getAll());
        List<Doctor> doctors = List.copyOf(doctorRepo.getAll());
        List<Nurse> nurses = List.copyOf(nurseRepo.getAll());

        Platform.runLater(() -> {
            hospitalsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    displayHospital(newVal, departments, doctors, nurses);
                }
            });

            setHospitals(hospitals);
            if (!hospitals.isEmpty()) {
                displayHospital(hospitalsTable.getSelectionModel().getSelectedItem(), departments, doctors, nurses);
            }
        });
    }

    /**
     * Sets hospitals.
     *
     * @param hospitals the hospitals
     */
    public void setHospitals(List<Hospital> hospitals) {
        hospitalsTable.setItems(FXCollections.observableArrayList(hospitals));
        if (!hospitals.isEmpty()) {
            hospitalsTable.getSelectionModel().selectFirst();
        }
    }

    private void displayHospital(Hospital hospital, List<Department> departments, List<Doctor> doctors, List<Nurse> nurses) {
        lblHospitalName.setText(hospital.getName());
        lblHospitalAddress.setText(hospital.getAddress());
        lblHospitalPhone.setText(hospital.getPhoneNumber());
        long targetHospitalId = hospital.getId();

        List<Department> hospitalDepartments = departments.stream().filter(d -> d.getHospital().getId() == targetHospitalId).toList();
        departmentsTable.setItems(FXCollections.observableArrayList(hospitalDepartments));

        List<Doctor> hospitalDoctors = doctors.stream().filter(doc -> doc.getHospital().getId() == targetHospitalId).toList();
        doctorsTable.setItems(FXCollections.observableArrayList(hospitalDoctors));

        List<Nurse> hospitalNurses = nurses.stream().filter(nurse -> nurse.getHospital() != null && nurse.getHospital().getId() != null && nurse.getHospital().getId() == targetHospitalId).toList();
        nursesTable.setItems(FXCollections.observableArrayList(hospitalNurses));
    }
}