package com.alex.sustavzaupravljanjebolnice.entity.interfaces;

import com.alex.sustavzaupravljanjebolnice.entity.PatientStatus;
import com.alex.sustavzaupravljanjebolnice.entity.Perscription;

import java.util.List;

/**
 * The interface Treatable.
 */
public interface Treatable {
    /**
     * Gets prescription.
     *
     * @return the prescription
     */
    List<Perscription> getPrescription();

    /**
     * Sets prescription.
     *
     * @param prescription the prescription
     */
    void setPrescription(Perscription prescription);

    /**
     * Remove prescription.
     *
     * @param prescription the prescription
     */
    void removePrescription(Perscription prescription);

    /**
     * Gets patient status.
     *
     * @return the patient status
     */
    PatientStatus getPatientStatus();

    /**
     * Sets patient status.
     *
     * @param patientStatus the patient status
     */
    void setPatientStatus(PatientStatus patientStatus);
}
