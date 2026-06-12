package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import java.io.Serializable;
import java.util.List;

/**
 * The type Department.
 */
public class Department implements Serializable {
    private Long id;
    private String name;
    private transient List<Ward> wards;
    private Hospital hospital;

    /**
     * Instantiates a new Department.
     *
     * @param id    the id
     * @param name  the name
     * @param wards the wards
     */
    public Department(Long id, String name, List<Ward> wards) {
        this.id = id;
        this.name = name;
        this.wards = wards;
    }

    /**
     * Instantiates a new Department.
     */
    public Department() {
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
     * Gets wards.
     *
     * @return the wards
     */
    public List<Ward> getWards() {
        return wards;
    }

    /**
     * Sets wards.
     *
     * @param wards the wards
     */
    public void setWards(List<Ward> wards) {
        this.wards = wards;
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
     */
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
