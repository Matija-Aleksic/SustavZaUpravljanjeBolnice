package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.time.LocalDate;
import java.util.List;

public non-sealed class Nurse extends Staff {
    private Hospital hospital;
    private Department department;
    private List<Ward> wards;

    public Nurse(String firstName, String lastName, String oib, LocalDate birthDate, StaffRoles role, String email, double salary, Hospital hospital, Department department, List<Ward> wards) {
        super(firstName, lastName, oib, birthDate, role, email, salary);
        this.hospital = hospital;
        this.department = department;
        this.wards = wards;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }
}
