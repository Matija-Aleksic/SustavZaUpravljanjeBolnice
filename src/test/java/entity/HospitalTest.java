package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Hospital test.
 */
class HospitalTest {
    /**
     * Gets doctors returns unmodifiable list.
     */
    @Test
    void getDoctors_returnsUnmodifiableList() {
        Hospital h = new Hospital(1, "Central");
        Doctor d = new Doctor(1, "Ana", "Ivic", LocalDate.now().minusYears(10), "Pediatrija", 2000);
        h.addDoctor(d);
        List<Doctor> docs = h.getDoctors();
        assertEquals(1, docs.size());
        Doctor another = new Doctor(2, "Ivan", "Novak", LocalDate.now().minusYears(5), "Opća", 1800);
        assertThrows(UnsupportedOperationException.class, () -> docs.add(another));
        assertThrows(UnsupportedOperationException.class, () -> docs.remove(d));
    }

    /**
     * Gets patients returns unmodifiable list.
     */
    @Test
    void getPatients_returnsUnmodifiableList() {
        Hospital h = new Hospital(1, "Central");
        Patient p = new Patient.Builder(1, "Ivana", "Kos", LocalDate.now().minusYears(30))
                .condition("STABLE")
                .insuranceNumber("I1")
                .build();
        h.addPatient(p);
        List<Patient> pts = h.getPatients();
        assertEquals(1, pts.size());
        Patient p2 = new Patient.Builder(2, "Luka", "Maric", LocalDate.now().minusYears(40))
                .condition("CRITICAL")
                .insuranceNumber("X")
                .build();
        assertThrows(UnsupportedOperationException.class, () -> pts.add(p2));
        assertThrows(UnsupportedOperationException.class, pts::clear);
    }

    /**
     * Appointment formatted date time works.
     */
    @Test
    void appointmentFormattedDateTimeWorks() {
        Doctor d = new Doctor(1, "Ana", "Ivic", LocalDate.now().minusYears(10), "Pediatrija", 2000);
        Patient p = new Patient.Builder(2, "Luka", "Maric", LocalDate.now().minusYears(40))
                .condition("CRITICAL")
                .insuranceNumber("XX")
                .build();
        var dt = LocalDateTime.of(2025, 1, 2, 14, 30);
        Appointment a = new Appointment(5, d, p, dt);
        String formatted = a.formattedDateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals("2025-01-02 14:30", formatted);
    }
}
