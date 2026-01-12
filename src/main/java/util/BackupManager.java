package util;

import entity.Appointment;
import entity.Doctor;
import entity.Hospital;
import entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * The type Backup manager.
 */
public class BackupManager {
    private static final Logger logger = LoggerFactory.getLogger(BackupManager.class);
    private static final String BACKUP_FILE = "backup.bin";

    private BackupManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create backup.
     *
     * @param hospitals    the hospitals
     * @param doctors      the doctors
     * @param patients     the patients
     * @param appointments the appointments
     */
    public static void createBackup(List<Hospital> hospitals, List<Doctor> doctors,
                                    List<Patient> patients, List<Appointment> appointments) {
        BackupData backup = new BackupData(hospitals, doctors, patients, appointments);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            oos.writeObject(backup);
            logger.info("Backup created successfully: {}", BACKUP_FILE);
        } catch (IOException e) {
            logger.error("Error creating backup", e);
        }
    }

    /**
     * Restore backup backup data.
     *
     * @return the backup data
     */
    public static BackupData restoreBackup() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            BackupData backup = (BackupData) ois.readObject();
            logger.info("Backup restored successfully: {}", BACKUP_FILE);
            return backup;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error restoring backup", e);
            return null;
        }
    }

    /**
     * Backup exists boolean.
     *
     * @return the boolean
     */
    public static boolean backupExists() {
        return new File(BACKUP_FILE).exists();
    }

    /**
     * The type Backup data.
     */
    public record BackupData(List<Hospital> hospitals, List<Doctor> doctors, List<Patient> patients,
                             List<Appointment> appointments) implements Serializable {
        private static final long serialVersionUID = 1L;

    }
}

