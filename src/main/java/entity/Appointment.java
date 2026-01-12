package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Appointment.
 */
public record Appointment(int id, Doctor doctor, Patient patient, LocalDateTime dateTime) implements Serializable {
    @Override
    public String toString() {
        return "Pregled #" + id + " | " + doctor + " -> " + patient + " u " + dateTime;
    }

    /**
     * Formatted date time string.
     *
     * @param formatter the formatter
     * @return the string
     */
    public String formattedDateTime(DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id();
    }

    /**
     * Gets date time.
     *
     * @return the date time
     */
    public LocalDateTime getDateTime() {
        return dateTime();
    }
}
