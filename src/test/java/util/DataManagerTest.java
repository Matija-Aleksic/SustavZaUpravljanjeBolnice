package util;

import entity.Appointment;
import entity.Doctor;
import entity.Hospital;
import entity.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Data manager test.
 */
class DataManagerTest {

    private static final String ABSOLUTE_HOSPITALS_FILE = "target/hospitals.json";
    private static final String ABSOLUTE_DOCTORS_FILE = "target/doctors.json";
    private static final String ABSOLUTE_PATIENTS_FILE = "target/patients.json";
    private static final String ABSOLUTE_APPOINTMENTS_FILE = "target/appointments.json";

    private List<Hospital> hospitals;
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        hospitals = new ArrayList<>();
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();

        // Create sample data
        Hospital h1 = new Hospital(1, "Test Hospital");
        hospitals.add(h1);

        Doctor d1 = new Doctor(1, "John", "Doe", LocalDate.of(1980, 1, 1), "Cardiology", 5000.0);
        doctors.add(d1);
        h1.addDoctor(d1);

        Patient p1 = new Patient.Builder(1, "Jane", "Smith", LocalDate.of(1990, 1, 1))
                .condition("STABLE")
                .insuranceNumber("INS001")
                .build();
        patients.add(p1);
        h1.addPatient(p1);

        Appointment a1 = new Appointment(1, d1, p1, LocalDateTime.now());
        appointments.add(a1);

        // Ensure files exist for tests
        DataManager.saveAllData(hospitals, doctors, patients, appointments);
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        // Clean up test files
        deleteFileIfExists(ABSOLUTE_HOSPITALS_FILE);
        deleteFileIfExists(ABSOLUTE_DOCTORS_FILE);
        deleteFileIfExists(ABSOLUTE_PATIENTS_FILE);
        deleteFileIfExists(ABSOLUTE_APPOINTMENTS_FILE);
    }

    @Test
    void testSaveAndLoadAllData() {
        // Save data
        DataManager.saveAllData(hospitals, doctors, patients, appointments);

        // Verify files exist
        assertTrue(new File(ABSOLUTE_HOSPITALS_FILE).exists());
        assertTrue(new File(ABSOLUTE_DOCTORS_FILE).exists());
        assertTrue(new File(ABSOLUTE_PATIENTS_FILE).exists());
        assertTrue(new File(ABSOLUTE_APPOINTMENTS_FILE).exists());

        // Log file existence for debugging
        System.out.println("Hospitals file exists: " + new File(ABSOLUTE_HOSPITALS_FILE).exists());
        System.out.println("Doctors file exists: " + new File(ABSOLUTE_DOCTORS_FILE).exists());
        System.out.println("Patients file exists: " + new File(ABSOLUTE_PATIENTS_FILE).exists());
        System.out.println("Appointments file exists: " + new File(ABSOLUTE_APPOINTMENTS_FILE).exists());

        // Load data
        DataManager.AllData loadedData = DataManager.loadAllData();

        // Verify loaded data
        assertNotNull(loadedData);
        assertEquals(1, loadedData.hospitals().size());
        assertEquals(1, loadedData.doctors().size());
        assertEquals(1, loadedData.patients().size());
        assertEquals(1, loadedData.appointments().size());
        assertEquals("Test Hospital", loadedData.hospitals().get(0).getName());
    }

    @Test
    void testLoadAllDataWithNoFiles() {
        // Ensure no files exist
        deleteFileIfExists(ABSOLUTE_HOSPITALS_FILE);
        deleteFileIfExists(ABSOLUTE_DOCTORS_FILE);
        deleteFileIfExists(ABSOLUTE_PATIENTS_FILE);
        deleteFileIfExists(ABSOLUTE_APPOINTMENTS_FILE);

        // Load data
        DataManager.AllData loadedData = DataManager.loadAllData();

        // Verify empty lists are returned
        assertNotNull(loadedData);
        assertTrue(loadedData.hospitals().isEmpty());
        assertTrue(loadedData.doctors().isEmpty());
        assertTrue(loadedData.patients().isEmpty());
        assertTrue(loadedData.appointments().isEmpty());
    }

    @Test
    void testSaveIndividualEntities() {
        // Save individual entities
        DataManager.saveHospitals(hospitals);
        DataManager.saveDoctors(doctors);
        DataManager.savePatients(patients);
        DataManager.saveAppointments(appointments);

        // Verify files exist
        assertTrue(new File(ABSOLUTE_HOSPITALS_FILE).exists());
        assertTrue(new File(ABSOLUTE_DOCTORS_FILE).exists());
        assertTrue(new File(ABSOLUTE_PATIENTS_FILE).exists());
        assertTrue(new File(ABSOLUTE_APPOINTMENTS_FILE).exists());
    }

    private void deleteFileIfExists(String filename) {
        File file = new File(filename);
        if (file.exists() && !file.delete()) {
            System.out.println("Failed to delete file: " + filename);
        }
    }
}
