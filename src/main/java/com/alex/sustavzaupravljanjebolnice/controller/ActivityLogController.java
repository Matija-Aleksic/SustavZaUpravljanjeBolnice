package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Activity;
import com.alex.sustavzaupravljanjebolnice.util.LogReader;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The type Activity log controller.
 */
public class ActivityLogController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(ActivityLogController.class);

    private final ObservableList<Activity> activityList = FXCollections.observableArrayList();

    @FXML
    private TableView<Activity> activityTable;

    @FXML
    private TableColumn<Activity, String> dateColumn;

    @FXML
    private TableColumn<Activity, String> descriptionColumn;

    @FXML
    private TableColumn<Activity, String> madeByColumn;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMadeOn().format(FORMATTER)));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesc()));
        madeByColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMadeBy()));
        activityTable.setItems(activityList);

        loadLogs();
    }

    @FXML
    private void handleRefresh() {
        loadLogs();
    }

    private void loadLogs() {

        Thread.startVirtualThread(() -> {
            try {
                List<Activity> logs = LogReader.readLogsFromFile();

                Set<String> uniqueKeys = new HashSet<>();
                for (Activity lo : logs) {
                    String key = lo.getMadeOn() + "|" + lo.getMadeBy() + "|" + lo.getDesc();
                    uniqueKeys.add(key);
                }

                Map<String, Activity> activityMap = new HashMap<>();
                for (Activity lo : logs) {
                    String key = lo.getMadeOn() + "|" + lo.getMadeBy() + "|" + lo.getDesc();
                    if (!activityMap.containsKey(key)) {
                        activityMap.put(key, lo);
                    }
                }

                List<Activity> sortedLogs = new ArrayList<>();
                for (String key : uniqueKeys) {
                    Activity uniqueLog = activityMap.get(key);
                    if (uniqueLog != null) {
                        sortedLogs.add(uniqueLog);
                    }
                }

                sortedLogs.sort((log1, log2) -> log2.getMadeOn().compareTo(log1.getMadeOn()));
                Platform.runLater(() -> activityList.setAll(sortedLogs));

            } catch (Exception e) {
                log.error("Error processing logs in background thread: {}", e.getMessage());
            }
        });
    }

}