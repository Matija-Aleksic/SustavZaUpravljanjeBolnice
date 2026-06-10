package com.alex.sustavzaupravljanjebolnice.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static com.alex.sustavzaupravljanjebolnice.entity.Permissions.FULL;

/**
 * The type Administrator.
 */
public final class Administrator extends Staff {
    private static final Logger logger = LoggerFactory.getLogger(Administrator.class);
    /**
     * The Permissions.
     */
    Permissions permissions = FULL;


    /**
     * Instantiates a new Administrator.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param oib       the oib
     * @param birthDate the birth date
     * @param role      the role
     * @param email     the email
     * @param salary    the salary
     */
    public Administrator(String firstName, String lastName, String oib, LocalDate birthDate, StaffRoles role, String email, double salary) {
        super(firstName, lastName, oib, birthDate, role, email, salary);
        logger.debug("Administrator created with oib: {}", oib);
    }

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public Permissions getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
}
