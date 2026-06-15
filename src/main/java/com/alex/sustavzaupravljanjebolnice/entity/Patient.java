package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Schedulable;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Treatable;

import java.util.List;

/**
 * The type Patient.
 */
public class Patient extends Person implements Schedulable, Treatable {
    /**
     * The Status.
     */
    protected PatientStatus status;
    /**
     * The Mbo.
     */
    protected String mbo;
    private transient Hospital hospital;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private transient Doctor assignedDoctor;
    private Ward assignedWard;


    /**
     * Instantiates a new Patient.
     *
     * @param builder the builder
     */
    public Patient(PatientBuilder builder) {
        super(builder.getId(), builder.getFirstName(), builder.getLastName(), builder.getOib(), builder.getBirthDate());
        this.status = builder.getStatus();
        this.mbo = builder.getMbo();
        this.hospital = builder.getHospital();
        this.appointments = builder.getAppointments();
        this.prescriptions = builder.getPrescriptions();
        this.assignedDoctor = builder.getAssignedDoctor();
    }

    /**
     * Instantiates a new Patient.
     */
    public Patient() {
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
     */
    public void setStatus(PatientStatus status) {
        this.status = status;
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
     */
    public void setMbo(String mbo) {
        this.mbo = mbo;
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

    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets appointments.
     *
     * @param appointments the appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
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
     */
    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    @Override
    public List<Prescription> getPrescription() {
        return prescriptions;
    }

    @Override
    public void setPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }

    @Override
    public void removePrescription(Prescription prescription) {
        this.prescriptions.remove(prescription);
    }

    @Override
    public PatientStatus getPatientStatus() {
        return this.status;
    }

    @Override
    public void setPatientStatus(PatientStatus patientStatus) {
        this.status = patientStatus;
    }


    /**
     * Sets prescriptions.
     *
     * @param prescriptions the prescriptions
     */
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    /**
     * Gets assigned ward.
     *
     * @return the assigned ward
     */
    public Ward getAssignedWard() {
        return assignedWard;
    }

    /**
     * Sets assigned ward.
     *
     * @param assignedWard the assigned ward
     */
    public void setAssignedWard(Ward assignedWard) {
        this.assignedWard = assignedWard;
    }
}
