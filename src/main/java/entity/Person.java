package entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public abstract class Person implements Ageable {
    protected final int id;
    protected final String firstName;
    protected final String lastName;
    protected final LocalDate dateOfBirth;

    public Person(int id, String firstName, String lastName, LocalDate dateOfBirth) {
        if (id <= 0) throw new IllegalArgumentException("ID mora biti pozitivan!");
        this.id = id;
        this.firstName = Objects.requireNonNull(firstName, "Ime ne može biti null");
        this.lastName = Objects.requireNonNull(lastName, "Prezime ne može biti null");
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Datum rođenja ne može biti null");
    }



    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getFullName() { return firstName + " " + lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "%s (dob: %d)".formatted(getFullName(), getAge());
    }
}