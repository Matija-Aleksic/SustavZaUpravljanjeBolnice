package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Schedulable;

import java.time.LocalDate;
import java.util.List;

public non-sealed class Doctor extends Staff implements Schedulable {
    private Hospital hospital;
    protected List<Patient> assignedPatients;
    protected List<Appointment> appointments;

    public Doctor(String firstName, String lastName, String oib, LocalDate birthDate, StaffRoles role, String email, double salary, Hospital hospital, List<Patient> assignedPatients, List<Appointment> appointments) {
        super(firstName, lastName, oib, birthDate, role, email, salary);
        this.hospital = hospital;
        this.assignedPatients = assignedPatients;
        this.appointments = appointments;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

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

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }


    public void addPatient(Patient patient) {
        if (!this.assignedPatients.contains(patient)) {
            this.assignedPatients.add(patient);
            if (patient.getAssignedDoctor() != this) {
                patient.setAssignedDoctor(this);
            }
        }
    }

    public void removePatient(Patient patient) {
        if (this.assignedPatients.contains(patient)) {
            this.assignedPatients.remove(patient);
            if (patient.getAssignedDoctor() == this) {
                patient.setAssignedDoctor(null);
            }
        }
    }

    public void addAppointment(Appointment appointment) {
        if (!this.appointments.contains(appointment)) {
            this.appointments.add(appointment);
            if (!appointment.patient().getAppointments().contains(appointment)) {
                appointment.patient().setAppointment(appointment);
            }
        }
    }
}
