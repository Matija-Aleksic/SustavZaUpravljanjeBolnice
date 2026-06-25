package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.controller.popup.DoctorDialogController;
import com.alex.sustavzaupravljanjebolnice.controller.popup.NurseDialogController;
import com.alex.sustavzaupravljanjebolnice.controller.popup.PrescriptionDialogController;
import com.alex.sustavzaupravljanjebolnice.entity.Activity;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Prescription;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Nurse;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PrescriptionRepo;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import com.alex.sustavzaupravljanjebolnice.util.boxes.ConfirmationBox;
import com.alex.sustavzaupravljanjebolnice.util.boxes.InfoBox;
import javafx.application.Platform;

import java.sql.SQLException;

public class HospitalCrudHelper {
    private static final NurseRepo nurseRepo = new NurseRepo();
    private static final DoctorRepo doctorRepo = new DoctorRepo();
    private static final PatientRepo patientRepo = new PatientRepo();
    private static final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();
    private static final Staff currentStaff = UserSession.getInstance().getLoggedInStaff();
    private static final String operator = currentStaff.getFirstName() + " " + currentStaff.getLastName();

    // ==========================================
    //           DOCTOR CRUD ACTIONS
    // ==========================================
    public static void addDoctor(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/doctor-dialog.fxml", "Add New Doctor Profile", DoctorDialogController::setNewDoctorContext, c -> {
            if (c.isSaved()) refresh.run();
        });
    }

    public static void editDoctor(Doctor selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Warning", "Please select a doctor to edit.");
            return;
        }
        WindowManager.<DoctorDialogController>showModal("/com/alex/sustavzaupravljanjebolnice/popup/doctor-dialog.fxml", "Edit Doctor Profile", c -> c.setDoctor(selection), c -> {
            if (c.isSaved()) refresh.run();
        });
    }

    public static void deleteDoctor(Doctor selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Warning", "Please select a doctor record to delete.");
            return;
        }
        if (ConfirmationBox.show("Are you sure?", "Delete doctor: " + selection.getFirstName() + " " + selection.getLastName() + "?\nThis action cannot be undone.")) {
            executeAsync(() -> doctorRepo.deleteById((long) selection.getId()), "Deleted Doctor Profile: " + selection.getLastName(), () -> {
                refresh.run();
                InfoBox.show("Success");
            });
        }
    }

    // ==========================================
    //           NURSE CRUD ACTIONS
    // ==========================================
    public static void addNurse(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/nurse-dialog.fxml", "Register Nurse", c -> ((NurseDialogController) c).setNewNurseContext(), c -> {
            if (((NurseDialogController) c).isSaved()) refresh.run();
        });
    }

    public static void editNurse(Nurse selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a nurse profile.");
            return;
        }
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/nurse-dialog.fxml", "Update Nurse", c -> ((NurseDialogController) c).setNurse(selection), c -> {
            if (((NurseDialogController) c).isSaved()) refresh.run();
        });
    }

    public static void deleteNurse(Nurse selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a nurse profile.");
            return;
        }
        if (ConfirmationBox.show("Erase Record", "Remove nurse profile: " + selection.getFirstName() + "?")) {
            executeAsync(() -> nurseRepo.deleteById(Long.valueOf(selection.getId())), "Removed Nurse: " + selection.getLastName(), refresh);
        }
    }

    // ==========================================
    //           PATIENT CRUD ACTIONS
    // ==========================================
    public static void addPatient(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/patient-dialog.fxml", "Admit Patient", null, c -> refresh.run());
    }

    public static void editPatient(Patient selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a patient row entry.");
            return;
        }
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/patient-dialog.fxml", "Modify Patient", c -> {
        }, c -> refresh.run());
    }

    public static void deletePatient(Patient selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a patient to discharge.");
            return;
        }
        if (ConfirmationBox.show("Discharge Case", "Clear patient profile: " + selection.getFirstName() + "?")) {
            executeAsync(() -> patientRepo.deleteById(Long.valueOf(selection.getId())), "Discharged Patient: " + selection.getFirstName(), refresh);
        }
    }

    // ==========================================
    //         PRESCRIPTION CRUD ACTIONS
    // ==========================================
    public static void addPrescription(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Issue Prescription", null, c -> {
            if (((PrescriptionDialogController) c).isSaved()) refresh.run();
        });
    }

    public static void editPrescription(Prescription selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a prescription item row.");
            return;
        }
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Modify Orders", c -> ((PrescriptionDialogController) c).setPrescription(selection), c -> {
            if (((PrescriptionDialogController) c).isSaved()) refresh.run();
        });
    }

    public static void deletePrescription(Prescription selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a prescription ledger target.");
            return;
        }
        if (ConfirmationBox.show("Revoke Script", "Erase completely medication record ID: " + selection.getId() + "?")) {
            executeAsync(() -> prescriptionRepo.deleteById(selection.getId()), "Revoked prescription sequence ID: " + selection.getId(), refresh);
        }
    }

    private static void executeAsync(SqlRunnable action, String activityLog, Runnable completeCallback) {
        Thread.startVirtualThread(() -> {
            try {
                action.run();
                LogWriter.writeLogAsync(new Activity(activityLog, operator));
                Platform.runLater(completeCallback);
            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("Database Fault", e.getMessage()));
            }
        });
    }

    @FunctionalInterface
    public interface SqlRunnable {
        void run() throws SQLException;
    }
}