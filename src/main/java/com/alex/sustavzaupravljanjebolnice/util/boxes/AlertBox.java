package com.alex.sustavzaupravljanjebolnice.util.boxes;

import javafx.scene.control.Alert;

/**
 * The type Alert box.
 */
public final class AlertBox {

    private AlertBox() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Show.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param title   the title
     * @param content the content
     */
    public static <K, V> void show(K title, V content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title != null ? String.valueOf(title) : "Warning");
        alert.setHeaderText(null);
        alert.setContentText(content != null ? String.valueOf(content) : "");
        alert.showAndWait();
    }
}