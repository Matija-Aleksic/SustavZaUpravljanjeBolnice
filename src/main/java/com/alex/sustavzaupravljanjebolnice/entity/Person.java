package com.alex.sustavzaupravljanjebolnice.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Person.
 */
public abstract class Person implements Serializable {
    /**
     * The First name.
     */
    protected final String firstName;
    /**
     * The Last name.
     */
    protected final String lastName;
    /**
     * The Oib.
     */
    protected final String oib;
    /**
     * The Birth date.
     */
    protected final LocalDate birthDate;

    protected final Integer Id;


    /**
     * Instantiates a new Person.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param oib       the oib
     * @param birthDate the birth date
     */
    protected Person(Integer id, String firstName, String lastName, String oib, LocalDate birthDate) {
        this.Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.birthDate = birthDate;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets oib.
     *
     * @return the oib
     */
    public String getOib() {
        return oib;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getId() {
        return Id;
    }

}
