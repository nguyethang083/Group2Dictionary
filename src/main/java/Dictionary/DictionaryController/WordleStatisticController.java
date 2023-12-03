package Dictionary.DictionaryController;

import Dictionary.Entities.ScoreWordle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    @FXML
    private Label Guess1;
    @FXML
    private Label Guess2;
    @FXML
    private Label Guess3;
    @FXML
    private Label Guess4;
    @FXML
    private Label Guess5;
    @FXML
    private Label Guess6;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScoreWordle score = new ScoreWordle();
        try {
            score = ScoreWordleDAO.getTupleStreakbyUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setLabels(score);
        setBarChart();
    }

    public void setLabels(ScoreWordle score) {
            Played.setText("" + score.getNum_play());
            Win.setText("" + score.getNum_win());
            Streak.setText("" + score.getStreak());
    }

    public void setBarChart() {
        long guess1 = 0;
        long guess2 = 2;
        long guess3 = 1;
        long guess4 = 5;
        long guess5 = 9;
        long guess6 = 14;

        Guess1.setText(guess1 + "");
        Guess1.setPrefWidth(barHeight(guess1, 14));
        Guess2.setText(guess2 + "");
        Guess2.setPrefWidth(barHeight(guess2, 14));
        Guess3.setText(guess3 + "");
        Guess3.setPrefWidth(barHeight(guess3, 14));
        Guess4.setText(guess4 + "");
        Guess4.setPrefWidth(barHeight(guess4, 14));
        Guess5.setText(guess5 + "");
        Guess5.setPrefWidth(barHeight(guess5, 14));
        Guess6.setText(guess6 + "");
        Guess6.setPrefWidth(barHeight(guess6, 14));
    }

    private double barHeight(long value, long maxValue) {
        if (value == 0) return 30;
        return 30 + 440.0 * value / (double) maxValue;
    }
}
