package Controller;


import Game.Quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.SQLException;

public class QuizController implements Initializable {

    private final Quiz quiz;
    ToggleGroup group = new ToggleGroup();
    @FXML
    private ToggleButton PlanA = new ToggleButton();
    @FXML
    private ToggleButton PlanB = new ToggleButton();
    @FXML
    private ToggleButton PlanC = new ToggleButton();
    @FXML
    private ToggleButton PlanD = new ToggleButton();
    @FXML
    private Label Question = new Label();
    @FXML
    private Button NextButton = new Button();
    @FXML
    private Label Result = new Label();
    @FXML
    private ImageView ScoreArea = new ImageView();
    @FXML
    private Label Score = new Label();
    @FXML
    private Label questionNumber = new Label();
    @FXML
    private ImageView ResultBack = new ImageView();
    @FXML
    private Label FinalScore = new Label();
    @FXML
    private Button TryAgain = new Button();


    public QuizController() throws SQLException {
        quiz = new Quiz();
    }


    public void setQuestion() {
        Question.setText(quiz.generateQuestion());
        Question.setWrapText(true);
        Question.setMaxWidth(826);
    }

    public void setScore() {
        Score.setText(String.format("%d", quiz.getScore()));
        Score.setWrapText(true);
    }

    public void setQuestionNumber() {
        questionNumber.setText((quiz.getNumberofQuestion() + 1) + " out of 10");
        Score.setWrapText(true);
    }

    public void setChoices() {
        for (ToggleButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setText(quiz.getChoice().get(List.of(PlanA, PlanB, PlanC, PlanD).indexOf(button)));
            button.setWrapText(true);
        }
    }

    public void setInputAnswer() {
        for (ToggleButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            if (button.isSelected()) {
                quiz.setInputAnswer(button.getText());
            }
        }
    }

    public void clearInputAnswer() {
        for (ToggleButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setSelected(false);
        }
    }

    public void handleNext(ActionEvent event) {
        if (quiz.getNumberofQuestion() % 10 == 0) {
            FinalScore.setText(String.format("%d", quiz.getScore()));
            handleResultVisible();

        } else {
            startQuiz();
            System.out.println("Submit button clicked!");
        }
    }

    public void setAvailable() {
        PlanA.setDisable(false);
        PlanB.setDisable(false);
        PlanC.setDisable(false);
        PlanD.setDisable(false);
    }

    public void startQuiz() {
        setAvailable();
        quiz.initQuiz();
        setQuestion();
        setChoices();
        setScore();
        setQuestionNumber();
        handlePlayingVisible();
        clearInputAnswer();
        quiz.increaseNumberofQuestion();
    }

    public void handleSelected(ActionEvent event) {
        Result.setWrapText(true);
        Result.setMaxWidth(642);
        ToggleButton selectedButton = (ToggleButton) event.getSource();
        group.getToggles().forEach(toggle -> {
            ToggleButton ToggleButton = (ToggleButton) toggle;
            if (!ToggleButton.equals(selectedButton)) {
                ToggleButton.setDisable(true);
            }
        });
        quiz.setInputAnswer(selectedButton.getText());
        if (quiz.checkAnswer()) {
            quiz.increaseScore();
            Result.setText("Correct!");
            String correctStyle = "-fx-border-radius: 10px;\n" +
                    "    -fx-background-color: #D6FEB8;\n" +
                    "    -fx-font-family: \"Noto Sans\";\n" +
                    "    -fx-font-style: \"Regular\";\n" +
                    "    -fx-text-fill: #6BB52C;\n" +
                    "    -fx-font-size: 20px;";
            Result.setStyle(correctStyle);
//            Result.setTextFill(Color.web("#6bb52c"));
//            Result.setBackground(Background.fill(Color.web("#d6feb8")));
        } else {
            Result.setText("Incorrect! The answer is " + quiz.getCorrectAnswer() + ".");
            String incorrectStyle = "-fx-border-radius: 10px;\n" +
                    "    -fx-background-color: #FEDEDF;\n" +
                    "    -fx-font-family: \"Noto Sans\";\n" +
                    "    -fx-font-style: \"Regular\";\n" +
                    "    -fx-text-fill: #EF6163;\n" +
                    "    -fx-font-size: 20px;";
            Result.setStyle(incorrectStyle);
//            Result.setTextFill(Color.web("#ef6163"));
//            Result.setBackground(Background.fill(Color.web("#fededf")));
        }
        Result.setVisible(true);
        NextButton.setVisible(true);
    }

    public void handlePlayAgain(ActionEvent event) {
        quiz.setNumberofQuestion(0);
        quiz.setScore(0);
        startQuiz();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (ToggleButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setToggleGroup(group);
        }
        startQuiz();
        for (ToggleButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setOnAction(this::handleSelected);
        }
        NextButton.setOnAction(this::handleNext);
    }

    public void handlePlayingVisible() {
        Result.setVisible(false);
        for (ToggleButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setVisible(true);
        }
        NextButton.setVisible(false);
        Question.setVisible(true);
        Score.setVisible(true);
        ScoreArea.setVisible(true);
        ResultBack.setVisible(false);
        TryAgain.setVisible(false);
        FinalScore.setVisible(false);
    }

    public void handleResultVisible() {
        Result.setVisible(false);
        for (ToggleButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setVisible(false);
        }
        NextButton.setVisible(false);
        Question.setVisible(false);
        Score.setVisible(false);
        ScoreArea.setVisible(false);
        ResultBack.setVisible(true);
        TryAgain.setVisible(true);
        FinalScore.setVisible(true);
        TryAgain.setOnAction(this::handlePlayAgain);

    }
}
