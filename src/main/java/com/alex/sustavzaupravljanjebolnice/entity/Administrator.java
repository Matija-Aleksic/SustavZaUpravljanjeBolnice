package com.alex.sustavzaupravljanjebolnice.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param administratorBuilder the administrator builder
     */
    public Administrator(AdministratorBuilder administratorBuilder) {
        super(administratorBuilder);
        logger.info("Administrator Builder created");
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
