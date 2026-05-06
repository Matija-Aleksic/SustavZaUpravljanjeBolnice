package com.alex.sustavzaupravljanjebolnice.entity;

import java.time.LocalDate;

public class Staff extends Person {
    protected StaffRoles role;
    protected String email;


    public Staff(String firstName, String lastName, String oib, LocalDate birthDate, StaffRoles role, String email) {
        super(firstName, lastName, oib, birthDate);
        this.role = role;
        this.email = email;
    }

    public StaffRoles getRole() {
        return role;
    }

    public void setRole(StaffRoles role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
