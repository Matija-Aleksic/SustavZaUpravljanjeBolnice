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
}
