package com.alex.sustavzaupravljanjebolnice.util;

import javafx.scene.control.Alert;

public class AlertBox<K, V> {

    private AlertBox(K title, V content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(String.valueOf(title));
        alert.setHeaderText(null);
        alert.setContentText(String.valueOf(content));
        alert.showAndWait();
    }

    public static <K, V> void show(K title, V content) {
        new AlertBox<>(title, content);
    }
}