package com.alex.sustavzaupravljanjebolnice.entity.hospital;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Ward> wards;

    public Department(String name) {
        this.name = name;
        this.wards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }
}
