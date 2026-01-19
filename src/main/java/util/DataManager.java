package util;

import entity.Appointment;
import entity.Doctor;
import entity.Hospital;
import entity.Patient;
import entity.repository.DataRepositoryFactory;
import entity.repository.db.DbRepositoryFactory;
import entity.repository.file.FileRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Data manager.
 */
public class DataManager {
    private static final Logger logger = LoggerFactory.getLogger(DataManager.class);

    private static DataRepositoryFactory repositoryFactory;
    private static boolean useDatabase = true;

    static {
        initializeDefaultRepository();
    }

    private DataManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initialize the default repository (database by default).
     */
    private static void initializeDefaultRepository() {
        if (useDatabase) {
            try {
                repositoryFactory = new DbRepositoryFactory();
                DatabaseManager.initializeSchema();
                logger.info("Database repository initialized");
            } catch (SQLException e) {
                logger.error("Failed to initialize database repository, falling back to file repository", e);
                repositoryFactory = new FileRepositoryFactory();
                useDatabase = false;
                logger.info("File repository initialized as fallback");
            }
        } else {
            repositoryFactory = new FileRepositoryFactory();
            logger.info("File repository initialized");
        }
    }

    /**
     * Switch to database repository.
     */
    public static synchronized void switchToDatabase() {
        if (useDatabase && repositoryFactory instanceof DbRepositoryFactory) {
            logger.info("Already using database repository");
            return;
        }
        try {
            repositoryFactory = new DbRepositoryFactory();
            DatabaseManager.initializeSchema();
            useDatabase = true;
            logger.info("Switched to database repository");
        } catch (SQLException e) {
            logger.error("Failed to switch to database repository", e);
            throw new RuntimeException("Failed to switch to database repository", e);
        }
    }

    /**
     * Switch to file repository.
     */
    public static synchronized void switchToFileRepository() {
        if (!useDatabase && repositoryFactory instanceof FileRepositoryFactory) {
            logger.info("Already using file repository");
            return;
        }
        repositoryFactory = new FileRepositoryFactory();
        useDatabase = false;
        logger.info("Switched to file repository");
    }

    /**
     * Check if currently using database repository.
     *
     * @return true if using database, false if using file repository
     */
    public static boolean isUsingDatabase() {
        return useDatabase;
    }

    /**
     * Set the repository factory directly.
     *
     * @param factory the factory to use
     */
    public static synchronized void setRepositoryFactory(DataRepositoryFactory factory) {
        repositoryFactory = factory;
        useDatabase = factory instanceof DbRepositoryFactory;
        logger.info("Repository factory set to: {}", factory.getClass().getSimpleName());
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
        repositoryFactory.getHospitalRepository().saveAll(hospitals);
        repositoryFactory.getDoctorRepository().saveAll(doctors);
        repositoryFactory.getPatientRepository().saveAll(patients);
        repositoryFactory.getAppointmentRepository().saveAll(appointments);
        logger.info("All data saved");
    }

    /**
     * Load all data all data.
     *
     * @return the all data
     */
    public static AllData loadAllData() {
        List<Hospital> hospitals = repositoryFactory.getHospitalRepository().findAll();
        List<Doctor> doctors = repositoryFactory.getDoctorRepository().findAll();
        List<Patient> patients = repositoryFactory.getPatientRepository().findAll();
        List<Appointment> appointments = repositoryFactory.getAppointmentRepository().findAll();
        logger.info("All data loaded");
        return new AllData(hospitals, doctors, patients, appointments);
    }

    /**
     * Save hospitals.
     *
     * @param hospitals the hospitals
     */
    public static void saveHospitals(List<Hospital> hospitals) {
        repositoryFactory.getHospitalRepository().saveAll(hospitals);
    }

    /**
     * Save doctors.
     *
     * @param doctors the doctors
     */
    public static void saveDoctors(List<Doctor> doctors) {
        repositoryFactory.getDoctorRepository().saveAll(doctors);
    }

    /**
     * Save patients.
     *
     * @param patients the patients
     */
    public static void savePatients(List<Patient> patients) {
        repositoryFactory.getPatientRepository().saveAll(patients);
    }

    /**
     * Save appointments.
     *
     * @param appointments the appointments
     */
    public static void saveAppointments(List<Appointment> appointments) {
        repositoryFactory.getAppointmentRepository().saveAll(appointments);
    }

    /**
     * Add hospital.
     *
     * @param hospital the hospital
     */
    public static void addHospital(Hospital hospital) {
        repositoryFactory.getHospitalRepository().save(hospital);
    }

    /**
     * Add doctor.
     *
     * @param doctor the doctor
     */
    public static void addDoctor(Doctor doctor) {
        repositoryFactory.getDoctorRepository().save(doctor);
    }

    /**
     * Add patient.
     *
     * @param patient the patient
     */
    public static void addPatient(Patient patient) {
        repositoryFactory.getPatientRepository().save(patient);
    }

    /**
     * Add appointment.
     *
     * @param appointment the appointment
     */
    public static void addAppointment(Appointment appointment) {
        repositoryFactory.getAppointmentRepository().save(appointment);
    }

    /**
     * The type All data.
     */
    public record AllData(List<Hospital> hospitals, List<Doctor> doctors, List<Patient> patients,
                          List<Appointment> appointments) {
    }
}
