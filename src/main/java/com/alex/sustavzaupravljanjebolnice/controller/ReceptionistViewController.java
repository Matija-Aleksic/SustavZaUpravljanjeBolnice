package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Patient;
import com.alex.sustavzaupravljanjebolnice.entity.hospital.Appointment;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.AppointmentRepo;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.repository.PatientRepo;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceptionistViewController {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final AppointmentRepo appointmentRepo = new AppointmentRepo();
    private final PatientRepo patientRepo = new PatientRepo();
    private final DoctorRepo doctorRepo = new DoctorRepo();
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, String> colId;
    @FXML
    private TableColumn<Appointment, String> colDate;
    @FXML
    private TableColumn<Appointment, String> colDoctor;
    @FXML
    private TableColumn<Appointment, String> colPatient;
    @FXML
    private ComboBox<String> sortComboBox;
    private ObservableList<Appointment> appointmentList;
    private Map<Integer, Patient> patientMap;
    private Map<Integer, Doctor> doctorMap;

    @FXML
    public void initialize() {
        try {
            Staff sessionStaff = UserSession.getInstance().getLoggedInStaff();

            patientMap = patientRepo.getAll().stream().collect(Collectors.toMap(Patient::getId, p -> p));
            doctorMap = doctorRepo.getAll().stream().collect(Collectors.toMap(Doctor::getId, d -> d));

            setupTableColumns();
            setupSorting();
            refreshTable();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load data: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().id())));

        colDate.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().dateTime().format(formatter)));

        colDoctor.setCellValueFactory(data -> {
            Doctor d = doctorMap.get(data.getValue().doctorId());
            return new ReadOnlyStringWrapper(d != null ? d.getFirstName() + " " + d.getLastName() : "Unknown");
        });

        colPatient.setCellValueFactory(data -> {
            Patient p = patientMap.get(data.getValue().patientId());
            return new ReadOnlyStringWrapper(p != null ? p.getFirstName() + " " + p.getLastName() : "Unknown");
        });
    }

    private void setupSorting() {
        sortComboBox.setItems(FXCollections.observableArrayList("Date", "Doctor", "Patient"));
        sortComboBox.setOnAction(e -> {
            String choice = sortComboBox.getValue();
            appointmentsTable.getSortOrder().clear();
            if ("Doctor".equals(choice)) {
                appointmentsTable.getSortOrder().add(colDoctor);
            } else if ("Patient".equals(choice)) {
                appointmentsTable.getSortOrder().add(colPatient);
            } else if ("Date".equals(choice)) {
                appointmentsTable.getSortOrder().add(colDate);
            }
            appointmentsTable.sort();
        });
    }

    private void refreshTable() throws SQLException {
        appointmentList = FXCollections.observableArrayList(appointmentRepo.getAll());
        appointmentsTable.setItems(appointmentList);
    }

    @FXML
    public void handleAddAppointment(ActionEvent event) {
        openAppointmentDialog(null);
    }

    @FXML
    public void handleEditAppointment(ActionEvent event) {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an appointment to edit.");
            return;
        }
        openAppointmentDialog(selected);
    }

    @FXML
    public void handleDeleteAppointment(ActionEvent event) {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an appointment to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            try {
                appointmentRepo.deleteById((long) selected.id());
                refreshTable();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Delete Failed", e.getMessage());
            }
        }
    }

    private void openAppointmentDialog(Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/alex/sustavzaupravljanjebolnice/appointment-dialog.fxml"));
            Parent root = loader.load();

            AppointmentDialogController controller = loader.getController();
            controller.setMaps(doctorMap, patientMap);
            if (appointment != null) {
                controller.setAppointment(appointment);
            }

            Stage stage = new Stage();
            stage.setTitle(appointment == null ? "New Appointment" : "Edit Appointment");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                refreshTable();
            }

        } catch (IOException | SQLException e) {
            showAlert(Alert.AlertType.ERROR, "UI Error", "Could not load dialog: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}