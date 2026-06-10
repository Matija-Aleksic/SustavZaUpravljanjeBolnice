package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.time.LocalDate;
import java.util.List;

public class DoctorBuilder {
    private String firstName;
    private String lastName;
    private String oib;
    private LocalDate birthDate;
    private StaffRoles role;
    private String email;
    private double salary;
    private Hospital hospital;
    private List<Patient> assignedPatients;
    private List<Appointment> appointments;

    public Doctor createDoctor() {
        return new Doctor(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public DoctorBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public DoctorBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getOib() {
        return oib;
    }

    public DoctorBuilder setOib(String oib) {
        this.oib = oib;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public DoctorBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public StaffRoles getRole() {
        return role;
    }

    public DoctorBuilder setRole(StaffRoles role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DoctorBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public double getSalary() {
        return salary;
    }

    public DoctorBuilder setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public DoctorBuilder setHospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    public DoctorBuilder setAssignedPatients(List<Patient> assignedPatients) {
        this.assignedPatients = assignedPatients;
        return this;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public DoctorBuilder setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }
}