package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.controller.popup.DoctorDialogController;
import com.alex.sustavzaupravljanjebolnice.controller.popup.NurseDialogController;
import com.alex.sustavzaupravljanjebolnice.controller.popup.PatientDialogController;
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

/**
 * The type Hospital crud helper.
 */
public class HospitalCrudHelper {
    private static final NurseRepo nurseRepo = new NurseRepo();
    private static final DoctorRepo doctorRepo = new DoctorRepo();
    private static final PatientRepo patientRepo = new PatientRepo();
    private static final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();
    private static final Staff currentStaff = UserSession.getInstance().getLoggedInStaff();
    private static final String OPERATOR = currentStaff.getFirstName() + " " + currentStaff.getLastName();

    /**
     * Add doctor.
     *
     * @param refresh the refresh
     */
    public static void addDoctor(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/doctor-dialog.fxml", "Add New Doctor Profile", DoctorDialogController::setNewDoctorContext, c -> {
            if (c.isSaved()) {
                logActionAsync("Added a new Doctor profile");
                refresh.run();
            }
        });
    }

    /**
     * Edit doctor.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
    public static void editDoctor(Doctor selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Warning", "Please select a doctor to edit.");
            return;
        }
        WindowManager.<DoctorDialogController>showModal("/com/alex/sustavzaupravljanjebolnice/popup/doctor-dialog.fxml", "Edit Doctor Profile", c -> c.setDoctor(selection), c -> {
            if (c.isSaved()) {
                logActionAsync("Modified Doctor profile: " + selection.getLastName());
                refresh.run();
            }
        });
    }

    /**
     * Delete doctor.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
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


    /**
     * Add nurse.
     *
     * @param refresh the refresh
     */
    public static void addNurse(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/nurse-dialog.fxml", "Register Nurse", c -> ((NurseDialogController) c).setNewNurseContext(), c -> {
            if (((NurseDialogController) c).isSaved()) {
                logActionAsync("Registered a new Nurse profile");
                refresh.run();
            }
        });
    }

    /**
     * Edit nurse.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
    public static void editNurse(Nurse selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a nurse profile.");
            return;
        }
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/nurse-dialog.fxml", "Update Nurse", c -> ((NurseDialogController) c).setNurse(selection), c -> {
            if (((NurseDialogController) c).isSaved()) {
                logActionAsync("Updated Nurse profile: " + selection.getLastName());
                refresh.run();
            }
        });
    }

    /**
     * Delete nurse.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
    public static void deleteNurse(Nurse selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a nurse profile.");
            return;
        }
        if (ConfirmationBox.show("Erase Record", "Remove nurse profile: " + selection.getFirstName() + "?")) {
            executeAsync(() -> nurseRepo.deleteById(Long.valueOf(selection.getId())), "Removed Nurse: " + selection.getLastName(), refresh);
        }
    }


    /**
     * Add patient.
     *
     * @param refresh the refresh
     */
    public static void addPatient(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/patient-dialog.fxml", "Admit Patient", null, c -> {
            if (((PatientDialogController) c).isOperationSaved()) {
                logActionAsync("Admitted a new Patient record");
                refresh.run();
            }
        });
    }

    /**
     * Edit patient.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
    public static void editPatient(Patient selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a patient row entry.");
            return;
        }
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/patient-dialog.fxml", "Modify Patient", c -> ((PatientDialogController) c).setPatientToEdit(selection), c -> {
            if (((PatientDialogController) c).isOperationSaved()) {
                logActionAsync("Modified Patient record: " + selection.getLastName());
                refresh.run();
            }
        });
    }

    /**
     * Delete patient.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
    public static void deletePatient(Patient selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a patient to discharge.");
            return;
        }
        if (ConfirmationBox.show("Discharge Case", "Clear patient profile: " + selection.getFirstName() + "?")) {
            executeAsync(() -> patientRepo.deleteById(Long.valueOf(selection.getId())), "Discharged Patient: " + selection.getFirstName(), refresh);
        }
    }

    /**
     * Add prescription.
     *
     * @param refresh the refresh
     */
    public static void addPrescription(Runnable refresh) {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Issue Prescription", null, c -> {
            if (((PrescriptionDialogController) c).isSaved()) {
                logActionAsync("Issued a new medication prescription");
                refresh.run();
            }
        });
    }

    /**
     * Edit prescription.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
    public static void editPrescription(Prescription selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a prescription item row.");
            return;
        }
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Modify Orders", c -> ((PrescriptionDialogController) c).setPrescription(selection), c -> {
            if (((PrescriptionDialogController) c).isSaved()) {
                logActionAsync("Modified Prescription ID: " + selection.getId());
                refresh.run();
            }
        });
    }

    /**
     * Delete prescription.
     *
     * @param selection the selection
     * @param refresh   the refresh
     */
    public static void deletePrescription(Prescription selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Missing Selection", "Select a prescription ledger target.");
            return;
        }
        if (ConfirmationBox.show("Revoke Script", "Erase completely medication record ID: " + selection.getId() + "?")) {
            executeAsync(() -> prescriptionRepo.deleteById(selection.getId()), "Revoked prescription sequence ID: " + selection.getId(), refresh);
        }
    }


    private static void logActionAsync(String activityLog) {
        Thread.startVirtualThread(() -> LogWriter.writeLogAsync(new Activity(activityLog, OPERATOR)));
    }

    private static void executeAsync(SqlRunnable action, String activityLog, Runnable completeCallback) {
        Thread.startVirtualThread(() -> {
            try {
                action.run();
                LogWriter.writeLogAsync(new Activity(activityLog, OPERATOR));
                Platform.runLater(completeCallback);
            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("Database Fault", e.getMessage()));
            }
        });
    }

    /**
     * The interface Sql runnable.
     */
    @FunctionalInterface
    public interface SqlRunnable {
        /**
         * Run.
         *
         * @throws SQLException the sql exception
         */
        void run() throws SQLException;
    }
}