package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.List;

public class MyWordsController {

    @FXML
    private JFXListView<String> wordlist;

    public void displaySavedWords(List<EngWord> words) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (EngWord word : words) {
            observableList.add(word.getWord());
        }
    }
}
