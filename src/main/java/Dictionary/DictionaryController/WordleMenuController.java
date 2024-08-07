package Dictionary.DictionaryController;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WordleMenuController extends GameMenuController {
    @FXML
    private AnchorPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleStart(Event event) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(DictionaryController.class.getResource("/Views/WordleUI.fxml")));
            content.getChildren().clear();
            content.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleHowtoPlay(Event event) {
        Stage Instruction = new Stage();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/WordleInstruction.fxml"));
            Instruction.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the title of the new Stage
        Instruction.setTitle("How to play");
        //Instruction.initStyle(StageStyle.TRANSPARENT);

        // Show the new Stage
        Instruction.show();
    }
}

