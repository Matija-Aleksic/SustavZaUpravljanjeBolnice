package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Salaried;

import java.time.LocalDate;

public sealed class Staff extends Person implements Salaried permits Administrator, Doctor, Nurse {
    protected StaffRoles role;
    protected String email;
    protected double salary;


    public Staff(String firstName, String lastName, String oib, LocalDate birthDate, StaffRoles role, String email, double salary) {
        super(firstName, lastName, oib, birthDate);
        this.role = role;
        this.email = email;
        this.salary = salary;
    }

    public StaffRoles getRole() {
        return role;
    }

    public void setRole(StaffRoles role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

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
