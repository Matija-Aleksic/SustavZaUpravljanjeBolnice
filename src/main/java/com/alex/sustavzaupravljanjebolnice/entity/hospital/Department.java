package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Department.
 */
public class Department implements Serializable {
    private String name;
    private transient List<Ward> wards;

    /**
     * Instantiates a new Department.
     *
     * @param name the name
     */
    public Department(String name) {
        this.name = name;
        this.wards = new ArrayList<>();
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
}
