package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import com.alex.sustavzaupravljanjebolnice.entity.interfaces.Conactable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hospital implements Conactable, Serializable {
    private transient List<Department> departments;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    public Hospital(String name, String address, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.departments = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
