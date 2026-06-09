package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Schedulable;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Treatable;

import java.time.LocalDate;
import java.util.List;

public class Patient extends Person implements Schedulable, Treatable {
    protected PatientStatus status;
    protected String mbo;
    private Hospital hospital;
    private List<Appointment> appointments;
    private List<Perscription> prescriptions;
    private Doctor assignedDoctor;


    public Patient(String firstName, String lastName, String oib, LocalDate birthDate, PatientStatus status, String mbo, Hospital hospital, List<Appointment> appointments, List<Perscription> prescriptions, Doctor assignedDoctor) {
        super(firstName, lastName, oib, birthDate);
        this.status = status;
        this.mbo = mbo;
        this.hospital = hospital;
        this.appointments = appointments;
        this.prescriptions = prescriptions;
        this.assignedDoctor = assignedDoctor;
    }

    public PatientStatus getStatus() {
        return status;
    }

    public void setStatus(PatientStatus status) {
        this.status = status;
    }

    public String getMbo() {
        return mbo;
    }

    public void setMbo(String mbo) {
        this.mbo = mbo;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public void setAppointment(Appointment appointment) {
        if (!this.appointments.contains(appointment)) {
            this.appointments.add(appointment);
            if (!appointment.doctor().getAppointments().contains(appointment)) {
                appointment.doctor().setAppointment(appointment);
            }
        }
    }

    @Override
    public Boolean isAvailable(Appointment appointment) {
        return !this.appointments.contains(appointment);
    }

    public List<Perscription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Perscription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }


    @Override
    public List<Perscription> getPrescription() {
        return prescriptions;
    }

    @Override
    public void setPrescription(Perscription prescription) {
        this.prescriptions.add(prescription);
    }

    @Override
    public void removePrescription(Perscription prescription) {
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


    public void removeAppointment(Appointment appointment) {
        if (this.appointments.contains(appointment)) {
            this.appointments.remove(appointment);
            appointment.doctor().getAppointments().remove(appointment);
        }
    }
}
