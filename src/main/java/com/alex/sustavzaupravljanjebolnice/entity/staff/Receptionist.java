package com.alex.sustavzaupravljanjebolnice.entity.staff;

import com.alex.sustavzaupravljanjebolnice.entity.builders.StaffBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Receptionist.
 */
public final class Receptionist extends Staff {
    private static final Logger log = LoggerFactory.getLogger(Receptionist.class);


    /**
     * Instantiates a new Receptionist.
     */
    public Receptionist() {
        log.info("Receptionist created");
    }

    /**
     * Instantiates a new Receptionist.
     *
     * @param b the b
     */
    public Receptionist(StaffBuilder b) {
        super(b);
        log.info("Receptionist created");
    }
}
