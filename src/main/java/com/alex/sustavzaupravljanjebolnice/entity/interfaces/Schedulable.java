package com.alex.sustavzaupravljanjebolnice.entity.interfaces;

import com.alex.sustavzaupravljanjebolnice.entity.Appointment;

import java.util.List;

public interface Schedulable {
    List<Appointment> getAppointments();

    void setAppointment(Appointment appointment);

    Boolean isAvailable(Appointment appointment);

}
