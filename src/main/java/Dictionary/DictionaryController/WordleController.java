package Dictionary.DictionaryController;

import Dictionary.Entities.ScoreWordle;
import Dictionary.Game.Wordle;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static Dictionary.DatabaseConn.CurrentUser;
import static Dictionary.DatabaseConn.ScoreWordleDAO;

public class WordleController implements Initializable {
    private final Wordle wordle;
    private final String[] firstRowLetters = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private final String[] secondRowLetters = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    private final String[] thirdRowLetters = {"↵", "Z", "X", "C", "V", "B", "N", "M", "←"};
    private final int MAX_COLUMN = 5;
    private final int MAX_ROW = 6;
    private int currentRow = 1;
    private int currentColumn = 1;

    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane keyboardRow1;
    @FXML
    private GridPane keyboardRow2;
    @FXML
    private GridPane keyboardRow3;
    @FXML
    private ImageView ResultBack;
    @FXML
    private Label GameStatus;
    @FXML
    private Label WinningWord = new Label();
    @FXML
    private ImageView restartIcon;
    @FXML
    private ImageView statisticIcon;
    @FXML
    private Button TryAgain;
    @FXML
    private Label Invalid;

    private ScoreWordle scoreWordle;

    public WordleController() {
        wordle = new Wordle();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            scoreWordle = ScoreWordleDAO.getTupleStreakbyUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        createGrid();
        createKeyboard();
        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                onKeyPressed(event);
            }
        });
        reset();
    }

    // tạo ra bảng gồm các từ hiển thị
    public void createGrid() {
        for (int i = 1; i <= MAX_ROW; i++) {
            for (int j = 1; j <= MAX_COLUMN; j++) {
                Label label = new Label();
                label.getStyleClass().add("default-tile");
                gridPane.add(label, j, i);
            }
        }
    }

    public void createKeyboard() {
        for (int i = 0; i < firstRowLetters.length; i++) {
            Label label = new Label();
            label.getStyleClass().add("keyboard-tile");
            label.setText(firstRowLetters[i]);
            keyboardRow1.add(label, i, 1);
        }
        for (int i = 0; i < secondRowLetters.length; i++) {
            Label label = new Label();
            label.getStyleClass().add("keyboard-tile");
            label.setText(secondRowLetters[i]);
            keyboardRow2.add(label, i, 2);
        }
        for (int i = 0; i < thirdRowLetters.length; i++) {
            Label label = new Label();
            if (i == 0 || i == thirdRowLetters.length - 1)
                label.getStyleClass().add("keyboard-tile-non-alphanumeric");
            else
                label.getStyleClass().add("keyboard-tile");
            label.setText(thirdRowLetters[i]);
            keyboardRow3.add(label, i, 3);
        }
    }

    // Cài đặt input lên ô từ
    private void setBlockText(int searchRow, int searchColumn, String input) {
        Label label = getLabel(searchRow, searchColumn);
        if (label != null)
            label.setText(input.toUpperCase());
    }

    // Trả về label chữ cái đang hiển thị của 1 ô bất kì
    private Label getLabel(int searchRow, int searchColumn) {
        for (Node child : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            // nếu = null thì đặt = 0, nếu not null trả về đúng giá trị
            int row = r == null ? 0 : r;
            int column = c == null ? 0 : c;
            if (row == searchRow && column == searchColumn && (child instanceof Label))
                return (Label) child;
        }
        return null;
    }

    // trả về label của nguyên tu trong grid tương ứng với chữ cái đúng
    private Label getLabel(GridPane keyboardRow, String letter) {
        Label label;
        for (Node child : keyboardRow.getChildren()) {
            if (child instanceof Label) {
                label = (Label) child;
                if (letter.equalsIgnoreCase(label.getText()))
                    return label;
            }
        }
        return null;
    }

    // Trả về chữ cái đang ở hàng nào cột nào
    private String getBlockText(int searchRow, int searchColumn) {
        Label label = getLabel(searchRow, searchColumn);
        if (label != null)
            return label.getText().toLowerCase();
        return null;
    }

    // set style class cho chữ cái
    private void setBlockStyleClass(int searchRow, int searchColumn, String styleclass) {
        Label label = getLabel(searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().add(styleclass);
        }
    }

    //xóa style class
    private void clearBlockStyleClass(int searchRow, int searchColumn) {
        Label label = getLabel(searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    //update màu cho chữ cái
    private void updateRowColor(int searchRow, String status) {
        SequentialTransition seq = new SequentialTransition();
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(searchRow, i);
            String styleClass;
            if (label != null) {
                String currentCharacter = String.valueOf(label.getText().charAt(0)).toLowerCase();
                if (String.valueOf(wordle.getAnswer().charAt(i - 1)).toLowerCase().equals(currentCharacter)) {
                    styleClass = "correct-letter";
                } else if (wordle.getAnswer().contains(currentCharacter)) {
                    styleClass = "present-letter";
                } else {
                    styleClass = "wrong-letter";
                }
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), label);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(1);
                fadeTransition.setOnFinished(e -> {
                    label.getStyleClass().clear();
                    label.getStyleClass().setAll(styleClass);
                });
                seq.getChildren().add(fadeTransition);

                ScaleTransition bounceTransition1 = new ScaleTransition(Duration.millis(100), label);
                bounceTransition1.fromXProperty().setValue(1);
                bounceTransition1.toXProperty().setValue(1.1);
                bounceTransition1.fromYProperty().setValue(1);
                bounceTransition1.toYProperty().setValue(1.1);
                ScaleTransition bounceTransition2 = new ScaleTransition(Duration.millis(100), label);
                bounceTransition2.fromXProperty().setValue(1.1);
                bounceTransition2.toXProperty().setValue(1);
                bounceTransition2.fromYProperty().setValue(1.1);
                bounceTransition2.toYProperty().setValue(1);

                seq.getChildren().add(bounceTransition1);
                seq.getChildren().add(bounceTransition2);
            }
        }
        if (status == "win") {
            seq.setOnFinished(e -> {
                GameStatus.setText("YOU WIN!");
                handleVisible(false);
            });
        } else if (status == "lose") {
            seq.setOnFinished(e -> {
                GameStatus.setText("YOU LOSE!");
                handleVisible(false);
            });
        }

        seq.play();
    }

    //update keyboard sau khi nhập
    private void updateKeyboardColor() {
        String currentWord = getWordFromCurrentRow().toLowerCase();
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label keyboardLabel = new Label();
            String styleClass;
            String currentCharacter = String.valueOf(currentWord.charAt(i - 1));
            String winningCharacter = String.valueOf(wordle.getAnswer().charAt(i - 1));

            if (wordle.contains(firstRowLetters, currentCharacter))
                keyboardLabel = getLabel(keyboardRow1, currentCharacter);
            else if (wordle.contains(secondRowLetters, currentCharacter))
                keyboardLabel = getLabel(keyboardRow2, currentCharacter);
            else if (wordle.contains(thirdRowLetters, currentCharacter))
                keyboardLabel = getLabel(keyboardRow3, currentCharacter);

            if (currentCharacter.equals(winningCharacter))
                styleClass = "keyboardCorrectColor";
            else if (wordle.getAnswer().contains(currentCharacter))
                styleClass = "keyboardPresentColor";
            else
                styleClass = "keyboardWrongColor";
            if (keyboardLabel != null) {
                keyboardLabel.getStyleClass().clear();
                keyboardLabel.getStyleClass().add(styleClass);
            }
        }
    }

    // lấy tư trong hàng hiện tại
    private String getWordFromCurrentRow() {
        StringBuilder input = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++)
            input.append(getBlockText(currentRow, j));
        return input.toString();
    }

    //xử lí sự kiện bàn phím
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            onBackspacePressed();
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterPressed(keyEvent);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterPressed();
        }
    }

    //xử lí xóa
    private void onBackspacePressed() {
        if ((currentColumn == MAX_COLUMN || currentColumn == 1)
                && !Objects.equals(getBlockText(currentRow, currentColumn), "")) {
            setBlockText(currentRow, currentColumn, "");
            clearBlockStyleClass(currentRow, currentColumn);
            setBlockStyleClass(currentRow, currentColumn, "default-tile");
        } else if ((currentColumn > 1 && currentColumn < MAX_COLUMN)
                || (currentColumn == MAX_COLUMN && Objects.equals(getBlockText(currentRow, currentColumn), ""))) {
            currentColumn--;
            setBlockText(currentRow, currentColumn, "");
            clearBlockStyleClass(currentRow, currentColumn);
            setBlockStyleClass(currentRow, currentColumn, "default-tile");
        }
    }

    private void onLetterPressed(KeyEvent keyEvent) {
        if (Objects.equals(getBlockText(currentRow, currentColumn), "")) {
            setBlockText(currentRow, currentColumn, keyEvent.getText());
            System.out.println(keyEvent.getText());
            Label label = getLabel(currentRow, currentColumn);
            ScaleTransition bounceTransition1 = new ScaleTransition(Duration.millis(100), label);
            bounceTransition1.fromXProperty().setValue(1);
            bounceTransition1.toXProperty().setValue(1.1);
            bounceTransition1.fromYProperty().setValue(1);
            bounceTransition1.toYProperty().setValue(1.1);
            ScaleTransition bounceTransition2 = new ScaleTransition(Duration.millis(100), label);
            bounceTransition2.fromXProperty().setValue(1.1);
            bounceTransition2.toXProperty().setValue(1);
            bounceTransition2.fromYProperty().setValue(1.1);
            bounceTransition2.toYProperty().setValue(1);
            new SequentialTransition(bounceTransition1, bounceTransition2).play();
            setBlockStyleClass(currentRow, currentColumn, "tile-with-letter");
            if (currentColumn < MAX_COLUMN)
                currentColumn++;
        }
    }

    // xử lí submit
    private void onEnterPressed() {
        for (int i = 1; i <= MAX_COLUMN; i++) {
            getLabel(currentRow, i).setTranslateX(0.0);
        }
        if (currentRow <= MAX_ROW && currentColumn == MAX_COLUMN) {
            String guess = getWordFromCurrentRow().toLowerCase();
            if (guess.equals(wordle.getAnswer())) {
                updateRowColor(currentRow, "win");
                updateScore(true);
                updateKeyboardColor();
            } else if (wordle.valid(guess)) {
                if (currentRow == MAX_ROW) {
                    updateScore(false);
                    updateRowColor(currentRow, "lose");
                } else {
                    updateRowColor(currentRow, "playing");
                }
                updateKeyboardColor();

                currentRow++;
                currentColumn = 1;
            } else {
                SequentialTransition seq = new SequentialTransition();
                Invalid.setVisible(true);
                FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(50), Invalid);
                fadeTransition1.setFromValue(0.1);
                fadeTransition1.setToValue(1);
                FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(3000), Invalid);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                seq.getChildren().add(fadeTransition1);
                seq.getChildren().add(fadeTransition2);
                seq.setOnFinished(e -> Invalid.setVisible(false));
                seq.play();

                for (int i = 1; i <= MAX_COLUMN; i++) {
                    Label currentBlock = getLabel(currentRow, i);
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(0.1), new KeyValue(currentBlock.translateXProperty(), -5.0)),
                            new KeyFrame(Duration.seconds(0.2), new KeyValue(currentBlock.translateXProperty(), 5.0)),
                            new KeyFrame(Duration.seconds(0.3), new KeyValue(currentBlock.translateXProperty(), -5.0)),
                            new KeyFrame(Duration.seconds(0.4), new KeyValue(currentBlock.translateXProperty(), 5.0)),
                            new KeyFrame(Duration.seconds(0.5), new KeyValue(currentBlock.translateXProperty(), 0))
                    );
                    timeline.play();
                }
            }
        }
    }

    public void reset() {
        wordle.generateRandomWord();
        Label label;
        for (Node child : gridPane.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.setText("");
                label.getStyleClass().add("default-tile");
            }

        for (Node child : keyboardRow1.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.getStyleClass().add("keyboard-tile");
            }

        for (Node child : keyboardRow2.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.getStyleClass().add("keyboard-tile");
            }

        for (Node child : keyboardRow3.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                if (label.getText() == "↵" || label.getText() == "←") continue;
                label.getStyleClass().clear();
                label.getStyleClass().add("keyboard-tile");
            }

        currentColumn = 1;
        currentRow = 1;

        handleVisible(true);
        WinningWord.setText(wordle.getAnswer().toUpperCase());
        scoreWordle.setNum_play(scoreWordle.getNum_play() + 1);
        System.out.println(wordle.getAnswer());
    }

    @FXML
    public void handleRestart() {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), restartIcon);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setOnFinished(ae -> reset());
        rotateTransition.play();
    }

    public void updateScore(boolean win) {
        long[] guess = {scoreWordle.getGuess1(), scoreWordle.getGuess2(), scoreWordle.getGuess3(), scoreWordle.getGuess4(),
                scoreWordle.getGuess5(), scoreWordle.getGuess6()};
        try {
            if (win) {
                long streak = scoreWordle.getStreak() + 1;
                long num_win = scoreWordle.getNum_win() + 1;
                long played = scoreWordle.getNum_play();
                System.out.println(currentRow);
                guess[currentRow - 1]++;
                for (int i = 0; i < 6; i++) {
                    System.out.println(guess[i]);
                }
                ScoreWordle newScore = new ScoreWordle(CurrentUser, streak, played, num_win, guess);
                System.out.println(newScore.getUser_id() + " " + newScore.getNum_win() + " " + newScore.getNum_play() + " " + newScore.getStreak() +
                        " " + newScore.getGuess1() + " " + newScore.getGuess2() + " " + newScore.getGuess3() + " " + newScore.getGuess4() + " " + newScore.getGuess5() + " " + newScore.getGuess6());
                ScoreWordleDAO.addScoreWordle(newScore);
            } else {
                scoreWordle.setStreak(0);
                ScoreWordleDAO.addScoreWordle(scoreWordle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleTryAgain() {
        reset();
    }

    @FXML
    public void handleStatisticIcon() {
        Stage Statistic = new Stage();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/WordleStatistic.fxml"));
            Statistic.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the title of the new Stage
        Statistic.setTitle("Wordle statistic");
        //Instruction.initStyle(StageStyle.TRANSPARENT);

        // Show the new Stage
        Statistic.show();
    }

    public void handleVisible(boolean status) {
        gridPane.setVisible(status);
        keyboardRow1.setVisible(status);
        keyboardRow2.setVisible(status);
        keyboardRow3.setVisible(status);
        restartIcon.setVisible(status);

        ResultBack.setVisible(!status);
        TryAgain.setVisible(!status);
        GameStatus.setVisible(!status);
        WinningWord.setVisible(!status);
        Invalid.setVisible(false);
    }
}

