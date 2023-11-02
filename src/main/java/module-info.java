module DICTIONARY {
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.controls;

    requires org.controlsfx.controls;
    opens Dictionary.DictionaryController to javafx.fxml;
    opens Dictionary to javafx.graphics;
    exports Dictionary.DictionaryController;
    exports Dictionary;
}