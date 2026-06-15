package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;

import java.io.Serializable;
import java.util.List;

public class Ward implements Serializable {

    private Long id;
    private String name;

    private int maxCapacity;
    private int capacity;

    private Long nurseId;

    private transient Department department;

    private List<Patient> patients;

    public Ward() {
    }

    public Ward(Long id, String name, int maxCapacity, int capacity, Long nurseId, Department department) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.capacity = capacity;
        this.nurseId = nurseId;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}