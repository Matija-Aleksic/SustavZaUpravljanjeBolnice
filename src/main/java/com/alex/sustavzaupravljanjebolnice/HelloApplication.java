package com.alex.sustavzaupravljanjebolnice;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import com.alex.sustavzaupravljanjebolnice.util.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * The type Hello application.
 */
public class HelloApplication extends Application {

    private final Logger logger = Logger.getLogger(HelloApplication.class.getName());

    @Override
    public void start(Stage stage) {
        try (var _ = DatabaseManager.getConnection()) {
            logger.info("Connected to database.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        WindowManager.switchScene(stage, "/com/alex/sustavzaupravljanjebolnice/login.fxml", "Hello!", 400, 600);
    }

    @Override
    public void stop() {
        logger.info("JavaFX UI has closed. Shutting down database engine...");

        try {
            logger.info("Database successfully stopped.");
        } catch (Exception _) {
            logger.log(Level.SEVERE, "Failed to clean stop H2 Server");
        }

        try {
            Path passwordsFile = Paths.get("passwords.properties");
            if (Files.exists(passwordsFile)) {
                Files.delete(passwordsFile);
            }

            Path logsDir = Paths.get("logs");
            if (Files.exists(logsDir)) {
                try (Stream<Path> walk = Files.walk(logsDir)) {
                    walk.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                }
            }

            logger.info("Cleanup completed successfully.");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to clean up files on exit: {}", e.getMessage());
        }
    }
}