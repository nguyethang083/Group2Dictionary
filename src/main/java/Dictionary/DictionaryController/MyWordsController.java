package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MyWordsController {
    @FXML
    private ListView<String> wordlist;
    private DictionaryController dictionaryController;

    public void setDictionaryController(DictionaryController dictionaryController) {
        this.dictionaryController = dictionaryController;
    }
    public void displaySavedWords(List<EngWord> words) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (EngWord word : words) {
            observableList.add(word.getWord());
        }
        wordlist.setItems(observableList);
        wordlist.setOnMouseClicked(event -> {
            String selectedWord = wordlist.getSelectionModel().getSelectedItem();

            // Load Dictionary.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dictionary.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Get the controller and set the selected word
            DictionaryController dictionaryController = loader.getController();
            dictionaryController.setSelectedWord(selectedWord);
            dictionaryController.updateView();

            Stage stage = (Stage) wordlist.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}
