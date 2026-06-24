package com.alex.sustavzaupravljanjebolnice.util.boxes;

import javafx.scene.control.Alert;

/**
 * The type Info box utility.
 */
public final class InfoBox {

    private InfoBox() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Show an information alert box.
     *
     * @param <T>     the type parameter of the message
     * @param message the message to display
     */
    public static <T> void show(T message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message != null ? message.toString() : "null");
        alert.showAndWait();
    }
}