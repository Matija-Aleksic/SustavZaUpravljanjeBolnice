package org.example.demo.doctor;

import entity.Doctor;
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
 * The type Doctor search controller.
 */
public class DoctorSearchController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField specializationField;

    @FXML
    private TextField idField;

    @FXML
    private TableView<Doctor> doctorTable;

    @FXML
    private TableColumn<Doctor, Integer> idColumn;

    @FXML
    private TableColumn<Doctor, String> firstNameColumn;

    @FXML
    private TableColumn<Doctor, String> lastNameColumn;

    @FXML
    private TableColumn<Doctor, String> specializationColumn;

    @FXML
    private TableColumn<Doctor, Double> baseSalaryColumn;

    private List<Doctor> allDoctors;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        baseSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));

        loadData();
        XmlLogger.logAction("DOCTOR_SEARCH_OPENED", "Doctor search screen opened");
    }

    /**
     * On search.
     */
    @FXML
    protected void onSearch() {
        try {
            String nameQuery = nameField.getText().trim().toLowerCase();
            String specQuery = specializationField.getText().trim().toLowerCase();
            String idQuery = idField.getText().trim();

            List<Doctor> filtered = allDoctors.stream()
                    .filter(d -> nameQuery.isEmpty() ||
                            d.getFirstName().toLowerCase().contains(nameQuery) ||
                            d.getFullName().toLowerCase().contains(nameQuery))
                    .filter(d -> specQuery.isEmpty() ||
                            d.getSpecialization().toLowerCase().contains(specQuery))
                    .filter(d -> idQuery.isEmpty() || String.valueOf(d.getId()).equals(idQuery))
                    .toList();

            doctorTable.setItems(FXCollections.observableArrayList(filtered));
            XmlLogger.logAction("DOCTOR_SEARCH",
                    String.format("Searched doctors: name=%s, spec=%s, id=%s, found=%d",
                            nameQuery, specQuery, idQuery, filtered.size()));

            if (filtered.isEmpty()) {
                DialogUtils.showInfo("Search Results", "No doctors found matching the criteria");
            }
        } catch (Exception e) {
            DialogUtils.showError("Search Error", "An error occurred during search: " + e.getMessage());
        }
    }

    /**
     * On clear.
     */
    @FXML
    protected void onClear() {
        nameField.clear();
        specializationField.clear();
        idField.clear();
        doctorTable.setItems(FXCollections.observableArrayList(allDoctors));
        XmlLogger.logAction("DOCTOR_SEARCH_CLEARED", "Search criteria cleared");
    }

    private void loadData() {
        DataManager.AllData data = DataManager.loadAllData();
        allDoctors = data.doctors();
        doctorTable.setItems(FXCollections.observableArrayList(allDoctors));
    }
}

