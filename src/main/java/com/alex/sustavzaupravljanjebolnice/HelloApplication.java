package com.alex.sustavzaupravljanjebolnice;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.db.H2Server;
import com.alex.sustavzaupravljanjebolnice.util.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Hello application.
 */
public class HelloApplication extends Application {

    private final Logger logger = Logger.getLogger(HelloApplication.class.getName());

    @Override
    public void start(Stage stage) {
        H2Server.start();

        try (Connection conn = DatabaseManager.getConnection()) {
            logger.info("Connected to database.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        WindowManager.switchScene(stage, "/com/alex/sustavzaupravljanjebolnice/login.fxml", "Hello!", 1300, 940);
    }
}