package entity;

import java.time.LocalDate;

/**
 * The type Student.
 */
public final class Student extends Person implements Payable {
    private final double scholarship;

    /**
     * Instantiates a new Student.
     *
     * @param name        the name
     * @param scholarship the scholarship
     */
    public Student(String name, double scholarship) {
        super(3, name, name, LocalDate.now());
        this.scholarship = scholarship;
    }

    /**
     * Gets scholarship.
     *
     * @return the scholarship
     */
    public double getScholarship() {
        return scholarship;
    }

    @Override
    public double calculatePay() {
        return scholarship;
    }
}
