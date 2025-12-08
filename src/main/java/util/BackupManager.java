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
 * Utility class for creating and restoring binary backups
 */
public class BackupManager {
    private static final Logger logger = LoggerFactory.getLogger(BackupManager.class);
    private static final String BACKUP_FILE = "backup.bin";

    private BackupManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create a backup of all data to a binary file
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
     * Restore data from backup file
     *
     * @return the backup data or null if error occurs
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
     * Check if backup file exists
     *
     * @return true if backup exists
     */
    public static boolean backupExists() {
        return new File(BACKUP_FILE).exists();
    }

    /**
         * Data container for backup
         */
        public record BackupData(List<Hospital> hospitals, List<Doctor> doctors, List<Patient> patients,
                                 List<Appointment> appointments) implements Serializable {
            private static final long serialVersionUID = 1L;

    }
}

