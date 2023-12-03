package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

import static Dictionary.DatabaseConn.WordDAO;

public class addWordController {
    @FXML
    private Button addButton;

    @FXML
    private TextField newDefinition, newExample, newPhonetic, newSynonym, newType, newWord;


    @FXML
    void addWord(MouseEvent event) {
        EngWord newEngWord = new EngWord();

        newEngWord.setWord(newWord.getText());
        newEngWord.setMeaning(newDefinition.getText());
        newEngWord.setType(newType.getText());
        newEngWord.setPronunciation(newPhonetic.getText());
        newEngWord.setSynonym(newSynonym.getText());
        newEngWord.setExample(newExample.getText());

        try {
            boolean isAdded = WordDAO.addWord(newEngWord);
            if (isAdded) {
                showAlert("Bạn đã thêm từ này vào từ điển!");
                clearTextFields();
            }
            else {
                showAlert("Từ này đã tồn tại!");
                clearTextFields();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " addWord");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
