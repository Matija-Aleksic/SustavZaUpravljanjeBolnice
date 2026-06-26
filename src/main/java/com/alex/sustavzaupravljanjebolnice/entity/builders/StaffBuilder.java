package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

import java.time.LocalDate;

/**
 * The type Staff builder.
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

    protected final T self() {
        //noinspection unchecked
        return (T) this;
    }

    public Staff createStaff() {
        return new Staff(this);
    }

    public Integer getId() {
        return id;
    }

    public T setId(Integer id) {
        this.id = id;
        return self();
    }

    public String getFirstName() {
        return firstName;
    }

    public T setFirstName(String firstName) {
        this.firstName = firstName;
        return self();
    }

    public String getLastName() {
        return lastName;
    }

    public T setLastName(String lastName) {
        this.lastName = lastName;
        return self();
    }

    public String getOib() {
        return oib;
    }

    public T setOib(String oib) {
        this.oib = oib;
        return self();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public T setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return self();
    }

    public StaffRoles getRole() {
        return role;
    }

    public T setRole(StaffRoles role) {
        this.role = role;
        return self();
    }

    public String getEmail() {
        return email;
    }

    public T setEmail(String email) {
        this.email = email;
        return self();
    }

    public double getSalary() {
        return salary;
    }

    public T setSalary(double salary) {
        this.salary = salary;
        return self();
    }

    public Hospital getHospital() {
        return hospital;
    }

    public T setHospital(Hospital hospital) {
        this.hospital = hospital;
        return self();
    }
}