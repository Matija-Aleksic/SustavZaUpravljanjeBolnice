package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Patient test.
 */
class PatientTest {
    /**
     * Builder builds patient.
     */
    @Test
    void builderBuildsPatient() {
        Patient p = new Patient.Builder(1, "Ivana", "Kos", LocalDate.now().minusYears(30))
                .condition("RECOVERING")
                .insuranceNumber("INS123")
                .build();
        assertEquals("Ivana Kos", p.getFullName());
        assertEquals(ConditionStatus.RECOVERING, p.getCondition());
        assertEquals("INS123", p.getInsuranceNumber());
    }

    /**
     * Calculate pay condition with operation costs more.
     */
    @Test
    void calculatePay_conditionWithOperationCostsMore() {
        Patient p = new Patient.Builder(2, "Luka", "Maric", LocalDate.now().minusYears(40))
                .condition("Planirana operacija koljena")
                .insuranceNumber("X")
                .build();
        assertEquals(200.0, p.calculatePay(), 0.001);
    }

    /**
     * Calculate pay default cost for non operation.
     */
    @Test
    void calculatePay_defaultCostForNonOperation() {
        Patient p = new Patient.Builder(3, "Tea", "Loncar", LocalDate.now().minusYears(25))
                .condition("Stabilno stanje")
                .insuranceNumber("Y")
                .build();
        assertEquals(50.0, p.calculatePay(), 0.001);
    }
}
