package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.time.LocalDate;
import java.util.List;

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

    public Nurse build() {
        return new Nurse(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public NurseBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public NurseBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getOib() {
        return oib;
    }

    public NurseBuilder setOib(String oib) {
        this.oib = oib;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public NurseBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public StaffRoles getRole() {
        return role;
    }

    public NurseBuilder setRole(StaffRoles role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public NurseBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public double getSalary() {
        return salary;
    }

    public NurseBuilder setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public NurseBuilder setHospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public NurseBuilder setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public NurseBuilder setWards(List<Ward> wards) {
        this.wards = wards;
        return this;
    }
}