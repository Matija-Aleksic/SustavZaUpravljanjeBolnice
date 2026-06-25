package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Administrator;

/**
 * The type Administrator builder.
 */
public class AdministratorBuilder extends StaffBuilder<AdministratorBuilder> {

    public Administrator build() {
        return new Administrator(this);
    }
}