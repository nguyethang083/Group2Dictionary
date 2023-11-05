package Dictionary.DictionaryController;

import Dictionary.DictionaryCommandLine.VoiceFunction;
import Dictionary.models.EngWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Dictionary.models.AllWord.allWord;
import static Dictionary.DatabaseConn.WordDAO;

public class searchController implements Initializable {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextArea meaning, synonym, example;

    @FXML
    private TextField partsofspeech, phonetic, definitionPrompt, searchField;

    @FXML
    private Label wordLabel;

    @FXML
    private ImageView exampleContainer, synonymContainer, voiceButton, deleteAll;

    @FXML
    private Text examplePrompt, synonymPrompt;

    @FXML
    private Rectangle rectangle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> words = FXCollections.observableArrayList();
        for (EngWord engword : allWord) {
            words.add(engword.getWord());
        }
        comboBox.setItems(words);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                comboBox.hide();
            } else {
                ObservableList<String> filteredWords;
                try {
                    filteredWords = FXCollections.observableArrayList();
                    List<EngWord> wordss = WordDAO.containWordByString(newValue);
                    for (EngWord e : wordss) {
                        filteredWords.add(e.getWord());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                comboBox.setItems(filteredWords);
                if (!filteredWords.isEmpty() && !comboBox.isShowing()) {
                    comboBox.show();
                } else if (filteredWords.isEmpty()) {
                    comboBox.hide();
                }
            }
        });

        comboBox.setOnAction(event -> updateView());
    }

    private void updateView() {
        String selectedWord = comboBox.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            try {
                EngWord engWord = WordDAO.queryWordByString(selectedWord);
                wordLabel.setText(engWord.getWord());
                phonetic.setText(engWord.getPronunciation());
                partsofspeech.setText(engWord.getType().toLowerCase());
                meaning.setText(engWord.getMeaning());
                synonym.setText(engWord.getSynonym());
                example.setText(engWord.getExample());
                setVisibility();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setVisibility() {
        exampleContainer.setVisible(true);
        synonymContainer.setVisible(true);
        examplePrompt.setVisible(true);
        definitionPrompt.setVisible(true);
        synonymPrompt.setVisible(true);
        rectangle.setVisible(true);
        voiceButton.setVisible(true);
    }

    @FXML
    void playVoice(MouseEvent event) {
        String selectedWord = comboBox.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            VoiceFunction.playVoice(selectedWord);
        }
    }

    @FXML
    void setOpacityPressed(MouseEvent event) {
        voiceButton.setOpacity(0.5);
    }

    @FXML
    void setOpacityReleased(MouseEvent event) {
        voiceButton.setOpacity(1.0);
    }

    @FXML
    void setOnMouseEntered(MouseEvent event) {
        voiceButton.setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited(MouseEvent event) {
        voiceButton.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void setOpacityPressed1(MouseEvent event) {
        deleteAll.setOpacity(0.5);
    }

    @FXML
    void setOpacityReleased1(MouseEvent event) {
        deleteAll.setOpacity(1.0);
    }

    @FXML
    void setOnMouseEntered1(MouseEvent event) {
        deleteAll.setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited1(MouseEvent event) {
        deleteAll.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void clearSearchField(MouseEvent event) {
        searchField.clear();
    }
}
