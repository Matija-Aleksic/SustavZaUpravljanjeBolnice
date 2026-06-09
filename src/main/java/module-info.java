module com.alex.sustavzaupravljanjebolnice {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.alex.sustavzaupravljanjebolnice to javafx.fxml;
    exports com.alex.sustavzaupravljanjebolnice;
}