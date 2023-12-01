package Dictionary.DictionaryController;

import Dictionary.Entities.SavedWord;
import Dictionary.Entities.SearchedWord;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static Dictionary.DatabaseConn.CurrentUser;
import static Dictionary.DatabaseConn.SearchedWordDAO;

public class RecentsController {
    @FXML
    private JFXListView<HBox> searchedList;

    @FXML
    private TextField searchbar;

    public void initialize() {
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            List<SearchedWord> savedWords = null;
            try {
                savedWords = SearchedWordDAO.queryListSearchedWordByUser();
                displaySavedWords(savedWords);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private HBox createHyperlink(String item, SearchedWord savedWord) {
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

        ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/images/recycle-bin.png").toExternalForm()));
        deleteIcon.setFitHeight(20);
        deleteIcon.setFitWidth(15);
        deleteIcon.setCursor(Cursor.HAND);
        /*deleteIcon.setOnMouseClicked(event -> {
            try {
                SavedWordDAO.deleteTuple(savedWord);
                displaySavedWords(SavedWordDAO.queryListSavedWordByUser());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        */
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hbox = new HBox(hyperlink, spacer, deleteIcon);
        hbox.setSpacing(10);
        HBox.setMargin(deleteIcon, new Insets(10, 20, 0, 0));


        return hbox;
    }

    public void displaySavedWords(List<SearchedWord> words) throws SQLException {
        ObservableList<HBox> observableList = FXCollections.observableArrayList();
        String filter = searchbar.getText().toLowerCase();
        for (SearchedWord word : words) {
            if (word.getWord().toLowerCase().contains(filter)) {
                System.out.println(word.getWord());
                observableList.add(createHyperlink(word.getWord(), word));
            }
        }
        searchedList.setItems(observableList);
        //adjustListViewHeight(wordlist);
    }
}
