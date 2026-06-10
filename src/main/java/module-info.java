module com.alex.sustavzaupravljanjebolnice {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;


    opens com.alex.sustavzaupravljanjebolnice to javafx.fxml;
    exports com.alex.sustavzaupravljanjebolnice;
}