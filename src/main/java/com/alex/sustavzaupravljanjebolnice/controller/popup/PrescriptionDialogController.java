package com.alex.sustavzaupravljanjebolnice.controller.popup;

import com.alex.sustavzaupravljanjebolnice.entity.hospital.Prescription;
import com.alex.sustavzaupravljanjebolnice.repository.PrescriptionRepo;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class PrescriptionDialogController {

    private final PrescriptionRepo prescriptionRepo = new PrescriptionRepo();
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtDoctorId;
    @FXML
    private TextField txtPatientId;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    private Prescription existingPrescription = null;
    private boolean operationSaved = false;

    public void setPrescriptionToEdit(Prescription prescription) {
        this.existingPrescription = prescription;

        // Disable editing the Primary Key (ID) when modifying records
        txtId.setText(prescription.getId());
        txtId.setDisable(true);

        txtName.setText(prescription.getName());
        txtDescription.setText(prescription.getDescription());
        txtDoctorId.setText(String.valueOf(prescription.getDoctorId()));
        txtPatientId.setText(String.valueOf(prescription.getPatientId()));
        dpStartDate.setValue(prescription.getStartDate());
        dpEndDate.setValue(prescription.getEndDate());
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if (isInputInvalid()) {
            AlertBox.show("Validation Missing", "Please complete all mandatory fields accurately.");
            return;
        }

        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String description = txtDescription.getText().trim();
            Integer docId = Integer.parseInt(txtDoctorId.getText().trim());
            Integer patId = Integer.parseInt(txtPatientId.getText().trim());

            if (dpStartDate.getValue().isAfter(dpEndDate.getValue())) {
                AlertBox.show("Validation Error", "Prescription start date cannot fall after its expiration date.");
                return;
            }

            if (existingPrescription == null) {
                Prescription freshPrescription = new Prescription(id, name, description, docId, patId, dpStartDate.getValue(), dpEndDate.getValue());
                prescriptionRepo.save(freshPrescription);
            } else {
                existingPrescription.setName(name);
                existingPrescription.setDescription(description);
                existingPrescription.setDoctorId(docId);
                existingPrescription.setPatientId(patId);
                existingPrescription.setStartDate(dpStartDate.getValue());
                existingPrescription.setEndDate(dpEndDate.getValue());
                prescriptionRepo.update(existingPrescription);
            }

            operationSaved = true;
            closeWindow();

        } catch (NumberFormatException _) {
            AlertBox.show("Input Format Misalignment", "Doctor ID and Patient ID must be written as valid whole numeric parameters.");
        } catch (SQLException _) {
            AlertBox.show("Persistence Database Exception", "coudnt do it");
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        closeWindow();
    }

    public boolean isOperationSaved() {
        return operationSaved;
    }

    private boolean isInputInvalid() {
        return txtId.getText().isBlank() || txtName.getText().isBlank() ||
                txtDoctorId.getText().isBlank() || txtPatientId.getText().isBlank() ||
                dpStartDate.getValue() == null || dpEndDate.getValue() == null;
    }

    private void closeWindow() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }
}