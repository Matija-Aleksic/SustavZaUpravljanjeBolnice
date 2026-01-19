package org.example.demo.doctor;

import entity.Doctor;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DataManager;
import util.DialogUtils;
import util.XmlLogger;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Doctor add controller.
 */
public class DoctorAddController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField specializationField;
    @FXML
    private TextField baseSalaryField;
    @FXML
    private DatePicker dateOfBirthPicker;

    /**
     * On save.
     */
    @FXML
    protected void onSave() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String specialization = specializationField.getText();
        String baseSalaryStr = baseSalaryField.getText();
        LocalDate dob = dateOfBirthPicker.getValue();
        if (firstName.isEmpty() || lastName.isEmpty() || specialization.isEmpty() || baseSalaryStr.isEmpty() || dob == null) {
            DialogUtils.showError("Validation Error", "All fields are required.");
            return;
        }
        try {
            int id = getNextDoctorId();
            double baseSalary = Double.parseDouble(baseSalaryStr);
            Doctor doctor = new Doctor(id, firstName, lastName, dob, specialization, baseSalary);
            DataManager.addDoctor(doctor);
            XmlLogger.logAction("ADD_DOCTOR", "Added doctor: " + firstName + " " + lastName);
            DialogUtils.showInfo("Success", "Doctor added successfully.");
            closeWindow();
        } catch (NumberFormatException _) {
            DialogUtils.showError("Validation Error", "Base salary must be a number.");
        } catch (Exception e) {
            DialogUtils.showError("Error", "Failed to add doctor: " + e.getMessage());
        }
    }

    private int getNextDoctorId() {
        List<Doctor> doctors = DataManager.loadAllData().doctors();
        return doctors.stream().mapToInt(Doctor::getId).max().orElse(0) + 1;
    }

    /**
     * On cancel.
     */
    @FXML
    protected void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }
}
