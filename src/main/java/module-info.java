module DICTIONARY {
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires ormlite.jdbc;
    requires jlayer;
    requires com.jfoenix;
    opens Dictionary.DictionaryController to javafx.fxml;
    opens Dictionary to javafx.graphics;
    opens Dictionary.models to ormlite.jdbc;
    exports Dictionary.models;
    exports Dictionary.DictionaryController;
    exports Dictionary;
}