package com.alex.sustavzaupravljanjebolnice.entity;

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
