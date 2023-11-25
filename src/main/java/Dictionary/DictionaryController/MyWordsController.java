package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MyWordsController {
    @FXML
    private ListView<String> wordlist;
    @FXML
    private TextField searchbar;
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
        adjustListViewHeight(wordlist);
        wordlist.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    Hyperlink hyperlink = new Hyperlink(item);
                    hyperlink.setStyle("-fx-focus-color: transparent;");
                    hyperlink.setStyle("-fx-text-fill: #527B8E;");
                    hyperlink.setOnMouseClicked(event -> {
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
                    setGraphic(hyperlink);
                }
            }
        });
    }
    private void adjustListViewHeight(ListView<String> listView) {
        int totalItems = listView.getItems().size();
        int itemHeight = 47;
        int verticalPadding = 6;
        totalItems = Math.min(totalItems, 6);
        double totalHeight = totalItems * itemHeight + verticalPadding;

        listView.setPrefHeight(totalHeight);
    }
}
