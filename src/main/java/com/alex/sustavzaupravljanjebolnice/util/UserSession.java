package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

/**
 * The type User session.
 */
public class UserSession {
    private static UserSession instance;
    private Staff loggedInStaff;

    private UserSession() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Gets logged in staff.
     *
     * @return the logged in staff
     */
    public Staff getLoggedInStaff() {
        return loggedInStaff;
    }

    /**
     * Sets logged in staff.
     *
     * @param staff the staff
     */
    public void setLoggedInStaff(Staff staff) {
        this.loggedInStaff = staff;
    }

    /**
     * Clean user session.
     */
    public void cleanUserSession() {
        this.loggedInStaff = null;
    }
}