package entity;

import java.time.LocalDate;

/**
 * The type Doctor.
 */
public final class Doctor extends Person implements Payable {
    private final String specialization;
    private final double baseSalary;

    /**
     * Instantiates a new Doctor.
     *
     * @param id             the id
     * @param firstName      the first name
     * @param lastName       the last name
     * @param dateOfBirth    the date of birth
     * @param specialization the specialization
     * @param baseSalary     the base salary
     */
    public Doctor(int id, String firstName, String lastName, LocalDate dateOfBirth,
                  String specialization, double baseSalary) {
        super(id, firstName, lastName, dateOfBirth);
        this.specialization = specialization;
        this.baseSalary = baseSalary;
    }

    /**
     * Gets specialization.
     *
     * @return the specialization
     */
    public String getSpecialization() { return specialization; }

    @Override
    public double calculatePay() {
        return baseSalary + getAge() * 50;
    }

    @Override
    public String toString() {
        return "Dr. %s (%s, plaća: %.2f€)".formatted(getFullName(), specialization, calculatePay());
    }

    public static double getBaseSalary(Object o) {
        return 1000;
    }
}