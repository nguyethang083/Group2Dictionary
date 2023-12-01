package Dictionary.DictionaryController;

import Dictionary.Entities.ScoreWordle;
import Dictionary.Entities.ScoreWordleDAO;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.*;
import java.net.URL;

import static Dictionary.DatabaseConn.ScoreWordleDAO;
public class WordleStatisticController implements Initializable {
    @FXML
    private Label Played;
    @FXML
    private Label Win;
    @FXML
    private Label Streak;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    public void setLabels() {
        try {
            Played.setText("" + ScoreWordleDAO.getNumPlaybyUser("lam"));
            Win.setText("" + ScoreWordleDAO.getNumWinbyUser("lam"));
            Streak.setText("" + ScoreWordleDAO.getStreakbyUser("lam"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
