package Dictionary.DictionaryController;

import Dictionary.models.EngWord;
import Dictionary.models.WordDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static Dictionary.models.AllWord.allWord;
import Dictionary.DatabaseConn;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class helloviewController implements Initializable {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextArea meaning;

    @FXML
    private TextField partsofspeech;

    @FXML
    private TextField phonetic;

    @FXML
    private Label wordLabel;

    @FXML
    private TextArea synonym, example;

    @FXML
    private TextField definitionprompt;

    @FXML
    private ImageView exampleContainer;

    @FXML
    private Text examplePrompt;

    @FXML
    private Rectangle rectangle;

    @FXML
    private ImageView synonymContainer;

    @FXML
    private Text synonymPrompt;

    @FXML
    public EngWord currentWord = new EngWord();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> words = FXCollections.observableArrayList();
        try {
            List<EngWord> sortedWords = DatabaseConn.WordDAO.sortingWord();
            for (EngWord engword : sortedWords) {
                words.add(engword.getWord());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboBox.setItems(words);
        comboBox.setEditable(true);
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> filteredWords = words.filtered(word -> word.toLowerCase().contains(newValue.toLowerCase()));
            /*Comparator<String> comparator = (s1, s2) -> {
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

             */
            //comboBox.setItems(sortedWords);
            comboBox.setItems(filteredWords);
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
        });
        comboBox.setOnAction(event -> {
            String selectedWord = comboBox.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                wordLabel.setText(selectedWord);
                try {
                    EngWord engWord = DatabaseConn.WordDAO.queryWordByString(selectedWord);
                    String definition = engWord.getMeaning();
                    phonetic.setText(engWord.getPronunciation());
                    partsofspeech.setText(engWord.getType().toLowerCase());
                    meaning.setText(definition);
                    synonym.setText(engWord.getSynonym());
                    example.setText(engWord.getExample());
                    definitionprompt.setVisible(true);
                    exampleContainer.setVisible(true);
                    synonymPrompt.setVisible(true);
                    synonymContainer.setVisible(true);
                    examplePrompt.setVisible(true);
                    rectangle.setVisible(true);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
