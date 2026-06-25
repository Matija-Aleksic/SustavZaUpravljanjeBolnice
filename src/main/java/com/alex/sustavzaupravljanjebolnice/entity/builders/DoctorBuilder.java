package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctor builder.
 */
public class DoctorBuilder extends StaffBuilder<DoctorBuilder> {

    private List<Patient> assignedPatients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private String phoneNumber;
    private String address;

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