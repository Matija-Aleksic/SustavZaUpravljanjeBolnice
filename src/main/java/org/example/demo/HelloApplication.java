package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DataManager;
import util.XmlLogger;

import java.io.IOException;

/**
 * Main JavaFX Application for Hospital Management System
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        XmlLogger.logAction("APP_START", "Application started");

        // Load main menu
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> XmlLogger.logAction("APP_CLOSE", "Application closed"));
        stage.show();
    }

    @Override
    public void init() {
        // Load data when application initializes
        DataManager.loadAllData();
    }
}
