package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.entity.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.AdminRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.repository.ReceptionistRepo;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffCredentialSeeder {

    /**
     * Pulls all categories of staff from the database and initializes credentials if missing.
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

            System.out.println("--- Starting Staff Credential Seeding ---");
            int accountsCreated = 0;

            for (Staff staff : staffList) {
                String firstName = staff.getFirstName() != null ? staff.getFirstName().trim() : "";
                String lastName = staff.getLastName() != null ? staff.getLastName().trim() : "";

                String username = firstName + " " + lastName;

                if (username.isBlank()) {
                    System.out.println("SKIPPED: Staff ID " + staff.getId() + " has no name."); // DEBUG
                    continue;
                }

                if (!pm.verifyPassword(username, firstName)) {
                    pm.savePassword(username, firstName);
                    System.out.println("Generated login for: " + username + " (Password: " + firstName + ")");
                    accountsCreated++;
                } else {
                    // This will show you the ones that already exist!
                    System.out.println("SKIPPED: Account already exists for " + firstName + lastName);
                }
            }

            System.out.println("--- Seeding Finished. Accounts created/updated: " + accountsCreated + " ---");

        } catch (Exception e) {
            System.err.println("Security error while hashing passwords: " + e.getMessage());
            e.printStackTrace();
        }
    }
}