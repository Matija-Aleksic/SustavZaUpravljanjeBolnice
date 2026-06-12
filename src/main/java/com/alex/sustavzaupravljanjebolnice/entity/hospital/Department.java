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
     * @param name the name
     */
    public Department(Long id, String name, List<Ward> wards) {
        this.id = id;
        this.name = name;
        this.wards = wards;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
