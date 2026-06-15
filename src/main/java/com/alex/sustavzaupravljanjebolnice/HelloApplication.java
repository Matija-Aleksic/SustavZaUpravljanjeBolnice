package com.alex.sustavzaupravljanjebolnice;

import com.alex.sustavzaupravljanjebolnice.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

/**
 * The type Hello application.
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try (Connection conn = DatabaseManager.getConnection()) {
            System.out.println("In-memory database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Warning: could not initialize in-memory DB: " + e.getMessage());
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nurse-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 940);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


}
