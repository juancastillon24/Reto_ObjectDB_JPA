module org.example.retoobjectdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.persistence;
    requires java.naming;
    requires javafx.base;
    requires objectdb;

    // AÑADE ESTA LÍNEA:
    requires java.sql;

    opens org.example.retoobjectdb;
    exports org.example.retoobjectdb;
    opens org.example.retoobjectdb.models;
    exports org.example.retoobjectdb.models;
    opens org.example.retoobjectdb.repositories;
    exports org.example.retoobjectdb.repositories;
    opens org.example.retoobjectdb.utils;
    exports org.example.retoobjectdb.utils;
    opens org.example.retoobjectdb.controllers;
    exports org.example.retoobjectdb.controllers;
    opens org.example.retoobjectdb.session;
    exports org.example.retoobjectdb.session;
    exports org.example.retoobjectdb.services;
    opens org.example.retoobjectdb.services;
}