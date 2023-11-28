package Dictionary.DictionaryController;

import Dictionary.Alerts.Alerts;
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

    private Alerts alerts = new Alerts();


    @FXML
    void addWord(MouseEvent event) {
        EngWord newEngWord = new EngWord();

        newEngWord.setWord(newWord.getText());
        newEngWord.setMeaning(newDefinition.getText());
        newEngWord.setType(newType.getText());
        newEngWord.setPronunciation(newPhonetic.getText());
        newEngWord.setSynonym(newSynonym.getText());
        newEngWord.setExample(newExample.getText());

        boolean status = alerts.showAlertConfirmation("Confirmation", "Ban co chac chan muon them tu nay vao tu dien?");
        if (status) {
            try {
                boolean isAdded = WordDAO.addWord(newEngWord);
                if (isAdded) {
                    alerts.showAlertInfo("Information", "Them thanh cong");
                    clearTextFields();
                } else {
                    alerts.showAlertWarning("Warning", "Tu nay da ton tai trong tu dien");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage() + " addWord");
            }
        } else {
            alerts.showAlertInfo("Information", "Ban da huy thao tac them");
        }
    }

    private void showAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Information Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
        alerts.showAlertWarning("Warning", message);
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
