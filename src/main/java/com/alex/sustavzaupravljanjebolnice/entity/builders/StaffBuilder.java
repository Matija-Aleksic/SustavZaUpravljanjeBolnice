package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

import java.time.LocalDate;

/**
 * The type Staff builder.
 *
 * @param <T> the type parameter
 */
public class StaffBuilder<T extends StaffBuilder<T>> {
    private Integer id;
    private String firstName;
    private String lastName;
    private String oib;
    private LocalDate birthDate;
    private StaffRoles role;
    private String email;
    private double salary;
    private Hospital hospital;

    /**
     * Self t.
     *
     * @return the t
     */
    protected final T self() {
        //noinspection unchecked
        return (T) this;
    }

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
    public T setId(Integer id) {
        this.id = id;
        return self();
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
    public T setFirstName(String firstName) {
        this.firstName = firstName;
        return self();
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
    public T setLastName(String lastName) {
        this.lastName = lastName;
        return self();
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
    public T setOib(String oib) {
        this.oib = oib;
        return self();
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
    public T setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return self();
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
    public T setRole(StaffRoles role) {
        this.role = role;
        return self();
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
    public T setEmail(String email) {
        this.email = email;
        return self();
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
    public T setSalary(double salary) {
        this.salary = salary;
        return self();
    }

    /**
     * Gets hospital.
     *
     * @return the hospital
     */
    public Hospital getHospital() {
        return hospital;
    }

    /**
     * Sets hospital.
     *
     * @param hospital the hospital
     * @return the hospital
     */
    public T setHospital(Hospital hospital) {
        this.hospital = hospital;
        return self();
    }
}