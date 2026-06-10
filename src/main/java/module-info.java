module com.alex.sustavzaupravljanjebolnice {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens com.alex.sustavzaupravljanjebolnice to javafx.fxml;
    exports com.alex.sustavzaupravljanjebolnice;
}