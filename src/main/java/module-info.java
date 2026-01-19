module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.slf4j;
    requires java.xml;
    requires java.sql;

    opens org.example.demo to javafx.fxml;
    opens entity to com.google.gson, javafx.base;
    opens util to com.google.gson;

    exports org.example.demo;
    exports util;
    exports entity;
    exports entity.repository;
    exports entity.repository.db;
    exports entity.repository.file;
    exports org.example.demo.doctor;
    opens org.example.demo.doctor to javafx.fxml;
    exports org.example.demo.appointment;
    opens org.example.demo.appointment to javafx.fxml;
    exports org.example.demo.patient;
    opens org.example.demo.patient to javafx.fxml;
    exports org.example.demo.logs;
    opens org.example.demo.logs to javafx.fxml;
    exports org.example.demo.hospital;
    opens org.example.demo.hospital to javafx.fxml;
    exports org.example.demo.menu;
    opens org.example.demo.menu to javafx.fxml;
}

