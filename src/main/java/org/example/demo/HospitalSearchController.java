package org.example.demo;

import entity.Hospital;
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
 * Controller for hospital search screen
 */
public class HospitalSearchController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private TableView<Hospital> hospitalTable;

    @FXML
    private TableColumn<Hospital, Integer> idColumn;

    @FXML
    private TableColumn<Hospital, String> nameColumn;

    private List<Hospital> allHospitals;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadData();
        XmlLogger.logAction("HOSPITAL_SEARCH_OPENED", "Hospital search screen opened");
    }

    @FXML
    protected void onSearch() {
        try {
            String nameQuery = nameField.getText().trim().toLowerCase();
            String idQuery = idField.getText().trim();

            List<Hospital> filtered = allHospitals.stream()
                .filter(h -> nameQuery.isEmpty() || h.getName().toLowerCase().contains(nameQuery))
                .filter(h -> idQuery.isEmpty() || String.valueOf(h.getId()).equals(idQuery))
                .toList();

            hospitalTable.setItems(FXCollections.observableArrayList(filtered));
            XmlLogger.logAction("HOSPITAL_SEARCH",
                String.format("Searched hospitals: name=%s, id=%s, found=%d",
                    nameQuery, idQuery, filtered.size()));

            if (filtered.isEmpty()) {
                DialogUtils.showInfo("Search Results", "No hospitals found matching the criteria");
            }
        } catch (Exception e) {
            DialogUtils.showError("Search Error", "An error occurred during search: " + e.getMessage());
        }
    }

    @FXML
    protected void onClear() {
        nameField.clear();
        idField.clear();
        hospitalTable.setItems(FXCollections.observableArrayList(allHospitals));
        XmlLogger.logAction("HOSPITAL_SEARCH_CLEARED", "Search criteria cleared");
    }

    private void loadData() {
        DataManager.AllData data = DataManager.loadAllData();
        allHospitals = data.hospitals();
        hospitalTable.setItems(FXCollections.observableArrayList(allHospitals));
    }
}

