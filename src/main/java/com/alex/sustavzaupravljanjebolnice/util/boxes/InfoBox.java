package com.alex.sustavzaupravljanjebolnice.util.boxes;

import javafx.scene.control.Alert;

/**
 * The type Info box.
 */
public final class InfoBox {

    private InfoBox() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Show.
     *
     * @param <T>     the type parameter
     * @param message the message
     */
    public static <T> void show(T message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message != null ? message.toString() : "null");
        alert.showAndWait();
    }
}