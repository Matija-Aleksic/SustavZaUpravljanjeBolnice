package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;

import java.util.List;

public class Ward {
    private final int maxCapacity;
    private String name;
    private int capacity;
    private Department department;
    private List<Patient> patients;


    public Ward(String name, int maxCapacity, Department department) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
