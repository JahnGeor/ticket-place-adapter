module ru.kidesoft.ticketplace.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.h2database;
    requires java.sql;
    requires flyway.core;
    requires com.google.gson;
    requires com.fasterxml.jackson.core;
    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires org.kordamp.ikonli.fluentui;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.apache.logging.log4j;
    opens ru.kidesoft.ticketplace.client.view.controller.impl to javafx.fxml;
    opens ru.kidesoft.ticketplace.database.migrations;
    exports ru.kidesoft.ticketplace.client.main;
    exports ru.kidesoft.ticketplace.client.view.controller.impl;
}