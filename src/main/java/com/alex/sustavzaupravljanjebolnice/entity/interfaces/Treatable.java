package com.alex.sustavzaupravljanjebolnice.entity.interfaces;

import com.alex.sustavzaupravljanjebolnice.entity.PatientStatus;
import com.alex.sustavzaupravljanjebolnice.entity.Perscription;

import java.util.List;

public interface Treatable {
    List<Perscription> getPrescription();

    void setPrescription(Perscription prescription);

    void removePrescription(Perscription prescription);

    PatientStatus getPatientStatus();

    void setPatientStatus(PatientStatus patientStatus);
}
