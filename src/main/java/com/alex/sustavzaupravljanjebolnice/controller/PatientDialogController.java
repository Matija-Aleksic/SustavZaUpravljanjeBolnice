package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Ward;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.util.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class PatientDialogController {

    private final PatientRepo patientRepo = new PatientRepo();
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtOib;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtWardId;
    private Patient existingPatient = null;
    private boolean operationSaved = false;

    public void setPatientToEdit(Patient patient) {
        this.existingPatient = patient;
        txtFirstName.setText(patient.getFirstName());
        txtLastName.setText(patient.getLastName());
        txtOib.setText(patient.getOib());

        if (patient.getAssignedWard() != null && patient.getAssignedWard().getId() != null) {
            txtWardId.setText(String.valueOf(patient.getAssignedWard().getId()));
        }
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if (isInputInvalid()) {
            AlertBox.show("Validation Missing", "Please fulfill all required text fields.");
            return;
        }

        try {
            String fName = txtFirstName.getText().trim();
            String lName = txtLastName.getText().trim();
            String oibNum = txtOib.getText().trim();

            Long wardId = txtWardId.getText().isBlank() ? null : Long.parseLong(txtWardId.getText().trim());

            Ward assignedWard = null;
            if (wardId != null) {
                assignedWard = new Ward();
                assignedWard.setId(wardId);
            }

            if (existingPatient == null) {
                Patient freshPatient = new Patient();
                freshPatient.setFirstName(fName);
                freshPatient.setLastName(lName);
                freshPatient.setOib(oibNum);

                freshPatient.setAssignedWard(assignedWard);

                patientRepo.save(freshPatient);
            } else {
                existingPatient.setFirstName(fName);
                existingPatient.setLastName(lName);
                existingPatient.setOib(oibNum);
                existingPatient.setAssignedWard(assignedWard);

                patientRepo.update(existingPatient);
            }

            operationSaved = true;
            closeWindow();

        } catch (NumberFormatException exc) {
            AlertBox.show("Input Format Error", "Ward ID must be a valid numeric index.");
        } catch (SQLException exc) {
            AlertBox.show("Database Write Error", exc.getMessage());
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
        return txtFirstName.getText().isBlank() || txtLastName.getText().isBlank() || txtOib.getText().isBlank();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtFirstName.getScene().getWindow();
        stage.close();
    }
}