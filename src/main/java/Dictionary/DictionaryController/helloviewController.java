package Dictionary.DictionaryController;

import Dictionary.models.EngWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static Dictionary.models.AllWord.allWord;

public class helloviewController implements Initializable {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ImageView listbar;

    @FXML
    private TextArea meaning;

    @FXML
    private TextField partsofspeech;

    @FXML
    private TextField phonetic;

    @FXML
    private Label wordLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> words = FXCollections.observableArrayList();
        for (EngWord engword : allWord) {
            words.add(engword.getWord());
        }
        comboBox.setItems(words);
        comboBox.setEditable(true);
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> filteredWords = words.filtered(word -> word.toLowerCase().contains(newValue.toLowerCase()));
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
