package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.entity.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.List;

public class DoctorViewController {

    @FXML
    private TableView<Doctor> doctorsTable;

    @FXML
    private TableColumn<Doctor, String> doctorColumn;

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

    @FXML
    public void initialize() throws SQLException {

        try {
            setDoctors(DatabaseManager.getDoctors());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Doctor view controller");


        doctorColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(
                        data.getValue().getFirstName() + " " + data.getValue().getLastName()
                ));

        patientNameColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(
                        data.getValue().getFirstName() + " " + data.getValue().getLastName()
                ));

        patientOibColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(
                        data.getValue().getOib()
                ));

        appointmentDateColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(
                        data.getValue().toString()
                ));
        //todo FIX
        appointmentPatientColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(
                        data.getValue().patient().getFirstName()
                                + " "
                                + data.getValue().patient().getLastName()
                ));

        doctorsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldDoctor, newDoctor) -> {
                    if (newDoctor != null) {
                        displayDoctor(newDoctor);
                    }
                });
    }

    public void setDoctors(List<Doctor> doctors) {
        doctorsTable.setItems(FXCollections.observableArrayList(doctors));

        if (!doctors.isEmpty()) {
            doctorsTable.getSelectionModel().selectFirst();
        }
    }

    private void displayDoctor(Doctor doctor) {

        nameSurname.setText(
                doctor.getFirstName() + " " + doctor.getLastName()
        );

        oib.setText(doctor.getOib());

//        role.setText(
//                doctor.getRole() != null
//                        ? doctor.getRole().toString()
//                        : ""
//        );

        email.setText(doctor.getEmail());

        salary.setText(String.valueOf(doctor.getSalary()));

        hospital.setText(
                doctor.getHospital() != null
                        ? doctor.getHospital().getName()
                        : ""
        );

        phoneNumber.setText(doctor.getPhoneNumber());

        address.setText(doctor.getAddress());

        patientsTable.setItems(
                FXCollections.observableArrayList(
                        doctor.getAssignedPatients()
                )
        );

        appointmentsTable.setItems(
                FXCollections.observableArrayList(
                        doctor.getAppointments()
                )
        );
    }
}