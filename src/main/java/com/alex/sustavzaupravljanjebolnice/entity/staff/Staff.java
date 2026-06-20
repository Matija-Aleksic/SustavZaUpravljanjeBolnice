package com.alex.sustavzaupravljanjebolnice.entity.staff;

import com.alex.sustavzaupravljanjebolnice.entity.Person;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.builders.StaffBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Salaried;

/**
 * The type Staff.
 */
public sealed class Staff extends Person implements Salaried permits Administrator, Doctor, Nurse, Receptionist {
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
    protected Long hospitalId;


    /**
     * Instantiates a new Staff.
     */
    public Staff() {
        super(null, null, null, null, null);
    }

    /**
     * Instantiates a new Staff.
     *
     * @param b the b
     */
    public Staff(StaffBuilder b) {
        super(b.getId(), b.getFirstName(), b.getLastName(), b.getOib(), b.getBirthDate());
        this.role = b.getRole();
        this.email = b.getEmail();
        this.salary = b.getSalary();
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

    /**
     * Sets id.
     *
     * @param aLong the a long
     */
    public void setId(long aLong) {
        super.id = Math.toIntExact(aLong);
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
}
