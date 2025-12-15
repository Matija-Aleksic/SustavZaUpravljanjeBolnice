package org.example.demo;

import entity.Patient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.DataManager;
import util.DialogUtils;
import util.XmlLogger;

import java.util.List;


/**
 * Controller for patient search screen
 */
public class PatientSearchController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField conditionField;

    @FXML
    private TextField idField;

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, Integer> idColumn;

    @FXML
    private TableColumn<Patient, String> firstNameColumn;

    @FXML
    private TableColumn<Patient, String> lastNameColumn;

    @FXML
    private TableColumn<Patient, String> conditionColumn;

    @FXML
    private TableColumn<Patient, String> insuranceColumn;

    private List<Patient> allPatients;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        insuranceColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceNumber"));

        loadData();
        XmlLogger.logAction("PATIENT_SEARCH_OPENED", "Patient search screen opened");
    }

    @FXML
    protected void onSearch() {
        try {
            String nameQuery = nameField.getText().trim().toLowerCase();
            String condQuery = conditionField.getText().trim().toLowerCase();
            String idQuery = idField.getText().trim();

            List<Patient> filtered = allPatients.stream()
                .filter(p -> nameQuery.isEmpty() ||
                    p.getFirstName().toLowerCase().contains(nameQuery) ||
                    p.getFullName().toLowerCase().contains(nameQuery))
                .filter(p -> condQuery.isEmpty() ||
                    p.getCondition().toLowerCase().contains(condQuery))
                .filter(p -> idQuery.isEmpty() || String.valueOf(p.getId()).equals(idQuery))
                .toList();

            patientTable.setItems(FXCollections.observableArrayList(filtered));
            XmlLogger.logAction("PATIENT_SEARCH",
                String.format("Searched patients: name=%s, condition=%s, id=%s, found=%d",
                    nameQuery, condQuery, idQuery, filtered.size()));

            if (filtered.isEmpty()) {
                DialogUtils.showInfo("Search Results", "No patients found matching the criteria");
            }
        } catch (Exception e) {
            DialogUtils.showError("Search Error", "An error occurred during search: " + e.getMessage());
        }
    }

    @FXML
    protected void onClear() {
        nameField.clear();
        conditionField.clear();
        idField.clear();
        patientTable.setItems(FXCollections.observableArrayList(allPatients));
        XmlLogger.logAction("PATIENT_SEARCH_CLEARED", "Search criteria cleared");
    }

    private void loadData() {
        DataManager.AllData data = DataManager.loadAllData();
        allPatients = data.patients();
        patientTable.setItems(FXCollections.observableArrayList(allPatients));
    }
}

