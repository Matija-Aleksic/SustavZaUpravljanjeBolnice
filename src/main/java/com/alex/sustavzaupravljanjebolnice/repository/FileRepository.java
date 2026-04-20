package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.entity.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.Staff;

import java.util.List;
import java.util.Optional;

public class FileRepository implements RepositoryInterface {
    @Override
    public void addAppointment(Appointment appointment) {

    }

    @Override
    public Optional<List<Appointment>> getAppointments() {
        return Optional.empty();
    }

    @Override
    public Optional<Appointment> getAppointment(String appointmentId) {
        return Optional.empty();
    }

    @Override
    public void updateAppointment(String appointmentId, Appointment appointment) {

    }

    @Override
    public void deleteAppointment(String appointmentId) {

    }

    @Override
    public void addPatient(Patient patient) {

    }

    @Override
    public Optional<List<Patient>> getPatients() {
        return Optional.empty();
    }

    @Override
    public Optional<Patient> getPatient(String patientId) {
        return Optional.empty();
    }

    @Override
    public void updatePatient(String patientId, Patient patient) {

    }

    @Override
    public void deletePatient(String patientId) {

    }

    @Override
    public void addStaff(Staff staff) {

    }

    @Override
    public Optional<List<Staff>> getStaff() {
        return Optional.empty();
    }

    @Override
    public Optional<Staff> getStaff(String staffId) {
        return Optional.empty();
    }

    @Override
    public void updateStaff(String staffId, Staff staff) {

    }

    @Override
    public void deleteStaff(String staffId) {

    }
}
