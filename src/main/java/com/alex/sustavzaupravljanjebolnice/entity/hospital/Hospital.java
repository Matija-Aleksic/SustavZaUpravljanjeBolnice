package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Conactable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Hospital.
 */
public class Hospital implements Conactable, Serializable {
    private Long id;
    private transient List<Department> departments;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    /**
     * Instantiates a new Hospital.
     *
     * @param id          the id
     * @param name        the name
     * @param address     the address
     * @param phoneNumber the phone number
     * @param email       the email
     */
    public Hospital(Long id, String name, String address, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.departments = new ArrayList<>();
    }

    /**
     * Instantiates a new Hospital.
     */
    public Hospital() {
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
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
     * Gets departments.
     *
     * @return the departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets departments.
     *
     * @param departments the departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
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
     * @return
     */
    public void setId(Long id) {
        this.id = id;
    }
}
