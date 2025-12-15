package org.example.demo;

import entity.Appointment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.DataManager;
import util.DialogUtils;
import util.ValidationUtils;
import util.XmlLogger;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for appointment search screen
 */
public class AppointmentSearchController {

    @FXML
    private TextField doctorNameField;

    @FXML
    private TextField patientNameField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField idField;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    @FXML
    private TableColumn<Appointment, String> doctorColumn;

    @FXML
    private TableColumn<Appointment, String> patientColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> dateTimeColumn;

    private List<Appointment> allAppointments;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        doctorColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().doctor().getFullName()));
        patientColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().patient().getFullName()));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        loadData();
        XmlLogger.logAction("APPOINTMENT_SEARCH_OPENED", "Appointment search screen opened");
    }

    @FXML
    protected void onSearch() {
        try {
            String doctorQuery = doctorNameField.getText().trim().toLowerCase();
            String patientQuery = patientNameField.getText().trim().toLowerCase();
            String dateQuery = dateField.getText().trim();
            String idQuery = idField.getText().trim();

            LocalDateTime searchDate = null;
            if (!dateQuery.isEmpty()) {
                    searchDate = ValidationUtils.parseDateTime(dateQuery, "yyyy-MM-dd HH:mm");
            }

            final LocalDateTime finalSearchDate = searchDate;
            List<Appointment> filtered = allAppointments.stream()
                .filter(a -> doctorQuery.isEmpty() ||
                    a.doctor().getFullName().toLowerCase().contains(doctorQuery))
                .filter(a -> patientQuery.isEmpty() ||
                    a.patient().getFullName().toLowerCase().contains(patientQuery))
                .filter(a -> finalSearchDate == null ||
                    a.dateTime().toLocalDate().equals(finalSearchDate.toLocalDate()))
                .filter(a -> idQuery.isEmpty() || String.valueOf(a.id()).equals(idQuery))
                .toList();

            appointmentTable.setItems(FXCollections.observableArrayList(filtered));
            XmlLogger.logAction("APPOINTMENT_SEARCH",
                String.format("Searched appointments: doctor=%s, patient=%s, date=%s, found=%d",
                    doctorQuery, patientQuery, dateQuery, filtered.size()));

            if (filtered.isEmpty()) {
                DialogUtils.showInfo("Search Results",
                    "No appointments found matching the criteria");
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            DialogUtils.showError("Search Error",
                "An error occurred during search: " + illegalArgumentException.getMessage());
        } catch (Exception _) {
        DialogUtils.showError("Invalid Date Format",
                "Please enter date in format: yyyy-MM-dd HH:mm (e.g., 2024-03-15 10:30)");
    }
    }

    @FXML
    protected void onClear() {
        doctorNameField.clear();
        patientNameField.clear();
        dateField.clear();
        idField.clear();
        appointmentTable.setItems(FXCollections.observableArrayList(allAppointments));
        XmlLogger.logAction("APPOINTMENT_SEARCH_CLEARED", "Search criteria cleared");
    }

    private void loadData() {
        DataManager.AllData data = DataManager.loadAllData();
        allAppointments = data.appointments();
        appointmentTable.setItems(FXCollections.observableArrayList(allAppointments));
    }
}

