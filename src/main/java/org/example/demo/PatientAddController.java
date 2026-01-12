package org.example.demo;

import entity.ConditionStatus;
import entity.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DataManager;
import util.DialogUtils;
import util.XmlLogger;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Patient add controller.
 */
public class PatientAddController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox<ConditionStatus> conditionComboBox;
    @FXML
    private TextField insuranceField;
    @FXML
    private DatePicker dateOfBirthPicker;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        conditionComboBox.getItems().setAll(ConditionStatus.values());
    }

    /**
     * On save.
     */
    @FXML
    protected void onSave() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        ConditionStatus condition = conditionComboBox.getValue();
        String insurance = insuranceField.getText();
        LocalDate dob = dateOfBirthPicker.getValue();
        if (firstName.isEmpty() || lastName.isEmpty() || condition == null || insurance.isEmpty() || dob == null) {
            DialogUtils.showError("Validation Error", "All fields are required.");
            return;
        }
        try {
            int id = getNextPatientId();
            Patient patient = new Patient.Builder(id, firstName, lastName, dob)
                    .condition(condition)
                    .insuranceNumber(insurance)
                    .build();
            DataManager.addPatient(patient);
            XmlLogger.logAction("ADD_PATIENT", "Added patient: " + firstName + " " + lastName);
            DialogUtils.showInfo("Success", "Patient added successfully.");
            closeWindow();
        } catch (IllegalArgumentException _) {
            DialogUtils.showError("Validation Error", "Invalid condition or date format.");
        } catch (Exception e) {
            DialogUtils.showError("Error", "Failed to add patient: " + e.getMessage());
        }
    }

    private int getNextPatientId() {
        List<Patient> patients = DataManager.loadAllData().patients();
        return patients.stream().mapToInt(Patient::getId).max().orElse(0) + 1;
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
