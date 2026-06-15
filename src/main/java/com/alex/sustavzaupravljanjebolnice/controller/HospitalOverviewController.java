package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.Nurse;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.repository.DepartmentRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.HospitalRepo;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

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

    private List<Department> allDepartments;
    private List<Doctor> allDoctors;
    private List<Nurse> allNurses;

    @FXML
    public void initialize() throws SQLException {
        List<Hospital> hospitals = hospitalRepo.getAll();
        allDepartments = departmentRepo.getAll();
        allDoctors = doctorRepo.getAll();
        allNurses = nurseRepo.getAll();

        hospitalNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));

        departmentIdColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getId())));
        departmentNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));

        doctorNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));
        doctorEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));

        nurseNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));
        nurseEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));

        hospitalsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) displayHospital(newVal);
        });

        setHospitals(hospitals);
    }

    public void setHospitals(List<Hospital> hospitals) {
        hospitalsTable.setItems(FXCollections.observableArrayList(hospitals));
        if (!hospitals.isEmpty()) {
            hospitalsTable.getSelectionModel().selectFirst();
        }
    }

    private void displayHospital(Hospital hospital) {
        lblHospitalName.setText(hospital.getName());
        lblHospitalAddress.setText(hospital.getAddress() != null ? hospital.getAddress() : "N/A");
        lblHospitalPhone.setText(hospital.getPhoneNumber() != null ? hospital.getPhoneNumber() : "N/A");

        long targetHospitalId = hospital.getId().longValue();

        List<Department> hospitalDepartments = allDepartments.stream().filter(d -> d.getHospital().getId() != null && d.getHospital().getId().longValue() == targetHospitalId).toList();
        departmentsTable.setItems(FXCollections.observableArrayList(hospitalDepartments));

        List<Doctor> hospitalDoctors = allDoctors.stream().filter(doc -> doc.getHospital().getId() != null && doc.getHospital().getId().longValue() == targetHospitalId).toList();
        doctorsTable.setItems(FXCollections.observableArrayList(hospitalDoctors));

        List<Nurse> hospitalNurses = allNurses.stream().filter(nurse -> nurse.getHospital().getId() != null && nurse.getHospital().getId().longValue() == targetHospitalId).toList();
        nursesTable.setItems(FXCollections.observableArrayList(hospitalNurses));
    }
}