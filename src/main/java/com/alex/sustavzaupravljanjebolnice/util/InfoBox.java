package com.alex.sustavzaupravljanjebolnice.util;

import javafx.scene.control.Alert;

public class InfoBox<T> {

    private InfoBox(T message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message.toString());
        alert.showAndWait();
    }

    public static <T> void show(T message) {
        new InfoBox<>(message);
    }
}