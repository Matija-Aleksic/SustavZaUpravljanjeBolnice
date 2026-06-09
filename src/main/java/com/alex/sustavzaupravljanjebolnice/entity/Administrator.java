package com.alex.sustavzaupravljanjebolnice.entity;

import java.time.LocalDate;

import static com.alex.sustavzaupravljanjebolnice.entity.Permissions.FULL;

public final class Administrator extends Staff {
    Permissions permissions = FULL;


    public Administrator(String firstName, String lastName, String oib, LocalDate birthDate, StaffRoles role, String email, double salary) {
        super(firstName, lastName, oib, birthDate, role, email, salary);
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
}
