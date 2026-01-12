package org.example.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.DialogUtils;
import util.XmlLogger;

import java.util.List;

/**
 * The type Logs controller.
 */
public class LogsController {
    @FXML
    private TableView<LogEntry> logsTable;
    @FXML
    private TableColumn<LogEntry, String> timestampColumn;
    @FXML
    private TableColumn<LogEntry, String> nameColumn;
    @FXML
    private TableColumn<LogEntry, String> descriptionColumn;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadLogs();
    }

    private void loadLogs() {
        List<LogEntry> logs = XmlLogger.getLogEntries();
        logsTable.setItems(FXCollections.observableArrayList(logs));
    }

    /**
     * On delete logs.
     */
    @FXML
    protected void onDeleteLogs() {
        XmlLogger.deleteLogs();
        loadLogs();
        DialogUtils.showInfo("Logs Deleted", "All logs have been deleted.");
    }

    /**
     * On close.
     */
    @FXML
    protected void onClose() {
        Stage stage = (Stage) logsTable.getScene().getWindow();
        stage.close();
    }

    /**
     * The type Log entry.
     */
    public record LogEntry(String timestamp, String name, String description) {
    }
}

