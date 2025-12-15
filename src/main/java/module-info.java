module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.slf4j;
    requires java.xml;


    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
}