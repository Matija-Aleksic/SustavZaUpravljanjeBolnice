package com.alex.sustavzaupravljanjebolnice.util;

import com.alex.sustavzaupravljanjebolnice.util.boxes.AlertBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Utility class to manage scene transitions and modal dialogs.
 */
public class WindowManager {

    /**
     * Switches the main scene on a given stage.
     */
    public static void switchScene(Stage stage, String fxmlPath, String title, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AlertBox.show("System Error", "Could not load scene: " + e.getMessage());
        }
    }

    private WindowManager() {
    }
    /**
     * Opens a modal dialog, allowing data injection before showing, and data retrieval after closing.
     *
     * @param fxmlPath The absolute path to the FXML file.
     * @param title    The window title.
     * @param onInit   Executes before showAndWait() - used to pass data TO the controller.
     * @param onClose  Executes after showAndWait() - used to read data FROM the controller.
     * @param <C>      The type of the Controller.
     */
    public static <C> void showModal(String fxmlPath, String title, Consumer<C> onInit, Consumer<C> onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            C controller = loader.getController();

            if (onInit != null) {
                onInit.accept(controller);
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (onClose != null) {
                onClose.accept(controller);
            }

        } catch (Exception e) {
            AlertBox.show("System Error", "Could not load dialog window: " + e.getMessage());
        }
    }
}