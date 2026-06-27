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
 * The type Window manager.
 */
public class WindowManager {

    private WindowManager() {
    }

    /**
     * Switch scene.
     *
     * @param stage    the stage
     * @param fxmlPath the fxml path
     * @param title    the title
     * @param width    the width
     * @param height   the height
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

    /**
     * Show modal.
     *
     * @param <C>      the type parameter
     * @param fxmlPath the fxml path
     * @param title    the title
     * @param onInit   the on init
     * @param onClose  the on close
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