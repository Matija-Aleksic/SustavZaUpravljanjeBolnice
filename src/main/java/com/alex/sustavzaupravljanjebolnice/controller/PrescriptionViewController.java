package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.controller.popup.PrescriptionDialogController;
import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Prescription;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PrescriptionRepo;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.WindowManager;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import com.alex.sustavzaupravljanjebolnice.util.boxes.ConfirmationBox;
import com.alex.sustavzaupravljanjebolnice.util.boxes.InfoBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * The type Prescription view controller.
 */
public class PrescriptionViewController {
    private static final Logger log = LoggerFactory.getLogger(PrescriptionViewController.class);

    private final Staff loggedInStaff = UserSession.getInstance().getLoggedInStaff();
    private final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();
    private final PatientRepo patientRepo = new PatientRepo();

    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> patientNameColumn;
    @FXML
    private TableView<Prescription> prescriptionsTable;
    @FXML
    private TableColumn<Prescription, String> colId;
    @FXML
    private TableColumn<Prescription, String> colMedication;
    @FXML
    private TableColumn<Prescription, String> colPatient;
    @FXML
    private TableColumn<Prescription, String> colStartDate;
    @FXML
    private TableColumn<Prescription, String> colEndDate;
    @FXML
    private TableColumn<Prescription, String> colDescription;
    @FXML
    private CheckBox chkShowAllPatients;

    private List<Patient> hospitalPatients = List.of();
    private List<Prescription> allPrescriptions = List.of();

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        patientNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirstName() + " " + c.getValue().getLastName()));

        colId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getId()));
        colMedication.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colStartDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStartDate() != null ? c.getValue().getStartDate().toString() : ""));
        colEndDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEndDate() != null ? c.getValue().getEndDate().toString() : ""));
        colDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));

        colPatient.setCellValueFactory(c -> {
            long pId = c.getValue().getPatientId();
            String name = hospitalPatients.stream().filter(p -> p.getId() == pId).map(p -> p.getFirstName() + " " + p.getLastName()).findFirst().orElse("Patient #" + pId);
            return new SimpleStringProperty(name);
        });

        patientsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                chkShowAllPatients.setSelected(false);
                applyPrescriptionFilter();
            }
        });

        chkShowAllPatients.selectedProperty().addListener((obs, wasAll, isAll) -> {
            if (Boolean.TRUE.equals(isAll)) {
                patientsTable.getSelectionModel().clearSelection();
            }
            applyPrescriptionFilter();
        });

        reload();
    }

    private void loadData() throws SQLException {
        if (loggedInStaff == null || loggedInStaff.getHospital() == null) {
            log.warn("Unauthorized data load attempt or context lacking hospital association.");
            return;
        }

        long currentHospitalId = loggedInStaff.getHospital().getId();
        hospitalPatients = patientRepo.getAll().stream().filter(p -> p.getHospital() != null && Objects.equals(p.getHospital().getId(), currentHospitalId)).toList();
        List<Long> validPatientIds = hospitalPatients.stream().map(p -> (long) p.getId()).toList();
        allPrescriptions = prescriptionRepo.getAll().stream().filter(pr -> validPatientIds.contains((long) pr.getPatientId())).toList();
        Platform.runLater(() -> {
            patientsTable.setItems(FXCollections.observableArrayList(hospitalPatients));
            applyPrescriptionFilter();
        });
    }

    private void applyPrescriptionFilter() {
        if (chkShowAllPatients.isSelected()) {
            prescriptionsTable.setItems(FXCollections.observableArrayList(allPrescriptions));
        } else {
            Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                List<Prescription> filtered = allPrescriptions.stream().filter(p -> Objects.equals(p.getPatientId(), selectedPatient.getId())).toList();
                prescriptionsTable.setItems(FXCollections.observableArrayList(filtered));
            } else {
                prescriptionsTable.getItems().clear();
            }
        }
    }

    @FXML
    private void handleAddPrescription() {
        WindowManager.showModal("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Issue Prescription", null, c -> {
            if (((PrescriptionDialogController) c).isSaved()) reload();
        });
    }

    @FXML
    private void handleEditPrescription() {
        Prescription selected = prescriptionsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.show("Selection Missing", "Please choose a prescription record to modify.");
            return;
        }

        WindowManager.<PrescriptionDialogController>showModal("/com/alex/sustavzaupravljanjebolnice/popup/prescription-dialog.fxml", "Update Prescription Order", c -> c.setPrescription(selected), c -> {
            if (c.isSaved()) reload();
        });
    }

    @FXML
    private void handleDeletePrescription() {
        Prescription selected = prescriptionsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.show("Selection Missing", "Please pinpoint a prescription target to revoke.");
            return;
        }

        boolean confirmed = ConfirmationBox.show("Revocation Order", "Are you sure you want to completely erase prescription: " + selected.getId() + "?");
        if (!confirmed) return;

        Thread.startVirtualThread(() -> {
            try {
                prescriptionRepo.deleteById(selected.getId());
                log.info("Discarded prescription key ledger reference entry ID: {}", selected.getId());
                Platform.runLater(() -> {
                    reload();
                    InfoBox.show("Prescription Revoked Successfully");
                });
            } catch (SQLException e) {
                log.error("Failed handling database extraction processing task", e);
                Platform.runLater(() -> AlertBox.show("Database Failure", e.getMessage()));
            }
        });
    }

    private void reload() {
        Thread.startVirtualThread(() -> {
            try {
                loadData();
            } catch (SQLException e) {
                log.error("Asynchronous processing exception encountered during reload initialization execution data chain link", e);
                Platform.runLater(() -> AlertBox.show("Sync Error", e.getMessage()));
            }
        });
    }
}