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

public class HospitalCrudHelper {

    private static final NurseRepo nurseRepo = new NurseRepo();
    private static final DoctorRepo doctorRepo = new DoctorRepo();
    private static final PatientRepo patientRepo = new PatientRepo();
    private static final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();
    private static final Staff currentStaff = UserSession.getInstance().getLoggedInStaff();
    private static final String OPERATOR = currentStaff.getFirstName() + " " + currentStaff.getLastName();
    private static final String MISSING_SELECTION = "Missing Selection";

    private HospitalCrudHelper() {
    }

    public static void addDoctor(Runnable refresh) {
        WindowManager.showPopup("/com/alex/sustavzaupravljanjebolnice/popup/doctor-dialog.fxml", "Add New Doctor Profile", DoctorDialogController::setNewDoctorContext, c -> {
            if (c.isSaved()) {
                LogWriter.writeLogAsync(new Activity("Added a new doctor", OPERATOR));
                refresh.run();
            }
        });
    }

    public static void editDoctor(Doctor selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Warning", "Please select a doctor to edit.");
            return;
        }
        WindowManager.<DoctorDialogController>showPopup("/com/alex/sustavzaupravljanjebolnice/popup/doctor-dialog.fxml", "Edit Doctor Profile", c -> c.setDoctor(selection), c -> {
            if (c.isSaved()) {
                LogWriter.writeLogAsync(new Activity("Modified doctor : " + selection.getLastName(), OPERATOR));
                refresh.run();
            }
        });
    }

    public static void deleteDoctor(Doctor selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show("Warning", "Please select record.");
            return;
        }

        if (!ConfirmationBox.show("Are you sure?", "Delete doctor: " + selection.getFirstName() + " " + selection.getLastName() + "?\nThis action cannot be undone.")) {
            return;
        }

        Thread.startVirtualThread(() -> {
            try {
                doctorRepo.deleteById((long) selection.getId());
                LogWriter.writeLogAsync(new Activity("Deleted Doctor Profile: " + selection.getLastName(), OPERATOR));
                Platform.runLater(() -> {
                    refresh.run();
                    InfoBox.show("Success");
                });
            } catch (SQLException e) {
                AlertBox.show("Database Fail", e.getMessage());
            }
        });
    }

    public static void addNurse(Runnable refresh) {
        WindowManager.showPopup("/com/alex/sustavzaupravljanjebolnice/popup/nurse-dialog.fxml", "Register Nurse", c -> ((NurseDialogController) c).setNewNurse(), c -> {
            if (((NurseDialogController) c).isSaved()) {
                LogWriter.writeLogAsync(new Activity("Registered a new nurse ", OPERATOR));
                refresh.run();
            }
        });
    }

    public static void editNurse(Nurse selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show(MISSING_SELECTION, "Select a nurse profile.");
            return;
        }
        WindowManager.showPopup("/com/alex/sustavzaupravljanjebolnice/popup/nurse-dialog.fxml", "Update Nurse", c -> ((NurseDialogController) c).setNurse(selection), c -> {
            if (((NurseDialogController) c).isSaved()) {
                LogWriter.writeLogAsync(new Activity("Updated Nurse: " + selection.getLastName(), OPERATOR));
                refresh.run();
            }
        });
    }

    public static void deleteNurse(Nurse selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show(MISSING_SELECTION, "Select a nurse profile.");
            return;
        }
        if (!ConfirmationBox.show("Erase Record", "Remove nurse : " + selection.getFirstName() + "?")) {
            return;
        }
        Thread.startVirtualThread(() -> {
            try {
                nurseRepo.deleteById(Long.valueOf(selection.getId()));
                LogWriter.writeLogAsync(new Activity("Removed Nurse: " + selection.getLastName(), OPERATOR));
                refresh.run();
            } catch (SQLException e) {
                AlertBox.show("Database fail", e.getMessage());
            }
        });
    }

    public static void addPatient(Runnable refresh) {
        WindowManager.showPopup("/com/alex/sustavzaupravljanjebolnice/popup/patient-dialog.fxml", "Admit Patient", null, c -> {
            if (((PatientDialogController) c).isSaved()) {
                LogWriter.writeLogAsync(new Activity("Admitted a new patient", OPERATOR));
                refresh.run();
            }
        });
    }

    public static void editPatient(Patient selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show(MISSING_SELECTION, "Select a patient row entry.");
            return;
        }
        WindowManager.showPopup("/com/alex/sustavzaupravljanjebolnice/popup/patient-dialog.fxml", "Modify Patient", c -> ((PatientDialogController) c).setPatientToEdit(selection), c -> {
            if (((PatientDialogController) c).isSaved()) {
                LogWriter.writeLogAsync(new Activity("Modified Patient record: " + selection.getLastName(), OPERATOR));
                refresh.run();
            }
        });
    }

    public static void deletePatient(Patient selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show(MISSING_SELECTION, "Select a patient to discharge.");
            return;
        }
        if (!ConfirmationBox.show("Discharge Case", "Clear patient profile: " + selection.getFirstName() + "?")) {
            return;
        }
        Thread.startVirtualThread(() -> {
            try {
                patientRepo.deleteById(Long.valueOf(selection.getId()));
                LogWriter.writeLogAsync(new Activity("Discharged Patient: " + selection.getFirstName(), OPERATOR));
                refresh.run();
            } catch (SQLException e) {
                Platform.runLater(() -> AlertBox.show("Database Fault", e.getMessage()));
            }
        });
    }

    public static void addPrescription(Runnable refresh) {
        WindowManager.showPopup("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Issue Prescription", null, c -> {
            if (((PrescriptionDialogController) c).isSaved()) {
                LogWriter.writeLogAsync(new Activity("Issued a new medication prescription", OPERATOR));
                refresh.run();
            }
        });
    }

    public static void editPrescription(Prescription selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show(MISSING_SELECTION, "Select a prescription item row.");
            return;
        }
        WindowManager.showPopup("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Modify Orders", c -> ((PrescriptionDialogController) c).setPrescription(selection), c -> {
            if (((PrescriptionDialogController) c).isSaved()) {
                LogWriter.writeLogAsync(new Activity("Modified Prescription ID: " + selection.getId(), OPERATOR));
                refresh.run();
            }
        });
    }

    public static void deletePrescription(Prescription selection, Runnable refresh) {
        if (selection == null) {
            AlertBox.show(MISSING_SELECTION, "Select a prescription ledger target.");
            return;
        }
        if (!ConfirmationBox.show("Revoke Script", "Erase completely medication record ID: " + selection.getId() + "?")) {
            return;
        }
        Thread.startVirtualThread(() -> {
            try {
                prescriptionRepo.deleteById(selection.getId());
                LogWriter.writeLogAsync(new Activity("Revoked prescription sequence ID: " + selection.getId(), OPERATOR));
                refresh.run();
            } catch (SQLException e) {
                AlertBox.show("Database Fault", e.getMessage());
            }
        });
    }
}