package Dictionary.DictionaryController;

import Dictionary.Entities.ScoreWordle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.*;
import java.net.URL;

import static Dictionary.DatabaseConn.CurrentUser;
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

    private ScoreWordle score;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            score = ScoreWordleDAO.getTupleStreakbyUser();
            if (score == null)
            {
                long[] guess = {0, 0, 0, 0, 0, 0};
                score = new ScoreWordle(CurrentUser, 0, 0, 0, guess);
            }
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
        Long guess[] = {score.getGuess1(), score.getGuess2(), score.getGuess3(), score.getGuess4(),
                score.getGuess5(), score.getGuess6()};

        long max = Collections.max(Arrays.asList(guess));

        Guess1.setText(guess[0] + "");
        Guess1.setPrefWidth(barHeight(guess[0], max));
        Guess2.setText(guess[1] + "");
        Guess2.setPrefWidth(barHeight(guess[1], max));
        Guess3.setText(guess[2] + "");
        Guess3.setPrefWidth(barHeight(guess[2], max));
        Guess4.setText(guess[3] + "");
        Guess4.setPrefWidth(barHeight(guess[3], max));
        Guess5.setText(guess[4] + "");
        Guess5.setPrefWidth(barHeight(guess[4], max));
        Guess6.setText(guess[5] + "");
        Guess6.setPrefWidth(barHeight(guess[5], max));
    }

    private double barHeight(long value, long maxValue) {
        if (maxValue == 0) return 30;
        return 30 + 440.0 * value / (double) maxValue;
    }
}
