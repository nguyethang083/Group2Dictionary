package Dictionary.DictionaryController;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public abstract class GameMenuController implements Initializable {
    @FXML
    protected Button Start;
    @FXML
    protected Button HowtoPlay;
    protected DictionaryController dictionaryController;


    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);

    @FXML
    public abstract void handleStart(Event event);

    @FXML
    public abstract void handleHowtoPlay(Event event);
}
