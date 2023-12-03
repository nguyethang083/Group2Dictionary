package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import Dictionary.Entities.SavedWord;
import Dictionary.Entities.SavedWordDAO;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import static Dictionary.DatabaseConn.SavedWordDAO;
import static Dictionary.DatabaseConn.CurrentUser;
import static Dictionary.Features.StringProcessing.normalizeString;


public class MyWordsController {
    @FXML
    private ListView<HBox> wordlist;

    @FXML
    private TextField searchbar;

    @FXML
    private Text deleteAll;

    @FXML
    private Label count, alphabetSort, newestSort;

    private DictionaryController dictionaryController = new DictionaryController();

    @FXML
    public void initialize() {
        alphabetSort.setOnMouseClicked(event -> {
            try {
                searchbar.clear();
                displaySavedWords(SavedWordDAO.queryListSavedWordByUser());
                alphabetSort.setStyle("-fx-background-color:  #1a475b; -fx-text-fill: white; -fx-font-weight: bold;");
                newestSort.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color:  #527B8E; -fx-font-weight: normal;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        newestSort.setOnMouseClicked(event -> {
            try {
                searchbar.clear();
                displaySavedWords(SavedWordDAO.queryListSavedWordByUserNewest());
                newestSort.setStyle("-fx-background-color:  #1a475b; -fx-text-fill: white; -fx-font-weight: bold;");
                alphabetSort.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color:  #527B8E; -fx-font-weight: normal;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String normalizedValue = normalizeString(newValue);
                List<SavedWord> words;
                if (newestSort.getStyle().contains("-fx-background-color:  #1a475b")) {
                    words = SavedWordDAO.searchSavedWordByUserNewest(normalizedValue);
                } else {
                    words = SavedWordDAO.searchSavedWordByUser(normalizedValue);
                }

                displaySavedWords(words);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private HBox createHyperlink(String item, SavedWord savedWord) {
        Hyperlink hyperlink = getHyperlink(item);

        ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/images/recycle-bin.png").toExternalForm()));
        deleteIcon.setFitHeight(20);
        deleteIcon.setFitWidth(15);
        deleteIcon.setCursor(Cursor.HAND);
        deleteIcon.setOnMouseClicked(event -> {
            try {
                SavedWordDAO.deleteTuple(savedWord);
                displaySavedWords(SavedWordDAO.queryListSavedWordByUser());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hbox = new HBox(hyperlink, spacer, deleteIcon);
        hbox.setSpacing(10);
        HBox.setMargin(deleteIcon, new Insets(10, 20, 0, 0));


        return hbox;
    }

    @NotNull
    private Hyperlink getHyperlink(String item) {
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


    public void displaySavedWords(List<SavedWord> words) throws SQLException {
        ObservableList<HBox> observableList = FXCollections.observableArrayList();
        for (SavedWord word : words) {
            observableList.add(createHyperlink(word.getWord(), word));
        }
        wordlist.setItems(observableList);
        adjustListViewHeight(wordlist);

        try {
            int wordCount = SavedWordDAO.getWordCountByUser();
            count.setText(Integer.toString(wordCount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void adjustListViewHeight(ListView<HBox> listView) {
        int totalItems = listView.getItems().size();
        int itemHeight = 47;
        int verticalPadding = 6;
        totalItems = Math.min(totalItems, 6);
        double totalHeight = totalItems * itemHeight + verticalPadding;

        listView.setPrefHeight(totalHeight);
    }

    @FXML
    public void handleDeleteAll(MouseEvent event) {
        try {
            SavedWordDAO.deleteAllWordsByUser(CurrentUser);
            displaySavedWords(SavedWordDAO.queryListSavedWordByUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
