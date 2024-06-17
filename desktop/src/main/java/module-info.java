module ru.kidesoft.desktop {
    requires atlantafx.base;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires gg.jte;
    requires gg.jte.runtime;

    requires jakarta.persistence;

    requires java.datatransfer;
    requires java.desktop;
    requires java.rmi;
    requires java.xml;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;

    requires static lombok;
    requires micrometer.observation;
    requires net.datafaker;
    requires net.rgielen.fxweaver.core;
    requires net.rgielen.fxweaver.spring;
    requires org.apache.logging.log4j;
    requires org.controlsfx.controls;
    requires org.hibernate.orm.core;
    requires org.kordamp.ikonli.fluentui;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.slf4j;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires spring.web;
    requires spring.tx;

    //opens ru.kidesoft.desktop to spring.core;

    opens ru.kidesoft.desktop;
    opens ru.kidesoft.desktop.controller.javafx;

    opens ru.kidesoft.desktop.controller.javafx.dto;
    opens ru.kidesoft.desktop.controller.javafx.events;
    opens ru.kidesoft.desktop.controller.javafx.fxml;
    opens ru.kidesoft.desktop.controller.javafx.fxml.main;


    opens ru.kidesoft.desktop.domain.entity;
    opens ru.kidesoft.desktop.domain.entity.history;
    opens ru.kidesoft.desktop.domain.entity.profile;
    opens ru.kidesoft.desktop.domain.entity.click;
    opens ru.kidesoft.desktop.domain.entity.constant;
    opens ru.kidesoft.desktop.domain.entity.login;
    opens ru.kidesoft.desktop.domain.entity.order;
    opens ru.kidesoft.desktop.domain.entity.session;
    opens ru.kidesoft.desktop.domain.entity.setting;

    opens ru.kidesoft.desktop.repository.kkt.atol;


    exports ru.kidesoft.desktop;
    exports ru.kidesoft.desktop.controller.javafx;
    exports ru.kidesoft.desktop.controller.javafx.dto;
    exports ru.kidesoft.desktop.controller.javafx.events;
    exports ru.kidesoft.desktop.controller.javafx.fxml;
    exports ru.kidesoft.desktop.controller.javafx.fxml.main;

    exports ru.kidesoft.desktop.repository.kkt.atol;
}