package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DoctorDialogController {

    private final DoctorRepo doctorRepo = new DoctorRepo();
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
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    private Doctor existingDoctor = null;
    private boolean operationSaved = false;

    public void setDoctorToEdit(Doctor doctor) {
        this.existingDoctor = doctor;
        txtFirstName.setText(doctor.getFirstName());
        txtLastName.setText(doctor.getLastName());
        txtOib.setText(doctor.getOib());
        txtEmail.setText(doctor.getEmail());
        txtSalary.setText(String.valueOf(doctor.getSalary()));
        txtPhone.setText(doctor.getPhoneNumber());
        txtAddress.setText(doctor.getAddress());
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if (isInputInvalid()) {
            showAlert("Validation Missing", "Please complete all fields with accurate data formats.");
            return;
        }

        try {
            String fName = txtFirstName.getText().trim();
            String lName = txtLastName.getText().trim();
            String oibNum = txtOib.getText().trim();
            String emailAddr = txtEmail.getText().trim();
            double salaryVal = Double.parseDouble(txtSalary.getText().trim());
            String phoneNum = txtPhone.getText().trim();
            String location = txtAddress.getText().trim();

            if (existingDoctor == null) {
                // Doctor freshDoctor = new Doctor(0, fName, lName, oibNum, emailAddr, salaryVal, phoneNum, location);
                //  doctorRepo.save(freshDoctor);
            } else {
                existingDoctor.setFirstName(fName);
                existingDoctor.setLastName(lName);
                existingDoctor.setOib(oibNum);
                existingDoctor.setEmail(emailAddr);
                existingDoctor.setSalary(salaryVal);
                existingDoctor.setPhoneNumber(phoneNum);
                existingDoctor.setAddress(location);
                doctorRepo.update(existingDoctor);
            }

            operationSaved = true;
            closeWindow();

        } catch (NumberFormatException exc) {
            showAlert("Input Format Misalignment", "Salary input must match numeric parameters (e.g., 4500.50).");
        } catch (SQLException exc) {
            showAlert("Persistence Database Exception", exc.getMessage());
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
                txtOib.getText().isBlank() || txtEmail.getText().isBlank() || txtSalary.getText().isBlank();
    }

    private void showAlert(String header, String message) {
        Alert box = new Alert(Alert.AlertType.ERROR);
        box.setTitle(header);
        box.setContentText(message);
        box.showAndWait();
    }

    private void closeWindow() {
        Stage win = (Stage) txtFirstName.getScene().getWindow();
        win.close();
    }
}