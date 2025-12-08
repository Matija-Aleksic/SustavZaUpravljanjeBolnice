package entity;

import java.time.LocalDate;
import java.time.Period;

/**
 * The type Employee.
 */
public final class Employee extends Person implements Payable {
    private final double salary;

    /**
     * Instantiates a new Employee.
     *
     * @param name   the name
     * @param salary the salary
     */
    public Employee(String name, double salary) {
        super(1, name, name, LocalDate.now());
        this.salary = salary;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    @Override
    public double calculatePay() {
        return salary;
    }

    @Override
    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
