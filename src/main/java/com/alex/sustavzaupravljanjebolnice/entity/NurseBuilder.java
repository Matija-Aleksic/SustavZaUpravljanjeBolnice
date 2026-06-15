package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.util.List;

/**
 * The type Nurse builder.
 */
public class NurseBuilder extends StaffBuilder {

    private Hospital hospital;
    private Department department;
    private List<Ward> wards;

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
     * @return the hospital
     */
    public NurseBuilder setHospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
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
     * @return the department
     */
    public NurseBuilder setDepartment(Department department) {
        this.department = department;
        return this;
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
     * @return the wards
     */
    public NurseBuilder setWards(List<Ward> wards) {
        this.wards = wards;
        return this;
    }

    /**
     * Build nurse.
     *
     * @return the nurse
     */
    public Nurse build() {
        return new Nurse(this);
    }
}