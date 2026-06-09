package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.entity.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.Staff;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface {
    void addAppointment(Appointment appointment);

    Optional<List<Appointment>> getAppointments();

    Optional<Appointment> getAppointment(String appointmentId);

    void updateAppointment(String appointmentId, Appointment appointment);

    void deleteAppointment(String appointmentId);

    void addPatient(Patient patient);

    Optional<List<Patient>> getPatients();

    Optional<Patient> getPatient(String patientId);

    void updatePatient(String patientId, Patient patient);

    void deletePatient(String patientId);

    void addStaff(Staff staff);

    Optional<List<Staff>> getStaff();

    Optional<Staff> getStaff(String staffId);

    void updateStaff(String staffId, Staff staff);

    void deleteStaff(String staffId);
}
