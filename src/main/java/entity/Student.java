package entity;

import java.time.LocalDate;

public final class Student extends Person implements Payable {
    private double scholarship;

    public Student(String name, int age, Address address, double scholarship) {
        super(3,name,name, LocalDate.now());
        this.scholarship = scholarship;
    }

    public double getScholarship() { return scholarship; }

    @Override
    public double calculatePay() {
        return scholarship;
    }


}
