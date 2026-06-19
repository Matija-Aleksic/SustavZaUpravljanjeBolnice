package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctor builder.
 */
public class DoctorBuilder extends StaffBuilder {

    private Hospital hospital;
    private List<Patient> assignedPatients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private String phoneNumber;
    private String address;

    @Override
    public DoctorBuilder setId(Integer id) {
        super.setId(id);
        return this;
    }

    @Override
    public DoctorBuilder setFirstName(String firstName) {
        super.setFirstName(firstName);
        return this;
    }

    @Override
    public DoctorBuilder setLastName(String lastName) {
        super.setLastName(lastName);
        return this;
    }

    @Override
    public DoctorBuilder setOib(String oib) {
        super.setOib(oib);
        return this;
    }

    @Override
    public DoctorBuilder setBirthDate(LocalDate birthDate) {
        super.setBirthDate(birthDate);
        return this;
    }

    @Override
    public DoctorBuilder setRole(StaffRoles role) {
        super.setRole(role);
        return this;
    }

    @Override
    public DoctorBuilder setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public DoctorBuilder setSalary(double salary) {
        super.setSalary(salary);
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

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     * @return the phone number
     */
    public DoctorBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     * @return the address
     */
    public DoctorBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Build doctor.
     *
     * @return the doctor
     */
    public Doctor build() {
        return new Doctor(this);
    }
}