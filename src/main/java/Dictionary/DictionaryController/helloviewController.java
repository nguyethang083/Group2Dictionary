package Dictionary.DictionaryController;

import Dictionary.DictionaryCommandLine.VoiceFunction;
import Dictionary.models.EngWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import Dictionary.DatabaseConn;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class helloviewController implements Initializable {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextArea meaning, synonym, example;

    @FXML
    private TextField partsofspeech, phonetic, definitionPrompt;

    @FXML
    private Label wordLabel;

    @FXML
    private ImageView exampleContainer, synonymContainer, voiceButton;

    @FXML
    private Text examplePrompt, synonymPrompt;;

    @FXML
    private Rectangle rectangle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<EngWord> sortedWords = DatabaseConn.WordDAO.sortingWord();
            ObservableList<String> words = FXCollections.observableArrayList(sortedWords.stream().map(EngWord::getWord).collect(Collectors.toList()));
            comboBox.setItems(words);
            comboBox.setEditable(true);
            comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                ObservableList<String> filteredWords = words.filtered(word -> word.toLowerCase().contains(newValue.toLowerCase()));
                comboBox.setItems(filteredWords);
                if (!comboBox.isShowing()) {
                    comboBox.show();
                }
            });
            comboBox.setOnAction(event -> updateView());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateView() {
        String selectedWord = comboBox.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            wordLabel.setText(selectedWord);
            try {
                EngWord engWord = DatabaseConn.WordDAO.queryWordByString(selectedWord);
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
}
