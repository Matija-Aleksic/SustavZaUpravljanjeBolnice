package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;

import java.io.Serializable;
import java.util.List;

/**
 * The type Ward.
 */
public class Ward implements Serializable {
    private int maxCapacity;
    private Long id;
    private String name;
    private int capacity;
    private transient Department department;
    private List<Patient> patients;


    /**
     * Instantiates a new Ward.
     *
     * @param maxCapacity the max capacity
     * @param id          the id
     * @param name        the name
     * @param capacity    the capacity
     * @param department  the department
     */
    public Ward(int maxCapacity, Long id, String name, int capacity, Department department) {
        this.maxCapacity = maxCapacity;
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.department = department;
    }

    /**
     * Instantiates a new Ward.
     */
    public Ward() {
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
}
