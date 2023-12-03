package Dictionary.DictionaryController;

import Dictionary.Entities.SearchedWord;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static Dictionary.DatabaseConn.*;
import static Dictionary.Features.StringProcessing.normalizeString;

public class RecentsController {
    @FXML
    private JFXListView<HBox> searchedList;

    @FXML
    private TextField searchbar;

    public void initialize() {
        //searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            List<SearchedWord> searchedWords = null;
            try {
                searchedWords = SearchedWordDAO.queryListSearchedWordByUser();
                displaySavedWords(searchedWords);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        //});
    }

    private HBox createHyperlink(String item, SearchedWord searchedWord) {
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
            Stage stage = (Stage) searchedList.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hbox = new HBox(hyperlink, spacer);
        hbox.setSpacing(10);
        //HBox.setMargin(deleteIcon, new Insets(10, 20, 0, 0));


        return hbox;
    }

    public void displaySavedWords(List<SearchedWord> words) throws SQLException {
        ObservableList<HBox> observableList = FXCollections.observableArrayList();
        String filter = searchbar.getText();
        filter = normalizeString(filter);
        for (SearchedWord word : words) {
            observableList.add(createHyperlink(word.getWord(), word));
        }
        searchedList.setItems(observableList);
        adjustListViewHeight(searchedList);
    }

    private void adjustListViewHeight(ListView<HBox> listView) {
        int totalItems = listView.getItems().size();
        int itemHeight = 47;
        int verticalPadding = 6;
        totalItems = Math.min(totalItems, 6);
        double totalHeight = totalItems * itemHeight + verticalPadding;

        listView.setPrefHeight(totalHeight);
    }
}
