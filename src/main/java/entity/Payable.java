package entity;

public sealed interface Payable permits Doctor, Employee, Patient, Student {
    double calculatePay();
}
