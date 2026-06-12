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

    /**
     * The Id.
     */
    protected Integer Id;


    /**
     * Instantiates a new Person.
     *
     * @param id        the id
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

    /**
     * Instantiates a new Person.
     */
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

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
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

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
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

    /**
     * Sets oib.
     *
     * @param oib the oib
     */
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

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return Id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        Id = id;
    }
}
