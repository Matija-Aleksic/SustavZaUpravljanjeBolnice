module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.slf4j;
    requires java.xml;
    requires java.sql;

    opens org.example.demo to javafx.fxml;
    opens entity to com.google.gson, javafx.base;

    exports org.example.demo;
}

