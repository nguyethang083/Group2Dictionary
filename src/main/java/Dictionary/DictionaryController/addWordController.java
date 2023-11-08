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
    private TextField newDefinition;

    @FXML
    private TextField newExample;

    @FXML
    private TextField newPhonetic;

    @FXML
    private TextField newSynonym;

    @FXML
    private TextField newType;

    @FXML
    private TextField newWord;

    @FXML
    void addWord(MouseEvent event) {
        // Create a new EngWord object
        EngWord newEngWord = new EngWord();

        // Set the properties of the newEngWord object based on the text fields
        newEngWord.setWord(newWord.getText());
        newEngWord.setMeaning(newDefinition.getText());
        newEngWord.setType(newType.getText());
        newEngWord.setPronunciation(newPhonetic.getText());
        newEngWord.setSynonym(newSynonym.getText());
        newEngWord.setExample(newExample.getText());

        try {
            // Add the new word to the database
            boolean isAdded = WordDAO.addWord(newEngWord);

            // If the word was added successfully, show an alert and clear the text fields
            if (isAdded) {
                showAlert("Bạn đã thêm từ này vào từ điển!");
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
