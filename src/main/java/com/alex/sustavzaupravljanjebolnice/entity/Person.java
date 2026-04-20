package com.alex.sustavzaupravljanjebolnice.entity;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Person implements Serializable {
    protected final String firstName;
    protected final String lastName;
    protected final String oib;
    protected final LocalDate birthDate;


    protected Person(String firstName, String lastName, String oib, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.birthDate = birthDate;
    }


}
