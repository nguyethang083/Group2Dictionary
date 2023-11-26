package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MyWordsController {
    @FXML
    private ListView<Hyperlink> wordlist;

    @FXML
    private TextField searchbar;

    private Hyperlink createHyperlink(String item) {
        Hyperlink hyperlink = new Hyperlink(item);
        hyperlink.setStyle("-fx-focus-color: transparent;");
        hyperlink.setStyle("-fx-text-fill: #527B8E;");
        hyperlink.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dictionary.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            DictionaryController dictionaryController = loader.getController();
            dictionaryController.setSelectedWord(item);
            dictionaryController.updateView();
            Stage stage = (Stage) wordlist.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        });
        return hyperlink;
    }

    public void displaySavedWords(List<EngWord> words) {
        ObservableList<Hyperlink> observableList = FXCollections.observableArrayList();
        for (EngWord word : words) {
            observableList.add(createHyperlink(word.getWord()));
        }
        wordlist.setItems(observableList);
        adjustListViewHeight(wordlist);
    }

    private void adjustListViewHeight(ListView<Hyperlink> listView) {
        int totalItems = listView.getItems().size();
        int itemHeight = 47;
        int verticalPadding = 6;
        totalItems = Math.min(totalItems, 6);
        double totalHeight = totalItems * itemHeight + verticalPadding;

        listView.setPrefHeight(totalHeight);
    }
}
