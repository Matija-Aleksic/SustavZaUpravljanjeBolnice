package com.alex.sustavzaupravljanjebolnice.entity.interfaces;

import com.alex.sustavzaupravljanjebolnice.entity.Staff;

public sealed interface Salaried permits Staff {
    double getSalary();

    void setSalary(double salary);
}
