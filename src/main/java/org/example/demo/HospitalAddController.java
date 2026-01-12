package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DataManager;
import util.DialogUtils;
import util.XmlLogger;
import entity.Hospital;
import java.util.List;

public class HospitalAddController {
    @FXML private TextField nameField;

    @FXML
    protected void onSave() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            DialogUtils.showError("Validation Error", "Name is required.");
            return;
        }
        try {
            int id = getNextHospitalId();
            Hospital hospital = new Hospital(id, name);
            DataManager.addHospital(hospital);
            XmlLogger.logAction("ADD_HOSPITAL", "Added hospital: " + name);
            DialogUtils.showInfo("Success", "Hospital added successfully.");
            closeWindow();
        } catch (Exception e) {
            DialogUtils.showError("Error", "Failed to add hospital: " + e.getMessage());
        }
    }

    private int getNextHospitalId() {
        List<Hospital> hospitals = DataManager.loadAllData().hospitals();
        return hospitals.stream().mapToInt(Hospital::getId).max().orElse(0) + 1;
    }

    @FXML
    protected void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
