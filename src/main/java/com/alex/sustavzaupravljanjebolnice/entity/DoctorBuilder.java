package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Doctor builder.
 */
public class DoctorBuilder {
    private Integer id;
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
    private String phoneNumber;
    private String address;

    /**
     * Create doctor doctor.
     *
     * @return the doctor
     */
    public Doctor createDoctor() {
        return new Doctor(this);
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
    public DoctorBuilder setFirstName(String firstName) {
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
    public DoctorBuilder setLastName(String lastName) {
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
    public DoctorBuilder setOib(String oib) {
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
    public DoctorBuilder setBirthDate(LocalDate birthDate) {
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
    public DoctorBuilder setRole(StaffRoles role) {
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
    public DoctorBuilder setEmail(String email) {
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
    public DoctorBuilder setSalary(double salary) {
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
    public DoctorBuilder setHospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    /**
     * Gets assigned patients.
     *
     * @return the assigned patients
     */
    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    /**
     * Sets assigned patients.
     *
     * @param assignedPatients the assigned patients
     * @return the assigned patients
     */
    public DoctorBuilder setAssignedPatients(List<Patient> assignedPatients) {
        this.assignedPatients = assignedPatients;
        return this;
    }

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public String getPhoneNumber() {
        return phoneNumber;

    }

    public DoctorBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DoctorBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets appointments.
     *
     * @param appointments the appointments
     * @return the appointments
     */


    public DoctorBuilder setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

}