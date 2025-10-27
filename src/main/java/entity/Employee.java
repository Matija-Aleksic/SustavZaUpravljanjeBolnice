package entity;

import java.time.LocalDate;

public final class Employee extends Person implements Payable {
    private double salary;


    public Employee(String name, int age, Address address, double salary) {
        super(1, name, name, LocalDate.ofEpochDay(age));
        this.salary = salary;
    }

    public double getSalary() { return salary; }

    @Override
    public double calculatePay() {
        return salary;
    }



    @Override
    public int getAge() {
        return 0;
    }
}
