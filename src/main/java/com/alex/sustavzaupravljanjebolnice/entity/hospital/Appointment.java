package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
public record Appointment(int id, Integer doctorId, Integer patientId, LocalDateTime dateTime) implements Serializable {
    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", doctorId=" + doctorId + ", patientId=" + patientId + ", dateTime=" + dateTime + '}';
    }
}
