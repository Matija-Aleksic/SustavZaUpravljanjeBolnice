package entity;

import java.time.LocalDate;

public final class Patient extends Person implements Payable {
    private final String condition;
    private final String insuranceNumber;

    private Patient(Builder builder) {
        super(builder.id, builder.firstName, builder.lastName, builder.dateOfBirth);
        this.condition = builder.condition;
        this.insuranceNumber = builder.insuranceNumber;
    }

    public String getCondition() { return condition; }
    public String getInsuranceNumber() { return insuranceNumber; }

    @Override
    public double calculatePay() {
        // Pacijent "plaća" bolnici trošak pregleda (npr. ovisno o stanju)
        return condition != null && condition.toLowerCase().contains("operacija") ? 200.0 : 50.0;
    }

    @Override
    public String toString() {
        return "%s, stanje: %s, osiguranje: %s, trošak: %.2f€"
                .formatted(getFullName(), condition, insuranceNumber, calculatePay());
    }


    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String condition;
        private String insuranceNumber;

        public Builder(int id, String firstName, String lastName, LocalDate dateOfBirth) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
        }

        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder insuranceNumber(String insuranceNumber) {
            this.insuranceNumber = insuranceNumber;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }
}
