package entity;

import java.time.LocalDate;

/**
 * The type Employee.
 */
public final class Employee extends Person implements Payable {
    private double salary;


    /**
     * Instantiates a new Employee.
     *
     * @param name    the name
     * @param age     the age
     * @param address the address
     * @param salary  the salary
     */
    public Employee(String name, int age, Address address, double salary) {
        super(1, name, name, LocalDate.ofEpochDay(age));
        this.salary = salary;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
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
