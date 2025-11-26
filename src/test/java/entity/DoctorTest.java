package entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {
    @Test
    void calculatePay_includesAgeBonus() {
        Doctor d = new Doctor(1, "Ana", "Ivic", LocalDate.now().minusYears(10), "Pediatrija", 2000);
        double pay = d.calculatePay();
        assertEquals(2000 + 10 * 50, pay, 0.001);
    }

    @Test
    void getBaseSalary_returnsConstructorValue() {
        Doctor d = new Doctor(2, "Marko", "Peric", LocalDate.now(), "Opća", 1500);
        assertEquals(1500, d.getBaseSalary());
    }
}

