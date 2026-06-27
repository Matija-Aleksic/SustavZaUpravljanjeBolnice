package com.alex.sustavzaupravljanjebolnice.controller.popup;

import com.alex.sustavzaupravljanjebolnice.entity.Activity;
import com.alex.sustavzaupravljanjebolnice.entity.StaffRoles;
import com.alex.sustavzaupravljanjebolnice.entity.builders.NurseBuilder;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Nurse;
import com.alex.sustavzaupravljanjebolnice.entity.staff.Staff;
import com.alex.sustavzaupravljanjebolnice.repository.NurseRepo;
import com.alex.sustavzaupravljanjebolnice.util.LogWriter;
import com.alex.sustavzaupravljanjebolnice.util.UserSession;
import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * The type Nurse dialog controller.
 */
public class NurseDialogController {

    private final NurseRepo nurseRepo = new NurseRepo();
    private final Staff loggedInStaff = UserSession.getInstance().getLoggedInStaff();
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
    private DatePicker dpBirthDate;
    private Integer currentNurseId = null;
    private Nurse existingNurse = null;
    private boolean saved = false;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        // Initialization configuration logic if required
    }

    /**
     * Sets new nurse context.
     */
    public void setNewNurseContext() {
        this.currentNurseId = null;
        this.existingNurse = null;

        txtFirstName.clear();
        txtLastName.clear();
        txtOib.clear();
        txtEmail.clear();
        txtSalary.clear();
        dpBirthDate.setValue(null);
    }

    /**
     * Sets nurse.
     *
     * @param nurse the nurse
     */
    public void setNurse(Nurse nurse) {
        this.existingNurse = nurse;
        this.currentNurseId = nurse.getId();

        txtFirstName.setText(nurse.getFirstName());
        txtLastName.setText(nurse.getLastName());
        txtOib.setText(nurse.getOib());
        txtEmail.setText(nurse.getEmail());
        txtSalary.setText(String.valueOf(nurse.getSalary()));
        dpBirthDate.setValue(nurse.getBirthDate());
    }

    /**
     * Handle save.
     *
     * @param event the event
     */
    @FXML
    public void handleSave(ActionEvent event) {
        if (!validateForm()) return;

        try {
            double salaryVal = Double.parseDouble(txtSalary.getText().trim());

            Nurse nurse;
            if (existingNurse != null) {
                nurse = existingNurse;
            } else {
                NurseBuilder builder = new NurseBuilder();
                builder.setHospital(UserSession.getInstance().getLoggedInStaff().getHospital());
                builder.setRole(StaffRoles.NURSE);
                nurse = builder.build();
                if (currentNurseId != null) {
                    nurse.setId(currentNurseId);
                }
            }

            nurse.setFirstName(txtFirstName.getText().trim());
            nurse.setLastName(txtLastName.getText().trim());
            nurse.setOib(txtOib.getText().trim());
            nurse.setEmail(txtEmail.getText().trim());
            nurse.setSalary(salaryVal);
            nurse.setBirthDate(dpBirthDate.getValue());


            if (currentNurseId == null) {
                nurseRepo.save(nurse);
                LogWriter.writeLogAsync(new Activity("created nurese" + nurse.getFirstName() + nurse.getLastName(), loggedInStaff.getFirstName().concat(" ").concat(loggedInStaff.getLastName())));
            } else {
                nurseRepo.update(nurse);
                LogWriter.writeLogAsync(new Activity(nurse.getEmail(), loggedInStaff.getFirstName().concat(" ").concat(loggedInStaff.getLastName())));
            }
            saved = true;
            closeStage();

        } catch (NumberFormatException _) {
            AlertBox.show("Validation Format Error", "Salary field configuration demands numerical precision values.");
        } catch (SQLException e) {
            AlertBox.show("Persistence Fail", "Database engine rejected updating: " + e.getMessage());
        }
    }

    /**
     * Handle cancel.
     *
     * @param event the event
     */
    @FXML
    public void handleCancel(ActionEvent event) {
        closeStage();
    }

    /**
     * Is saved boolean.
     *
     * @return the boolean
     */
    public boolean isSaved() {
        return saved;
    }

    private boolean validateForm() {
        if (txtFirstName.getText().isBlank() ||
                txtLastName.getText().isBlank() ||
                txtOib.getText().isBlank() ||
                txtEmail.getText().isBlank() ||
                txtSalary.getText().isBlank() ||
                dpBirthDate.getValue() == null) {

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

