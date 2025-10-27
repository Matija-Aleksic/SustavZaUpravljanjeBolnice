package entity;

import java.time.LocalDateTime;

public record Appointment(int id, Doctor doctor, Patient patient, LocalDateTime dateTime) {
    @Override
    public String toString() {
        return "Pregled #" + id + " | " + doctor + " -> " + patient + " u " + dateTime;
    }

    public Object getDoctor() {
        return doctor;
    }
}
