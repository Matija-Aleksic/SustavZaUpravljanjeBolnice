package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Patient builder.
 */
public class PatientBuilder {
    private Integer id;
    private String firstName;
    private String lastName;
    private String oib;
    private LocalDate birthDate;
    private PatientStatus status;
    private String mbo;
    private Hospital hospital;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private Doctor assignedDoctor;

    /**
     * Create patient patient.
     *
     * @return the patient
     */
    public Patient createPatient() {
        return new Patient(this);
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     * @return the first name
     */
    public PatientBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     * @return the last name
     */
    public PatientBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Gets oib.
     *
     * @return the oib
     */
    public String getOib() {
        return oib;
    }

    /**
     * Sets oib.
     *
     * @param oib the oib
     * @return the oib
     */
    public PatientBuilder setOib(String oib) {
        this.oib = oib;
        return this;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     * @return the birth date
     */
    public PatientBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public PatientStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     * @return the status
     */
    public PatientBuilder setStatus(PatientStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Gets mbo.
     *
     * @return the mbo
     */
    public String getMbo() {
        return mbo;
    }

    /**
     * Sets mbo.
     *
     * @param mbo the mbo
     * @return the mbo
     */
    public PatientBuilder setMbo(String mbo) {
        this.mbo = mbo;
        return this;
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
     * @return the hospital
     */
    public PatientBuilder setHospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets appointments.
     *
     * @param appointments the appointments
     * @return the appointments
     */
    public PatientBuilder setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    /**
     * Gets prescriptions.
     *
     * @return the prescriptions
     */
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Sets prescriptions.
     *
     * @param prescriptions the prescriptions
     * @return the prescriptions
     */
    public PatientBuilder setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    /**
     * Gets assigned doctor.
     *
     * @return the assigned doctor
     */
    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    /**
     * Sets assigned doctor.
     *
     * @param assignedDoctor the assigned doctor
     * @return the assigned doctor
     */
    public PatientBuilder setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
        return this;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}