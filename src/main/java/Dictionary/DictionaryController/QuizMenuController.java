package Dictionary.DictionaryController;

import com.j256.ormlite.stmt.query.In;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.StageStyle;

import java.io.IOException;

import java.net.URL;
import java.util.Dictionary;
import java.util.Objects;
import java.util.ResourceBundle;

public class QuizMenuController extends GameMenuController {
    @FXML
    private AnchorPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleStart(Event event) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(DictionaryController.class.getResource("/Views/QuizUI.fxml")));
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
            Parent root = FXMLLoader.load(getClass().getResource("/Views/QuizInstruction.fxml"));
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

