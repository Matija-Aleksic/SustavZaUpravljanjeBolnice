package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class DoctorOverviewController {

    @FXML
    private TableView<Doctor> doctorsTable;
    @FXML
    private TableColumn<Doctor, String> firstNameColumn;
    @FXML
    private TableColumn<Doctor, String> lastNameColumn;
    @FXML
    private TableColumn<Doctor, String> oibColumn;
    @FXML
    private TableColumn<Doctor, LocalDate> birthDateColumn;
    @FXML
    private TableColumn<Doctor, String> emailColumn;

    private ObservableList<Doctor> masterData = FXCollections.observableArrayList();

    public DoctorOverviewController() {
        // Sample Data
        masterData.add(new Doctor("John", "Doe", "12345678901", LocalDate.of(1980, 5, 15), "john.doe@example.com", null, null));
        masterData.add(new Doctor("Jane", "Smith", "09876543210", LocalDate.of(1975, 10, 20), "jane.smith@example.com", null, null));
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        oibColumn.setCellValueFactory(new PropertyValueFactory<>("oib"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        doctorsTable.setItems(masterData);
    }

    @FXML
    private void handleAddDoctor() {
        System.out.println("Add Doctor button clicked");
        // Logic to open Add Doctor popup
    }

    @FXML
    private void handleEditDoctor() {
        System.out.println("Edit Doctor button clicked");
        // Logic to open Edit Doctor popup with selected doctor's data
    }

    @FXML
    private void handleDeleteDoctor() {
        System.out.println("Delete Doctor button clicked");
        // Logic to delete selected doctor
    }
}