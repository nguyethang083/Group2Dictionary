package Dictionary.DictionaryController;

import Dictionary.Alerts.Alerts;
import Dictionary.Entities.EngWord;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

import static Dictionary.DatabaseConn.WordDAO;
import static Dictionary.Features.StringProcessing.normalizeString;

public class addWordController {
    @FXML
    private Button addButton;

    @FXML
    private TextField newDefinition, newExample, newPhonetic, newSynonym, newType, newWord;

    private Alerts alert = new Alerts();

    @FXML
    void addWord(MouseEvent event) {
        EngWord newEngWord = new EngWord();

        newEngWord.setWord(normalizeString(newWord.getText()));
        newEngWord.setMeaning(newDefinition.getText());
        newEngWord.setType(newType.getText());
        newEngWord.setPronunciation(newPhonetic.getText());
        newEngWord.setSynonym(newSynonym.getText());
        newEngWord.setExample(newExample.getText());

        try {
            boolean isAdded = WordDAO.addWord(newEngWord);
            if (isAdded) {
                alert.showAlertInfo("Add new word", "This word is successfully added");
                clearTextFields();
            } else if (newEngWord.getWord().isEmpty() || newEngWord.getMeaning().isEmpty()) {
                alert.showAlertWarning("Warning", "You have to fill in the blanks");
            } else {
                alert.showAlertWarning("Warning", "This word existed");
                clearTextFields();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " addWord");
        }
    }

    private void clearTextFields() {
        newWord.clear();
        newDefinition.clear();
        newType.clear();
        newPhonetic.clear();
        newSynonym.clear();
        newExample.clear();
    }
}
