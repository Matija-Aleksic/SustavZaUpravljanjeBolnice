package com.alex.sustavzaupravljanjebolnice.repository;

import com.alex.sustavzaupravljanjebolnice.entity.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.Staff;

import java.util.List;
import java.util.Optional;

/**
 * The interface Repository interface.
 */
public interface RepositoryInterface {
    /**
     * Add appointment.
     *
     * @param appointment the appointment
     */
    void addAppointment(Appointment appointment);

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    Optional<List<Appointment>> getAppointments();

    /**
     * Gets appointment.
     *
     * @param appointmentId the appointment id
     * @return the appointment
     */
    Optional<Appointment> getAppointment(String appointmentId);

    /**
     * Update appointment.
     *
     * @param appointmentId the appointment id
     * @param appointment   the appointment
     */
    void updateAppointment(String appointmentId, Appointment appointment);

    /**
     * Delete appointment.
     *
     * @param appointmentId the appointment id
     */
    void deleteAppointment(String appointmentId);

    /**
     * Add patient.
     *
     * @param patient the patient
     */
    void addPatient(Patient patient);

    /**
     * Gets patients.
     *
     * @return the patients
     */
    Optional<List<Patient>> getPatients();

    /**
     * Gets patient.
     *
     * @param patientId the patient id
     * @return the patient
     */
    Optional<Patient> getPatient(String patientId);

    /**
     * Update patient.
     *
     * @param patientId the patient id
     * @param patient   the patient
     */
    void updatePatient(String patientId, Patient patient);

    /**
     * Delete patient.
     *
     * @param patientId the patient id
     */
    void deletePatient(String patientId);

    /**
     * Add staff.
     *
     * @param staff the staff
     */
    void addStaff(Staff staff);

    /**
     * Gets staff.
     *
     * @return the staff
     */
    Optional<List<Staff>> getStaff();

    /**
     * Gets staff.
     *
     * @param staffId the staff id
     * @return the staff
     */
    Optional<Staff> getStaff(String staffId);

    /**
     * Update staff.
     *
     * @param staffId the staff id
     * @param staff   the staff
     */
    void updateStaff(String staffId, Staff staff);

    /**
     * Delete staff.
     *
     * @param staffId the staff id
     */
    void deleteStaff(String staffId);
}
