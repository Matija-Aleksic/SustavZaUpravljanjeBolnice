package com.alex.sustavzaupravljanjebolnice.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * The type Confirmation box.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public class ConfirmationBox<K, V> {

    private final boolean confirmed;

    private ConfirmationBox(K title, V content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.valueOf(content), ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation Required");
        alert.setHeaderText(String.valueOf(title));
        this.confirmed = alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    /**
     * Show boolean.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param title   the title/header
     * @param content the content text
     * @return true if the user clicked YES, false otherwise
     */
    public static <K, V> boolean show(K title, V content) {
        return new ConfirmationBox<>(title, content).isConfirmed();
    }

    private boolean isConfirmed() {
        return confirmed;
    }
}