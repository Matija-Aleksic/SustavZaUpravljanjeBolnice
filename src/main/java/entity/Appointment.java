package entity;

import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
public record Appointment(int id, Doctor doctor, Patient patient, LocalDateTime dateTime) {
    @Override
    public String toString() {
        return "Pregled #" + id + " | " + doctor + " -> " + patient + " u " + dateTime;
    }

    /**
     * Gets doctor.
     *
     * @return the doctor
     */
    public Object getDoctor() {
        return doctor;
    }
}
