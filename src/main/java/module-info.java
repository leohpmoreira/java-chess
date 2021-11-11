module com.fklm.javachess {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.fklm.javachess to javafx.fxml;
    exports com.fklm.javachess;
    opens com.fklm.javachess.controller to javafx.fxml;
    exports com.fklm.javachess.controller;
}