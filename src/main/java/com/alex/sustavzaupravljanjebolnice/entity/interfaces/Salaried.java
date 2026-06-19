package com.alex.sustavzaupravljanjebolnice.entity.interfaces;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

/**
 * The interface Salaried.
 */
public sealed interface Salaried permits Staff {
    /**
     * Gets salary.
     *
     * @return the salary
     */
    double getSalary();

    /**
     * Sets salary.
     *
     * @param salary the salary
     */
    void setSalary(double salary);
}
