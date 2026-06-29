package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;

/**
 * The type User session.
 */
@SuppressWarnings("java:S6548")
public class UserSession {
    private static final UserSession INSTANCE = new UserSession();
    private Staff loggedInStaff;

    private UserSession() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserSession getInstance() {
        return INSTANCE;
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