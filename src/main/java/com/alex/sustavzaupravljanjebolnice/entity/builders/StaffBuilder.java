package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

import java.time.LocalDate;

/**
 * The type Staff builder.
 */
public class StaffBuilder {
    private Integer id;
    private String firstName;
    private String lastName;
    private String oib;
    private LocalDate birthDate;
    private StaffRoles role;
    private String email;
    private double salary;

    /**
     * Create staff staff.
     *
     * @return the staff
     */
    public Staff createStaff() {
        return new Staff(this);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @return the id
     */
    public StaffBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     * @return the first name
     */
    public StaffBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     * @return the last name
     */
    public StaffBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Gets oib.
     *
     * @return the oib
     */
    public String getOib() {
        return oib;
    }

    /**
     * Sets oib.
     *
     * @param oib the oib
     * @return the oib
     */
    public StaffBuilder setOib(String oib) {
        this.oib = oib;
        return this;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     * @return the birth date
     */
    public StaffBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
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
     * @return the role
     */
    public StaffBuilder setRole(StaffRoles role) {
        this.role = role;
        return this;
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
     * @return the email
     */
    public StaffBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets salary.
     *
     * @param salary the salary
     * @return the salary
     */
    public StaffBuilder setSalary(double salary) {
        this.salary = salary;
        return this;
    }
}