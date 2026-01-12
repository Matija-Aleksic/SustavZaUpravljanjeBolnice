package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The type Dialog utils.
 */
public class DialogUtils {

    private DialogUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Show error.
     *
     * @param title   the title
     * @param message the message
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        XmlLogger.logAction("ERROR_DIALOG", title + ": " + message);
    }

    /**
     * Show info.
     *
     * @param title   the title
     * @param message the message
     */
    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        XmlLogger.logAction("INFO_DIALOG", title + ": " + message);
    }

    /**
     * Show warning.
     *
     * @param title   the title
     * @param message the message
     */
    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        XmlLogger.logAction("WARNING_DIALOG", title + ": " + message);
    }

    /**
     * Show confirmation boolean.
     *
     * @param title   the title
     * @param message the message
     * @return the boolean
     */
    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        boolean confirmed = result.isPresent() && result.get() == ButtonType.OK;
        XmlLogger.logAction("CONFIRMATION_DIALOG", title + ": " + (confirmed ? "Confirmed" : "Cancelled"));
        return confirmed;
    }
}

