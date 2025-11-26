package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Appointment.
 */
public record Appointment(int id, Doctor doctor, Patient patient, LocalDateTime dateTime) {
    @Override
    public String toString() {
        return "Pregled #" + id + " | " + doctor + " -> " + patient + " u " + dateTime;
    }

    /**
     * Format the dateTime using a provided formatter.
     *
     * @param formatter formatter to apply
     * @return formatted date-time string
     */
    public String formattedDateTime(DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }
}
