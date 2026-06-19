package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

public class UserSession {
    private static UserSession instance;
    private Staff loggedInStaff;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public Staff getLoggedInStaff() {
        return loggedInStaff;
    }

    public void setLoggedInStaff(Staff staff) {
        this.loggedInStaff = staff;
    }

    public void cleanUserSession() {
        this.loggedInStaff = null;
    }
}