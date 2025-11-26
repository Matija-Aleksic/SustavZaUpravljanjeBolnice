package entity;

import java.time.LocalDate;

/**
 * The type Patient.
 */
public final class Patient extends Person implements Payable {
    private static final double OPERATION_COST = 200.0; // extracted constants for Sonar
    private static final double DEFAULT_COST = 50.0;
    private final String condition;
    private final String insuranceNumber;

    private Patient(Builder builder) {
        super(builder.id, builder.firstName, builder.lastName, builder.dateOfBirth);
        this.condition = builder.condition;
        this.insuranceNumber = builder.insuranceNumber;
    }

    /**
     * Gets condition.
     *
     * @return the condition
     */
    public String getCondition() { return condition; }

    /**
     * Gets insurance number.
     *
     * @return the insurance number
     */
    public String getInsuranceNumber() { return insuranceNumber; }

    @Override
    public double calculatePay() {
        return condition != null && condition.toLowerCase().contains("operacija") ? OPERATION_COST : DEFAULT_COST;
    }

    @Override
    public String toString() {
        return "%s, stanje: %s, osiguranje: %s, trošak: %.2f€"
                .formatted(getFullName(), condition, insuranceNumber, calculatePay());
    }


    /**
     * The type Builder.
     */
    public static class Builder {
        private final int id;
        private final String firstName;
        private final String lastName;
        private final LocalDate dateOfBirth;
        private  String condition;
        private  String insuranceNumber;

        /**
         * Instantiates a new Builder.
         *
         * @param id          the id
         * @param firstName   the first name
         * @param lastName    the last name
         * @param dateOfBirth the date of birth
         */
        public Builder(int id, String firstName, String lastName, LocalDate dateOfBirth) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
        }

        /**
         * Condition builder.
         *
         * @param condition the condition
         * @return the builder
         */
        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        /**
         * Insurance number builder.
         *
         * @param insuranceNumber the insurance number
         * @return the builder
         */
        public Builder insuranceNumber(String insuranceNumber) {
            this.insuranceNumber = insuranceNumber;
            return this;
        }

        /**
         * Build patient.
         *
         * @return the patient
         */
        public Patient build() {
            return new Patient(this);
        }
    }
}
