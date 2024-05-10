module ru.kidesoft.ticketplace.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires spring.boot.autoconfigure;;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.data.jpa;

    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires org.kordamp.ikonli.fluentui;
    requires org.kordamp.ikonli.materialdesign2;
    opens ru.kidesoft.ticketplace.client.view.controller.impl to javafx.fxml;
    opens ru.kidesoft.ticketplace.client.main to spring.core;
    exports ru.kidesoft.ticketplace.client.main;
    exports ru.kidesoft.ticketplace.client.view.controller.impl;
}