package com.alex.sustavzaupravljanjebolnice.entity;

import java.time.LocalDate;
import java.util.List;

public class Patient extends Person {
    protected PatientStatus status;
    protected String mbo;
    private List<Appointment> appointments;

    protected Patient(String firstName, String lastName, String oib, LocalDate birthDate, PatientStatus status, String mbo, List<Appointment> appointments) {
        super(firstName, lastName, oib, birthDate);
        this.status = status;
        this.mbo = mbo;
        this.appointments = appointments;
    }

}
