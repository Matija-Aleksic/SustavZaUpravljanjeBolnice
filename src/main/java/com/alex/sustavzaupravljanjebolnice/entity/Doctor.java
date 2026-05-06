package com.alex.sustavzaupravljanjebolnice.entity;

import java.time.LocalDate;
import java.util.List;

public class Doctor extends Staff {
    protected List<Patient> assignedPatients;
    protected List<Appointment> appointments;

    public Doctor(String firstName, String lastName, String oib, LocalDate birthDate, String email, List<Patient> assignedPatients, List<Appointment> appointments) {
        super(firstName, lastName, oib, birthDate, StaffRoles.DOCTOR, email);
        this.assignedPatients = assignedPatients;
        this.appointments = appointments;
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

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
