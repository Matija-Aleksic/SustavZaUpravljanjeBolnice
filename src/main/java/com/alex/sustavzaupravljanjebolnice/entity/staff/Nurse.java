package com.alex.sustavzaupravljanjebolnice.entity.staff;

import com.alex.sustavzaupravljanjebolnice.entity.builders.NurseBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Department;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;

import java.util.List;

/**
 * The type Nurse.
 */
public non-sealed class Nurse extends Staff {
    private transient Hospital hospital;
    private Department department;
    private List<Ward> wards;


    /**
     * Instantiates a new Nurse.
     *
     * @param b the b
     */
    public Nurse(NurseBuilder b) {
        super(b);

        this.hospital = b.getHospital();
        this.department = b.getDepartment();
        this.wards = b.getWards();
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
     */
    public void setDepartment(Department department) {
        this.department = department;
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
