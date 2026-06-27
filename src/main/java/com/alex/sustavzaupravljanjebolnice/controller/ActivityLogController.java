package com.alex.sustavzaupravljanjebolnice.controller;

import com.alex.sustavzaupravljanjebolnice.entity.Activity;
import com.alex.sustavzaupravljanjebolnice.util.LogReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Activity log controller.
 */
public class ActivityLogController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final ObservableList<Activity> activityList = FXCollections.observableArrayList();

    private Map<String, Activity> activityMap = Map.of();

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
        configureColumns();
        activityTable.setItems(activityList);

        loadLogs();
    }

    private void configureColumns() {

        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMadeOn().format(FORMATTER)));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesc()));
        madeByColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMadeBy()));
    }

    @FXML
    private void handleRefresh() {
        loadLogs();
    }

    private void loadLogs() {

        List<Activity> logs = LogReader.readLogsFromFile();
        Set<String> uniqueKeys = logs.stream().map(this::buildActivityKey).collect(Collectors.toSet());
        activityMap = logs.stream().collect(Collectors.toMap(this::buildActivityKey, activity -> activity, (existing, replacement) -> existing));
        List<Activity> sortedLogs = uniqueKeys.stream().map(activityMap::get).sorted(Comparator.comparing(Activity::getMadeOn).reversed()).toList();

        activityList.setAll(sortedLogs);
    }

    private String buildActivityKey(Activity activity) {

        return activity.getMadeOn() + "|" + activity.getMadeBy() + "|" + activity.getDesc();
    }
}