package com.alex.sustavzaupravljanjebolnice.controller.popup;

import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Doctor;
import com.alex.sustavzaupravljanjebolnice.repository.DoctorRepo;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

public class DoctorDialogController {

    private final DoctorRepo doctorRepo = new DoctorRepo();

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtOib;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSalary;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;

    private Integer currentDoctorId = null;
    private Doctor existingDoctor = null;
    private boolean saved = false;

    @FXML
    public void initialize() {
        //intellij complains if not commented
    }

    public void setNewDoctorContext() {
        this.currentDoctorId = null;
        this.existingDoctor = null;

        txtFirstName.clear();
        txtLastName.clear();
        txtOib.clear();
        txtEmail.clear();
        txtSalary.clear();
        txtPhone.clear();
        txtAddress.clear();
    }

    public void setDoctor(Doctor doctor) {
        this.existingDoctor = doctor;
        this.currentDoctorId = doctor.getId();

        txtFirstName.setText(doctor.getFirstName());
        txtLastName.setText(doctor.getLastName());
        txtOib.setText(doctor.getOib());
        txtEmail.setText(doctor.getEmail());
        txtSalary.setText(String.valueOf(doctor.getSalary()));
        txtPhone.setText(doctor.getPhoneNumber());
        txtAddress.setText(doctor.getAddress());
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if (!validateForm()) return;

        try {
            double salaryVal = Double.parseDouble(txtSalary.getText().trim());

            Doctor doc;
            if (existingDoctor != null) {
                doc = existingDoctor;
            } else {
                doc = new Doctor();
                doc.setRole(StaffRoles.DOCTOR);
                doc.setBirthDate(LocalDate.of(1990, Month.JANUARY.getValue(), 1));
            }

            doc.setId(currentDoctorId == null ? 0 : currentDoctorId);
            doc.setFirstName(txtFirstName.getText().trim());
            doc.setLastName(txtLastName.getText().trim());
            doc.setOib(txtOib.getText().trim());
            doc.setEmail(txtEmail.getText().trim());
            doc.setSalary(salaryVal);
            doc.setPhoneNumber(txtPhone.getText().trim());
            doc.setAddress(txtAddress.getText().trim());

            if (existingDoctor == null) {
                doc.setHospital(UserSession.getInstance().getLoggedInStaff().getHospital());
            }

            if (currentDoctorId == null) {
                doctorRepo.save(doc);
            } else {
                doctorRepo.update(doc);
            }

            saved = true;
            closeStage();

        } catch (NumberFormatException _) {
            AlertBox.show("Validation Format Error", "Salary field configuration demands numerical precision values.");
        } catch (SQLException e) {
            AlertBox.show("Persistence Fail", "Database engine rejected updating: " + e.getMessage());
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        closeStage();
    }

    public boolean isSaved() {
        return saved;
    }

    private boolean validateForm() {
        if (txtFirstName.getText().isBlank() ||
                txtLastName.getText().isBlank() ||
                txtOib.getText().isBlank() ||
                txtEmail.getText().isBlank() ||
                txtSalary.getText().isBlank() ||
                txtPhone.getText().isBlank() ||
                txtAddress.getText().isBlank()) {

            AlertBox.show("Validation Failure", "Every data field configuration value must explicitly be set.");
            return false;
        }
        return true;
    }

    private void closeStage() {
        Stage targetStage = (Stage) txtFirstName.getScene().getWindow();
        targetStage.close();
    }
}