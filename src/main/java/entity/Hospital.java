package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Hospital.
 */
public class Hospital implements Serializable {
    

    private final int id;
    private final String name;
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();

    /**
     * Instantiates a new Hospital.
     *
     * @param id   the id
     * @param name the name
     */
    public Hospital(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
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
     * Add doctor.
     *
     * @param doctor the doctor
     */
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    /**
     * Add patient.
     *
     * @param patient the patient
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    /**
     * Gets doctors.
     *
     * @return the doctors
     */
    public List<Doctor> getDoctors() {
        return Collections.unmodifiableList(doctors);
    }

    /**
     * Gets patients.
     *
     * @return the patients
     */
    public List<Patient> getPatients() {
        return Collections.unmodifiableList(patients);
    }

    @Override
    public String toString() {
        return "Bolnica: " + name + " (doktora: " + doctors.size() + ", pacijenata: " + patients.size() + ")";
    }
}
