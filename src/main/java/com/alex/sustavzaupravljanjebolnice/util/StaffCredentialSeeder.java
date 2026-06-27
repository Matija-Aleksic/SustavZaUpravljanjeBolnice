package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.AdminRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.repository.ReceptionistRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Staff credential seeder.
 */
public class StaffCredentialSeeder {

    private static final Logger log = LoggerFactory.getLogger(StaffCredentialSeeder.class);

    private StaffCredentialSeeder() {
    }

    /**
     * Seed staff credentials.
     */
    public static void seedStaffCredentials() {
        File credentialStorage = new File("passwords.properties");
        List<Staff> staffList = new ArrayList<>();

        DoctorRepo doctorRepo = new DoctorRepo();
        NurseRepo nurseRepo = new NurseRepo();
        AdminRepo adminRepo = new AdminRepo();
        ReceptionistRepo receptionistRepo = new ReceptionistRepo();

        try {
            staffList.addAll(doctorRepo.getAll());
            staffList.addAll(nurseRepo.getAll());
            staffList.addAll(adminRepo.getAll());
            staffList.addAll(receptionistRepo.getAll());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to pull staff collections from database", e);
        }

        try {
            PasswordManager pm = new PasswordManager(credentialStorage);

            log.info("--- Starting Staff Credential Seeding ---");
            int accountsCreated = 0;

            for (Staff staff : staffList) {
                String firstName = staff.getFirstName() != null ? staff.getFirstName().trim() : "";
                String lastName = staff.getLastName() != null ? staff.getLastName().trim() : "";

                String username = firstName + " " + lastName;

                if (username.isBlank()) {
                    log.info("SKIPPED: Staff ID {} has no name.", staff.getId()); // DEBUG
                    continue;
                }

                if (!pm.verifyPassword(username, firstName)) {
                    pm.savePassword(username, firstName);
                    log.info("Generated login for: {} (Password: {})", username, firstName);
                    accountsCreated++;
                } else {
                    log.info("SKIPPED: Account already exists for {}{}", firstName, lastName);
                }
            }

            log.info("--- Seeding Finished. Accounts created/updated: {} ---", accountsCreated);

        } catch (Exception e) {
            log.error("Security error while hashing passwords: {}", e.getMessage());

        }
    }
}