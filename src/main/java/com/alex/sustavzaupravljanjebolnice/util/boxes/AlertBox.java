package com.alex.sustavzaupravljanjebolnice.util.boxes;

import javafx.scene.control.Alert;

/**
 * The type Alert box.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public class AlertBox<K, V> {

    private AlertBox(K title, V content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(String.valueOf(title));
        alert.setHeaderText(null);
        alert.setContentText(String.valueOf(content));
        alert.showAndWait();
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
        new AlertBox<>(title, content);
    }
}