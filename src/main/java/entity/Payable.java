package entity;

/**
 * The interface Payable.
 */
public sealed interface Payable permits Doctor, Employee, Patient, Student {
    /**
     * Calculate pay double.
     *
     * @return the double
     */
    double calculatePay();
}
