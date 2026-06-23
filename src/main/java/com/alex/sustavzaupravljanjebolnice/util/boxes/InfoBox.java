package com.alex.sustavzaupravljanjebolnice.util.boxes;

import javafx.scene.control.Alert;

/**
 * The type Info box.
 *
 * @param <T> the type parameter
 */
public class InfoBox<T> {

    private InfoBox(T message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message.toString());
        alert.showAndWait();
    }

    /**
     * Show.
     *
     * @param <T>     the type parameter
     * @param message the message
     */
    public static <T> void show(T message) {
        new InfoBox<>(message);
    }
}