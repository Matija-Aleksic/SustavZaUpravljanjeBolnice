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
    protected String firstName;
    /**
     * The Last name.
     */
    protected String lastName;
    /**
     * The Oib.
     */
    protected String oib;
    /**
     * The Birth date.
     */
    protected LocalDate birthDate;

    protected Integer Id;


    /**
     * Instantiates a new Person.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param oib       the oib
     * @param birthDate the birthdate
     */
    protected Person(Integer id, String firstName, String lastName, String oib, LocalDate birthDate) {
        this.Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.birthDate = birthDate;
    }

    protected Person() {
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets oib.
     *
     * @return the oib
     */
    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    /**
     * Gets birthdate.
     *
     * @return the birthdate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
