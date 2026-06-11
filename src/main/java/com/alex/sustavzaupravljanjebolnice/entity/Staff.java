package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Salaried;

import java.time.LocalDate;

/**
 * The type Staff.
 */
public sealed class Staff extends Person implements Salaried permits Administrator, Doctor, Nurse {
    /**
     * The Role.
     */
    protected StaffRoles role;
    /**
     * The Email.
     */
    protected String email;
    /**
     * The Salary.
     */
    protected double salary;


    /**
     * Instantiates a new Staff.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param oib       the oib
     * @param birthDate the birth date
     * @param role      the role
     * @param email     the email
     * @param salary    the salary
     */
    public Staff(Integer id, String firstName, String lastName, String oib, LocalDate birthDate, StaffRoles role, String email, double salary) {
        super(id, firstName, lastName, oib, birthDate);
        this.role = role;
        this.email = email;
        this.salary = salary;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public StaffRoles getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(StaffRoles role) {
        this.role = role;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
