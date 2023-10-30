module DICTIONARY {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    //opens com.example.dictionary to javafx.fxml;
    opens DictionaryCommandLine to javafx.graphics;
    //exports com.example.dictionary;
    exports DictionaryCommandLine;
}