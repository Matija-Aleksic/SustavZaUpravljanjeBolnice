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

public class ActivityLogController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final ObservableList<Activity> activityList = FXCollections.observableArrayList();
    @FXML
    private TableView<Activity> activityTable;
    @FXML
    private TableColumn<Activity, String> dateColumn;
    @FXML
    private TableColumn<Activity, String> descriptionColumn;
    @FXML
    private TableColumn<Activity, String> madeByColumn;

    @FXML
    public void initialize() {

        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMadeOn().format(FORMATTER)));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesc()));
        madeByColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMadeBy()));

        loadLogs();
    }

    @FXML
    private void handleRefresh() {
        loadLogs();
    }

    private void loadLogs() {

        List<Activity> logs = LogReader.readLogsFromFile();
        logs.sort(Comparator.comparing(Activity::getMadeOn).reversed());
        activityList.clear();
        activityList.addAll(logs);

        activityTable.setItems(activityList);
    }
}