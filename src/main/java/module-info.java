module co.edu.uniquindio.virtualwallet.virtualwallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.mapstruct;
    requires org.mapstruct.processor;
    requires java.desktop;
    requires java.logging;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires jdk.sctp;
    requires com.github.librepdf.openpdf;
    requires com.opencsv;
    requires jakarta.activation;

    opens co.edu.uniquindio.virtualwallet.virtualwallet to javafx.fxml;
    exports co.edu.uniquindio.virtualwallet.virtualwallet;

    opens co.edu.uniquindio.virtualwallet.virtualwallet.viewController to javafx.fxml;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

    opens co.edu.uniquindio.virtualwallet.virtualwallet.controller to javafx.fxml;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.controller;

    exports co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.mapping.mappers;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.model;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services;
    opens co.edu.uniquindio.virtualwallet.virtualwallet.utils to javafx.base, javafx.controls;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.utils;
    exports co.edu.uniquindio.virtualwallet.virtualwallet.services;

}