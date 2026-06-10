package com.alex.sustavzaupravljanjebolnice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
public record Appointment(int id, Doctor doctor, Patient patient, LocalDateTime dateTime) implements Serializable {
    @Override
    public String toString() {
        return "Pregled #" + id + " | " + doctor + " -> " + patient + " u " + dateTime;
    }


}
