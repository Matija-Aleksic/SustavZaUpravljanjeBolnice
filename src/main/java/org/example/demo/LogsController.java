package org.example.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import util.DialogUtils;
import util.XmlLogger;

import java.util.List;
import java.util.Map;

/**
 * The type Logs controller.
 */
public class LogsController {
    @FXML
    private TableView<Map<String, String>> logsTable;
    @FXML
    private TableColumn<Map<String, String>, String> timestampColumn;
    @FXML
    private TableColumn<Map<String, String>, String> nameColumn;
    @FXML
    private TableColumn<Map<String, String>, String> descriptionColumn;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        timestampColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get("timestamp")));
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get("name")));
        descriptionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get("description")));
        loadLogs();
    }

    private void loadLogs() {
        List<Map<String, String>> logs = util.XmlLogger.getLogEntries();
        logsTable.setItems(FXCollections.observableArrayList(logs));
    }

    /**
     * On delete logs.
     */
    @FXML
    protected void onDeleteLogs() {
        util.XmlLogger.deleteLogs();
        loadLogs();
        util.DialogUtils.showInfo("Logs Deleted", "All logs have been deleted.");
    }

    /**
     * On close.
     */
    @FXML
    protected void onClose() {
        Stage stage = (Stage) logsTable.getScene().getWindow();
        stage.close();
    }
}
