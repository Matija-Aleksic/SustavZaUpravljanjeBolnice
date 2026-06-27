package com.alex.sustavzaupravljanjebolnice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * The type Activity.
 */
public class Activity implements Serializable {

    private LocalDateTime madeOn;
    private String desc;
    private String madeBy;

    /**
     * Instantiates a new Activity.
     *
     * @param desc   the desc
     * @param madeBy the made by
     */
    public Activity(String desc, String madeBy) {
        this.madeOn = LocalDateTime.now(ZoneId.of("Europe/Zagreb"));
        this.desc = desc;
        this.madeBy = madeBy;
    }

    /**
     * Gets made on.
     *
     * @return the made on
     */
    public LocalDateTime getMadeOn() {
        return madeOn;
    }

    /**
     * Sets made on.
     *
     * @param madeOn the made on
     */
    public void setMadeOn(LocalDateTime madeOn) {
        this.madeOn = madeOn;
    }

    /**
     * Gets desc.
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets desc.
     *
     * @param desc the desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Gets made by.
     *
     * @return the made by
     */
    public String getMadeBy() {
        return madeBy;
    }

    /**
     * Sets made by.
     *
     * @param madeBy the made by
     */
    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }
}