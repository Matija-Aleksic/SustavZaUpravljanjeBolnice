package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.util.List;

public non-sealed class Nurse extends Staff {
    private transient Hospital hospital;
    private Department department;
    private List<Ward> wards;

    public Nurse(NurseBuilder nusrseBuilder) {
        super(nusrseBuilder.getFirstName(), nusrseBuilder.getLastName(), nusrseBuilder.getOib(), nusrseBuilder.getBirthDate(), nusrseBuilder.getRole(), nusrseBuilder.getEmail(), nusrseBuilder.getSalary());
        this.hospital = nusrseBuilder.getHospital();
        this.department = nusrseBuilder.getDepartment();
        this.wards = nusrseBuilder.getWards();
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
