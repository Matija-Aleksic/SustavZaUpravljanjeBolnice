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
 * The type Backup manager test.
 */
class BackupManagerTest {

    private static final String BACKUP_FILE = "backup.bin";
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

        Patient p1 = new Patient.Builder(1, "Jane", "Smith", LocalDate.of(1990, 1, 1))
                .condition("STABLE")
                .insuranceNumber("INS001")
                .build();
        patients.add(p1);

        Appointment a1 = new Appointment(1, d1, p1, LocalDateTime.now());
        appointments.add(a1);
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        // Clean up backup file
        File backupFile = new File(BACKUP_FILE);
        if (backupFile.exists()) {
            backupFile.delete();
        }
    }

    /**
     * Test create backup.
     */
    @Test
    void testCreateBackup() {
        // Create backup
        BackupManager.createBackup(hospitals, doctors, patients, appointments);

        // Verify backup file exists
        assertTrue(BackupManager.backupExists());
        assertTrue(new File(BACKUP_FILE).exists());
    }

    /**
     * Test restore backup.
     */
    @Test
    void testRestoreBackup() {
        // Create backup
        BackupManager.createBackup(hospitals, doctors, patients, appointments);

        // Restore backup
        BackupManager.BackupData restoredData = BackupManager.restoreBackup();

        // Verify restored data
        assertNotNull(restoredData);
        assertEquals(1, restoredData.hospitals().size());
        assertEquals(1, restoredData.doctors().size());
        assertEquals(1, restoredData.patients().size());
        assertEquals(1, restoredData.appointments().size());

        // Verify content
        assertEquals("Test Hospital", restoredData.hospitals().get(0).getName());
        assertEquals("John", restoredData.doctors().get(0).getFirstName());
    }

    /**
     * Test backup exists when no file.
     */
    @Test
    void testBackupExistsWhenNoFile() {
        // Ensure no backup file exists
        File backupFile = new File(BACKUP_FILE);
        if (backupFile.exists()) {
            backupFile.delete();
        }

        // Verify backup does not exist
        assertFalse(BackupManager.backupExists());
    }

    /**
     * Test restore backup when no file.
     */
    @Test
    void testRestoreBackupWhenNoFile() {
        // Ensure no backup file exists
        File backupFile = new File(BACKUP_FILE);
        if (backupFile.exists()) {
            backupFile.delete();
        }

        // Try to restore
        BackupManager.BackupData restoredData = BackupManager.restoreBackup();

        // Verify null is returned
        assertNull(restoredData);
    }
}

