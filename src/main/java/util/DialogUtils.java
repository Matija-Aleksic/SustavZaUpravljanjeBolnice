package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utility class for displaying JavaFX dialogs
 */
public class DialogUtils {

    private DialogUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Show error dialog with message
     * @param title dialog title
     * @param message error message
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
     * Show info dialog with message
     * @param title dialog title
     * @param message info message
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
     * Show warning dialog with message
     * @param title dialog title
     * @param message warning message
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
     * Show confirmation dialog
     * @param title dialog title
     * @param message confirmation message
     * @return true if user confirmed
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

