package com.alex.sustavzaupravljanjebolnice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Activity implements Serializable {

    private LocalDateTime madeOn;
    private String desc;
    private String madeBy;

    public Activity(String desc, String madeBy) {
        this.madeOn = LocalDateTime.now(ZoneId.of("Europe/Zagreb"));
        this.desc = desc;
        this.madeBy = madeBy;
    }

    public LocalDateTime getMadeOn() {
        return madeOn;
    }

    public void setMadeOn(LocalDateTime madeOn) {
        this.madeOn = madeOn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }
}