package com.alex.sustavzaupravljanjebolnice.entity;

import com.alex.sustavzaupravljanjebolnice.entity.builders.StaffBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Receptionist extends Staff {
    private static final Logger log = LoggerFactory.getLogger(Receptionist.class);
    private Hospital hospital;

    public Receptionist() {
        log.info("Receptionist created");
    }

    public Receptionist(StaffBuilder b) {
        super(b);
        log.info("Receptionist created");
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
