package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.time.LocalDate;
import java.util.List;

public class PatientBuilder {
    private String firstName;
    private String lastName;
    private String oib;
    private LocalDate birthDate;
    private PatientStatus status;
    private String mbo;
    private Hospital hospital;
    private List<Appointment> appointments;
    private List<Perscription> prescriptions;
    private Doctor assignedDoctor;

    public PatientBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PatientBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PatientBuilder setOib(String oib) {
        this.oib = oib;
        return this;
    }

    public PatientBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PatientBuilder setStatus(PatientStatus status) {
        this.status = status;
        return this;
    }

    public PatientBuilder setMbo(String mbo) {
        this.mbo = mbo;
        return this;
    }

    public PatientBuilder setHospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public PatientBuilder setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public PatientBuilder setPrescriptions(List<Perscription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    public PatientBuilder setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
        return this;
    }

    public Patient createPatient() {
        return new Patient(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOib() {
        return oib;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public PatientStatus getStatus() {
        return status;
    }

    public String getMbo() {
        return mbo;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Perscription> getPrescriptions() {
        return prescriptions;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }
}