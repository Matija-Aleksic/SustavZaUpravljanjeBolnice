package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;

import java.io.Serializable;
import java.util.List;

/**
 * The type Ward.
 */
public class Ward implements Serializable {

    private Long id;
    private String name;

    private int maxCapacity;
    private int capacity;

    private Long nurseId;

    private transient Department department;

    private List<Patient> patients;

    /**
     * Instantiates a new Ward.
     */
    public Ward() {
    }

    /**
     * Instantiates a new Ward.
     *
     * @param id          the id
     * @param name        the name
     * @param maxCapacity the max capacity
     * @param capacity    the capacity
     * @param nurseId     the nurse id
     * @param department  the department
     */
    public Ward(Long id, String name, int maxCapacity, int capacity, Long nurseId, Department department) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.capacity = capacity;
        this.nurseId = nurseId;
        this.department = department;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets max capacity.
     *
     * @return the max capacity
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Sets max capacity.
     *
     * @param maxCapacity the max capacity
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Gets nurse id.
     *
     * @return the nurse id
     */
    public Long getNurseId() {
        return nurseId;
    }

    /**
     * Sets nurse id.
     *
     * @param nurseId the nurse id
     */
    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
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
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Gets patients.
     *
     * @return the patients
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Sets patients.
     *
     * @param patients the patients
     */
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}