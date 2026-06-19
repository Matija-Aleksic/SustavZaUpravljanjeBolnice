package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Administrator;

/**
 * The type Administrator builder.
 */
public class AdministratorBuilder extends StaffBuilder {
    /**
     * Build administrator.
     *
     * @return the administrator
     */
    private Long hospitalId;

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Administrator build() {
        return new Administrator(this);
    }
}
