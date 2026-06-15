package com.alex.sustavzaupravljanjebolnice.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Prescription.
 */
public class Prescription implements Serializable {
    private String id;
    private String name;
    private String description;
    private Integer doctorId;
    private Integer patientId;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Instantiates a new Prescription.
     *
     * @param id          the id
     * @param name        the name
     * @param description the description
     * @param doctorId    the doctor id
     * @param patientId   the patient id
     * @param startDate   the start date
     * @param endDate     the end date
     */
    public Prescription(String id, String name, String description, Integer doctorId, Integer patientId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Instantiates a new Prescription.
     */
    public Prescription() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets doctor id.
     *
     * @return the doctor id
     */
    public Integer getDoctorId() {
        return doctorId;
    }

    /**
     * Sets doctor id.
     *
     * @param doctorId the doctor id
     */
    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets patient id.
     *
     * @return the patient id
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * Sets patient id.
     *
     * @param patientId the patient id
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}


