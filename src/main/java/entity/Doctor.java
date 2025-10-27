package entity;

import java.time.LocalDate;

public final class Doctor extends Person implements Payable {
    private final String specialization;
    private final double baseSalary;

    public Doctor(int id, String firstName, String lastName, LocalDate dateOfBirth,
                  String specialization, double baseSalary) {
        super(id, firstName, lastName, dateOfBirth);
        this.specialization = specialization;
        this.baseSalary = baseSalary;
    }

    public String getSpecialization() { return specialization; }

    @Override
    public double calculatePay() {
        // Plaća raste s godinama iskustva
        return baseSalary + getAge() * 50;
    }

    @Override
    public String toString() {
        return "Dr. %s (%s, plaća: %.2f€)".formatted(getFullName(), specialization, calculatePay());
    }
}