package Controller;


import Game.Quiz;
import DictionaryCommandLine.Dictionary;
import DictionaryCommandLine.DictionaryManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuizController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final Quiz quiz;
    ToggleGroup group = new ToggleGroup();
    @FXML
    private RadioButton PlanA = new RadioButton();
    @FXML
    private RadioButton PlanB = new RadioButton();
    @FXML
    private RadioButton PlanC = new RadioButton();
    @FXML
    private RadioButton PlanD = new RadioButton();
    @FXML
    private Label Question = new Label();
    @FXML
    private Button NextButton = new Button();
    @FXML
    private Label Result = new Label();

    public QuizController() {
        quiz = new Quiz();
    }


    public void setQuestion() {
        Question.setText(quiz.generateQuestion());
        Question.setWrapText(true);
        Question.setMaxWidth(500);
    }

    public void setChoices() {
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setText(quiz.getChoice().get(List.of(PlanA, PlanB, PlanC, PlanD).indexOf(button)));
            button.setWrapText(true);
        }
    }

    public void setInputAnswer() {
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            if (button.isSelected()) {
                quiz.setInputAnswer(button.getText());
            }
        }
    }

    public void clearInputAnswer() {
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setSelected(false);
        }
    }

    public void handleSubmit(ActionEvent event) {
        startQuiz();
        System.out.println("Submit button clicked!");
    }

    public void setAvailable() {
        PlanA.setDisable(false);
        PlanB.setDisable(false);
        PlanC.setDisable(false);
        PlanD.setDisable(false);
    }

    public void startQuiz() {
        setAvailable();
        quiz.initQuiz(dictionary);
        setQuestion();
        setChoices();
        handleVisible();
        clearInputAnswer();
        quiz.increaseNumberofQuestion();
    }

    public void handleSelected(ActionEvent event) {
        Result.setWrapText(true);
        Result.setMaxWidth(642);
        RadioButton selectedButton = (RadioButton) event.getSource();
        group.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            if (!radioButton.equals(selectedButton)) {
                radioButton.setDisable(true);
            }
        });
        quiz.setInputAnswer(selectedButton.getText());
        if (quiz.checkAnswer()) {
            quiz.increaseScore();
            Result.setText("Correct!");
        } else {
            Result.setText("Wrong, " + quiz.getCorrectAnswer());
        }
        Result.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.insertFromFile(dictionary);
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setToggleGroup(group);
        }
        startQuiz();
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setOnAction(this::handleSelected);
        }
        NextButton.setOnAction(this::handleSubmit);
    }

    public void handleVisible() {
        Result.setVisible(false);
        for (RadioButton button : List.of(PlanA, PlanB, PlanC, PlanD)) {
            button.setVisible(true);
        }
    }
}
