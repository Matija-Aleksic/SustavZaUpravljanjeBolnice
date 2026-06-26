package com.alex.sustavzaupravljanjebolnice.entity.staff;

import com.alex.sustavzaupravljanjebolnice.entity.builders.StaffBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Receptionist extends Staff {
    private static final Logger log = LoggerFactory.getLogger(Receptionist.class);


    public Receptionist() {
        log.info("Receptionist created");
    }

    public Receptionist(StaffBuilder b) {
        super(b);
        log.info("Receptionist created");
    }
}
