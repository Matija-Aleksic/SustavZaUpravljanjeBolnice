package entity;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private int id;
    private String name;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    public Hospital(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public List<Doctor> getDoctors() { return doctors; }
    public List<Patient> getPatients() { return patients; }

    @Override
    public String toString() {
        return "Bolnica: " + name + " (doktora: " + doctors.size() + ", pacijenata: " + patients.size() + ")";
    }
}
