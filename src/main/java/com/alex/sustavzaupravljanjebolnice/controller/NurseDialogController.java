package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.builders.NurseBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Nurse;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.util.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NurseDialogController {

    private final NurseRepo nurseRepo = new NurseRepo();
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtOib;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSalary;
    @FXML
    private DatePicker dpBirthDate;
    private Nurse existingNurse = null;
    private boolean operationSaved = false;

    public void setNurseToEdit(Nurse nurse) {
        this.existingNurse = nurse;
        txtFirstName.setText(nurse.getFirstName());
        txtLastName.setText(nurse.getLastName());
        txtOib.setText(nurse.getOib());
        txtEmail.setText(nurse.getEmail());
        txtSalary.setText(String.valueOf(nurse.getSalary()));
        if (nurse.getBirthDate() != null) {
            dpBirthDate.setValue(nurse.getBirthDate());
        }
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if (isInputInvalid()) {
            AlertBox.show("Validation Missing", "Please populate all fields with valid data.");
            return;
        }

        try {
            String fName = txtFirstName.getText().trim();
            String lName = txtLastName.getText().trim();
            String oibNum = txtOib.getText().trim();
            String emailAddr = txtEmail.getText().trim();
            double salaryVal = Double.parseDouble(txtSalary.getText().trim());

            if (existingNurse == null) {
                NurseBuilder freshNurse = new NurseBuilder();
                freshNurse.setFirstName(fName);
                freshNurse.setLastName(lName);
                freshNurse.setOib(oibNum);
                freshNurse.setEmail(emailAddr);
                freshNurse.setSalary(salaryVal);
                freshNurse.setBirthDate(dpBirthDate.getValue());
                freshNurse.setRole(StaffRoles.NURSE);

                nurseRepo.save(freshNurse.build());
            } else {
                existingNurse.setFirstName(fName);
                existingNurse.setLastName(lName);
                existingNurse.setOib(oibNum);
                existingNurse.setEmail(emailAddr);
                existingNurse.setSalary(salaryVal);
                existingNurse.setBirthDate(dpBirthDate.getValue());

                nurseRepo.update(existingNurse);
            }

            operationSaved = true;
            closeWindow();

        } catch (NumberFormatException exc) {
            AlertBox.show("Input Format Error", "Salary must be numeric (e.g., 3200.00).");
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
        return txtFirstName.getText().isBlank() || txtLastName.getText().isBlank() ||
                txtOib.getText().isBlank() || txtEmail.getText().isBlank() ||
                txtSalary.getText().isBlank() || dpBirthDate.getValue() == null;
    }

    private void closeWindow() {
        Stage stage = (Stage) txtFirstName.getScene().getWindow();
        stage.close();
    }
}