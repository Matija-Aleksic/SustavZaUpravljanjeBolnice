package entity;

import java.time.LocalDate;

/**
 * The type Student.
 */
public final class Student extends Person implements Payable {
    private double scholarship;

    /**
     * Instantiates a new Student.
     *
     * @param name        the name
     * @param age         the age
     * @param address     the address
     * @param scholarship the scholarship
     */
    public Student(String name, int age, Address address, double scholarship) {
        super(3,name,name, LocalDate.now());
        this.scholarship = scholarship;
    }

    /**
     * Gets scholarship.
     *
     * @return the scholarship
     */
    public double getScholarship() { return scholarship; }

    @Override
    public double calculatePay() {
        return scholarship;
    }


}
