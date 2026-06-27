package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

@SuppressWarnings("java:S6548")
public class UserSession {
    private static final UserSession INSTANCE = new UserSession();
    private Staff loggedInStaff;

    private UserSession() {
    }

    public static UserSession getInstance() {
        return INSTANCE;
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