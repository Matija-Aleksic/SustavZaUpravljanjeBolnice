package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.repository.AppointmentRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Doctor view controller.
 */
public class DoctorViewController {

    private final PatientRepo patientRepo = new PatientRepo();
    private final AppointmentRepo appointmentRepo = new AppointmentRepo();
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
    private Label oib;
    private Label role;
    private Label email;
    private Label salary;
    private Label hospital;
    private Label phoneNumber;
    private Label address;
    private List<Patient> allPatients;
    private List<Appointment> allAppointments;

    /**
     * Initialize.
     *
     * @throws SQLException the sql exception
     */
    @FXML
    public void initialize() throws SQLException {

        allPatients = patientRepo.getAll();
        allAppointments = appointmentRepo.getAll();

        Map<Integer, Patient> patientMap = allPatients.stream().collect(Collectors.toMap(Patient::getId, p -> p));


        try {
            DoctorRepo doctorRepo = new DoctorRepo();
            setDoctors(doctorRepo.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        doctorColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));

        patientNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName() + " " + data.getValue().getLastName()));

        patientOibColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getOib()));

        appointmentDateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().toString()));

        appointmentPatientColumn.setCellValueFactory(data -> {
            Appointment appt = data.getValue();
            Patient p = patientMap.get(appt.patientId());

            String name = (p != null) ? (p.getFirstName() + " " + p.getLastName()) : "Unknown";
            return new ReadOnlyStringWrapper(name);
        });

        doctorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldDoctor, newDoctor) -> {
            if (newDoctor != null) {
                displayDoctor(newDoctor);
            }
        });
    }

    /**
     * Sets doctors.
     *
     * @param doctors the doctors
     */
    public void setDoctors(List<Doctor> doctors) {
        doctorsTable.setItems(FXCollections.observableArrayList(doctors));

        if (!doctors.isEmpty()) {
            doctorsTable.getSelectionModel().selectFirst();
        }
    }

    private void displayDoctor(Doctor doctor) {
        nameSurname.setText(doctor.getFirstName() + " " + doctor.getLastName());
        oib.setText(doctor.getOib());
        role.setText(doctor.getRole() != null ? doctor.getRole().toString() : "");
        email.setText(doctor.getEmail());
        salary.setText(String.valueOf(doctor.getSalary()));
        hospital.setText(doctor.getHospital() != null ? doctor.getHospital().getName() : "");
        phoneNumber.setText(doctor.getPhoneNumber());
        address.setText(doctor.getAddress());

        List<Patient> filteredPatients = allPatients.stream().filter(patient -> patient.getAssignedDoctor() != null && Objects.equals(patient.getAssignedDoctor().getId(), doctor.getId())).toList();
        patientsTable.setItems(FXCollections.observableArrayList(filteredPatients));

        List<Appointment> filteredAppointments = allAppointments.stream().filter(appointment -> Objects.equals(appointment.doctorId(), doctor.getId())).toList();
        appointmentsTable.setItems(FXCollections.observableArrayList(filteredAppointments));
    }
}