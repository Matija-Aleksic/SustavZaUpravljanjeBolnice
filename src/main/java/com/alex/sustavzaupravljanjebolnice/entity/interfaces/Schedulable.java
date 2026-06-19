package com.alex.sustavzaupravljanjebolnice.entity.interfaces;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;

import java.util.List;

/**
 * The interface Schedulable.
 */
public interface Schedulable {
    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    List<Appointment> getAppointments();

    /**
     * Sets appointment.
     *
     * @param appointment the appointment
     */
    void setAppointment(Appointment appointment);

    /**
     * Is available boolean.
     *
     * @param appointment the appointment
     * @return the boolean
     */
    Boolean isAvailable(Appointment appointment);

}
