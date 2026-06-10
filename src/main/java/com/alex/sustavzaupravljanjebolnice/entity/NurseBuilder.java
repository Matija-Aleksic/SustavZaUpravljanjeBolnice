package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Nurse builder.
 */
public class NurseBuilder {
    private String firstName;
    private String lastName;
    private String oib;
    private LocalDate birthDate;
    private StaffRoles role;
    private String email;
    private double salary;
    private Hospital hospital;
    private Department department;
    private List<Ward> wards;

    /**
     * Build nurse.
     *
     * @return the nurse
     */
    public Nurse build() {
        return new Nurse(this);
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
    public NurseBuilder setFirstName(String firstName) {
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
    public NurseBuilder setLastName(String lastName) {
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
    public NurseBuilder setOib(String oib) {
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
    public NurseBuilder setBirthDate(LocalDate birthDate) {
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
    public NurseBuilder setRole(StaffRoles role) {
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
    public NurseBuilder setEmail(String email) {
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
    public NurseBuilder setSalary(double salary) {
        this.salary = salary;
        return this;
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
    public NurseBuilder setHospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    /**
     * Gets department.
     *
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets department.
     *
     * @param department the department
     * @return the department
     */
    public NurseBuilder setDepartment(Department department) {
        this.department = department;
        return this;
    }

    /**
     * Gets wards.
     *
     * @return the wards
     */
    public List<Ward> getWards() {
        return wards;
    }

    /**
     * Sets wards.
     *
     * @param wards the wards
     * @return the wards
     */
    public NurseBuilder setWards(List<Ward> wards) {
        this.wards = wards;
        return this;
    }
}