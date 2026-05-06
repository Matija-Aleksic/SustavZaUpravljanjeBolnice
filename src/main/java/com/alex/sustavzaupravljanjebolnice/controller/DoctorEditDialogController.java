package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class DoctorEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField oibField;
    @FXML
    private DatePicker birthDateField;
    @FXML
    private TextField emailField;

    private Stage dialogStage;
    private Doctor doctor;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;

        firstNameField.setText(doctor.getFirstName());
        lastNameField.setText(doctor.getLastName());
        oibField.setText(doctor.getOib());
        birthDateField.setValue(doctor.getBirthDate());
        emailField.setText(doctor.getEmail());
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            doctor.setFirstName(firstNameField.getText());
            doctor.setLastName(lastNameField.getText());
            doctor.setOib(oibField.getText());
            doctor.setBirthDate(birthDateField.getValue());
            doctor.setEmail(emailField.getText());

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (oibField.getText() == null || oibField.getText().length() == 0) {
            errorMessage += "No valid OIB!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            System.out.println(errorMessage);
            return false;
        }
    }
}
