package util;

import com.google.gson.reflect.TypeToken;
import entity.Appointment;
import entity.Doctor;
import entity.Hospital;
import entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Data manager.
 */
public class DataManager {
    private static final Logger logger = LoggerFactory.getLogger(DataManager.class);

    private static final String HOSPITALS_FILE = "src/main/java/file/hospitals.json";
    private static final String DOCTORS_FILE = "src/main/java/file/doctors.json";
    private static final String PATIENTS_FILE = "src/main/java/file/patients.json";
    private static final String APPOINTMENTS_FILE = "src/main/java/file/appointments.json";

    private DataManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Save all data.
     *
     * @param hospitals    the hospitals
     * @param doctors      the doctors
     * @param patients     the patients
     * @param appointments the appointments
     */
    public static void saveAllData(List<Hospital> hospitals, List<Doctor> doctors,
                                   List<Patient> patients, List<Appointment> appointments) {
        JsonFileManager.saveToJson(HOSPITALS_FILE, hospitals);
        JsonFileManager.saveToJson(DOCTORS_FILE, doctors);
        JsonFileManager.saveToJson(PATIENTS_FILE, patients);
        JsonFileManager.saveToJson(APPOINTMENTS_FILE, appointments);
        logger.info("All data saved to JSON files");
    }

    /**
     * Load all data all data.
     *
     * @return the all data
     */
    public static AllData loadAllData() {
        Type hospitalType = new TypeToken<ArrayList<Hospital>>() {
        }.getType();
        Type doctorType = new TypeToken<ArrayList<Doctor>>() {
        }.getType();
        Type patientType = new TypeToken<ArrayList<Patient>>() {
        }.getType();
        Type appointmentType = new TypeToken<ArrayList<Appointment>>() {
        }.getType();

        List<Hospital> hospitals = JsonFileManager.loadFromJson(HOSPITALS_FILE, hospitalType);
        List<Doctor> doctors = JsonFileManager.loadFromJson(DOCTORS_FILE, doctorType);
        List<Patient> patients = JsonFileManager.loadFromJson(PATIENTS_FILE, patientType);
        List<Appointment> appointments = JsonFileManager.loadFromJson(APPOINTMENTS_FILE, appointmentType);

        logger.info("All data loaded from JSON files");
        return new AllData(hospitals, doctors, patients, appointments);
    }

    /**
     * Save hospitals.
     *
     * @param hospitals the hospitals
     */
    public static void saveHospitals(List<Hospital> hospitals) {
        JsonFileManager.saveToJson(HOSPITALS_FILE, hospitals);
    }

    /**
     * Save doctors.
     *
     * @param doctors the doctors
     */
    public static void saveDoctors(List<Doctor> doctors) {
        JsonFileManager.saveToJson(DOCTORS_FILE, doctors);
    }

    /**
     * Save patients.
     *
     * @param patients the patients
     */
    public static void savePatients(List<Patient> patients) {
        JsonFileManager.saveToJson(PATIENTS_FILE, patients);
    }

    /**
     * Save appointments.
     *
     * @param appointments the appointments
     */
    public static void saveAppointments(List<Appointment> appointments) {
        JsonFileManager.saveToJson(APPOINTMENTS_FILE, appointments);
    }

    /**
     * Add hospital.
     *
     * @param hospital the hospital
     */
    public static void addHospital(Hospital hospital) {
        List<Hospital> hospitals = loadAllData().hospitals();
        hospitals.add(hospital);
        saveHospitals(hospitals);
    }

    /**
     * Add doctor.
     *
     * @param doctor the doctor
     */
    public static void addDoctor(Doctor doctor) {
        List<Doctor> doctors = loadAllData().doctors();
        doctors.add(doctor);
        saveDoctors(doctors);
    }

    /**
     * Add patient.
     *
     * @param patient the patient
     */
    public static void addPatient(Patient patient) {
        List<Patient> patients = loadAllData().patients();
        patients.add(patient);
        savePatients(patients);
    }

    /**
     * Add appointment.
     *
     * @param appointment the appointment
     */
    public static void addAppointment(Appointment appointment) {
        List<Appointment> appointments = loadAllData().appointments();
        appointments.add(appointment);
        saveAppointments(appointments);
    }

    /**
     * The type All data.
     */
    public record AllData(List<Hospital> hospitals, List<Doctor> doctors, List<Patient> patients,
                          List<Appointment> appointments) {
    }
}
