package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import Dictionary.Entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import Dictionary.Entities.ScoreQuiz;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Dictionary.DatabaseConn.SavedWordDAO;
import static Dictionary.DatabaseConn.ScoreQuizDAO;

public class QuizStatisticController implements Initializable {
    @FXML
    private TableView<ScoreQuiz> History;
    @FXML
    private TableColumn<ScoreQuiz, Long> Score;
    @FXML
    private TableColumn<ScoreQuiz, String> User_id;
    @FXML
    private TableColumn<ScoreQuiz, Long> id;
    @FXML
    private Label Played;
    @FXML
    private Label TotalScore;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<ScoreQuiz> quizScores = null;
        try {
            quizScores = ScoreQuizDAO.queryListScoreByUser("Lam");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayHistory(quizScores);
        setLabels();
    }

    public void displayHistory(List<ScoreQuiz> scoreQuiz) {
        ObservableList<ScoreQuiz> history = FXCollections.observableList(scoreQuiz);
        id.setCellValueFactory(new PropertyValueFactory<ScoreQuiz, Long>("id"));
        User_id.setCellValueFactory(new PropertyValueFactory<ScoreQuiz, String>("User_id"));
        Score.setCellValueFactory(new PropertyValueFactory<ScoreQuiz, Long>("Score"));
        id.setResizable(false);
        User_id.setResizable(false);
        Score.setResizable(false);
        History.setItems(history);
    }

    public void setLabels() {
        try {
            long played = ScoreQuizDAO.getNumPlaybyUser("Lam");
            Played.setText("" + played);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            long totalScore = ScoreQuizDAO.getTotalScorebyUser("Lam");
            TotalScore.setText("" + totalScore);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
