package Dictionary.DictionaryController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class comboBoxController implements Initializable {
    @FXML
    private ComboBox<String> comboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> words = FXCollections.observableArrayList("Dog", "Cat", "Bear", "Doc", "crocodile", "Coconut", "dog", "sedile");
        comboBox.setItems(words);
        comboBox.setEditable(true);
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> filteredWords = words.filtered(word -> word.toLowerCase().startsWith(newValue.toLowerCase()));
            Comparator<String> comparator = (s1, s2) -> {
                if (s1.startsWith(newValue) && !s2.startsWith(newValue)) {
                    return -1;
                } else if (!s1.startsWith(newValue) && s2.startsWith(newValue)) {
                    return 1;
                } else {
                    return s1.compareToIgnoreCase(s2);
                }
            };
            ObservableList<String> sortedWords = FXCollections.observableArrayList(
                    filteredWords.stream().sorted(comparator).collect(Collectors.toList())
            );
            comboBox.setItems(sortedWords);
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
        });
    }
}
