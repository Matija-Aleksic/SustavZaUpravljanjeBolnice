package com.alex.sustavzaupravljanjebolnice.entity.builders;

import com.alex.sustavzaupravljanjebolnice.entity.Administrator;

/**
 * The type Administrator builder.
 */
public class AdministratorBuilder extends StaffBuilder {
    /**
     * Build administrator.
     *
     * @return the administrator
     */
    public Administrator build() {
        return new Administrator(this);
    }
}
