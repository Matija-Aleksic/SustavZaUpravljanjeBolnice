package com.alex.sustavzaupravljanjebolnice;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Hello application.
 */
public class HelloApplication extends Application {
    /**
     * The Logger.
     */
    Logger logger = Logger.getLogger(HelloApplication.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        try (Connection conn = DatabaseManager.getConnection()) {
            logger.info("Connected to database.");

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 940);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


}
