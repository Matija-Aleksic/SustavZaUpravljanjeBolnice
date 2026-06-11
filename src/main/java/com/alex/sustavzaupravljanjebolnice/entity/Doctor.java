package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Conactable;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Schedulable;

import java.util.List;

/**
 * The type Doctor.
 */
public non-sealed class Doctor extends Staff implements Schedulable, Conactable {
    private Hospital hospital;
    private List<Patient> assignedPatients;
    private List<Appointment> appointments;
    private String phoneNumber;
    private String address;

    /**
     * Instantiates a new Doctor.
     *
     * @param doctor the doctor
     */
    public Doctor(DoctorBuilder doctor) {
        super(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getOib(), doctor.getBirthDate(), doctor.getRole(), doctor.getEmail(), doctor.getSalary());
        this.hospital = doctor.getHospital();
        this.assignedPatients = doctor.getAssignedPatients();
        this.appointments = doctor.getAppointments();
        this.phoneNumber = doctor.getPhoneNumber();
        this.address = doctor.getAddress();
    }

    /**
     * Gets hospital.
     *
     * @return the hospital
     */
    public Hospital getHospital() {
        return hospital;
    }

    /**
     * Sets hospital.
     *
     * @param hospital the hospital
     */
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    /**
     * Gets assigned patients.
     *
     * @return the assigned patients
     */
    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    /**
     * Sets assigned patients.
     *
     * @param assignedPatients the assigned patients
     */
    public void setAssignedPatients(List<Patient> assignedPatients) {
        this.assignedPatients = assignedPatients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public void setAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    @Override
    public Boolean isAvailable(Appointment appointment) {
        return !this.appointments.contains(appointment);
    }

    /**
     * Sets appointments.
     *
     * @param appointments the appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }


    /**
     * Add patient.
     *
     * @param patient the patient
     */
    public void addPatient(Patient patient) {
        if (!this.assignedPatients.contains(patient)) {
            this.assignedPatients.add(patient);
            if (patient.getAssignedDoctor() != this) {
                patient.setAssignedDoctor(this);
            }
        }
    }

    /**
     * Remove patient.
     *
     * @param patient the patient
     */
    public void removePatient(Patient patient) {
        if (this.assignedPatients.contains(patient)) {
            this.assignedPatients.remove(patient);
            if (patient.getAssignedDoctor() == this) {
                patient.setAssignedDoctor(null);
            }
        }
    }

    /**
     * Add appointment.
     *
     * @param appointment the appointment
     */
    public void addAppointment(Appointment appointment) {
        if (!this.appointments.contains(appointment)) {
            this.appointments.add(appointment);
            if (!appointment.patient().getAppointments().contains(appointment)) {
                appointment.patient().setAppointment(appointment);
            }
        }
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

}
