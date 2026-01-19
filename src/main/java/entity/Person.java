package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * The type Person.
 */
public abstract class Person implements Serializable {


    /**
     * The Id.
     */
    protected final int id;
    /**
     * The First name.
     */
    protected final String firstName;
    /**
     * The Last name.
     */
    protected final String lastName;
    /**
     * The Date of birth.
     */
    protected final LocalDate dateOfBirth;

    /**
     * Instantiates a new Person.
     *
     * @param id          the id
     * @param firstName   the first name
     * @param lastName    the last name
     * @param dateOfBirth the date of birth
     */
    protected Person(int id, String firstName, String lastName, LocalDate dateOfBirth) {
        if (id <= 0) throw new exception.NegativeValueException("ID mora biti pozitivan!");
        this.id = id;
        this.firstName = Objects.requireNonNull(firstName, "Ime ne može biti null");
        this.lastName = Objects.requireNonNull(lastName, "Prezime ne može biti null");
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Datum rođenja ne može biti null");
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
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }


    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "%s (dob: %d)".formatted(getFullName(), getAge());
    }
}