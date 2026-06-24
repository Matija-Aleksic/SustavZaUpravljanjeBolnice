module com.alex.sustavzaupravljanjebolnice {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    requires com.h2database;

    opens com.alex.sustavzaupravljanjebolnice to javafx.fxml;
    opens com.alex.sustavzaupravljanjebolnice.controller to javafx.fxml;
    exports com.alex.sustavzaupravljanjebolnice;
    opens com.alex.sustavzaupravljanjebolnice.controller.popup to javafx.fxml;
}
