module DICTIONARY {
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires ormlite.jdbc;
    requires jlayer;
    requires com.jfoenix;
    requires httpcore;
    requires httpclient;
    requires json;
    requires java.net.http;
    opens Dictionary.DictionaryController to javafx.fxml;
    opens Dictionary to javafx.graphics;
    opens Dictionary.Entities to ormlite.jdbc;
    exports Dictionary.Entities;
    exports Dictionary.DictionaryController;
    exports Dictionary;
}