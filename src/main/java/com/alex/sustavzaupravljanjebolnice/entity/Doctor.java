package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Schedulable;

import java.util.List;

public non-sealed class Doctor extends Staff implements Schedulable {
    private Hospital hospital;
    private List<Patient> assignedPatients;
    private List<Appointment> appointments;

    public Doctor(DoctorBuilder doctor) {
        super(doctor.getFirstName(), doctor.getLastName(), doctor.getOib(), doctor.getBirthDate(), doctor.getRole(), doctor.getEmail(), doctor.getSalary());
        this.hospital = doctor.getHospital();
        this.assignedPatients = doctor.getAssignedPatients();
        this.appointments = doctor.getAppointments();
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
