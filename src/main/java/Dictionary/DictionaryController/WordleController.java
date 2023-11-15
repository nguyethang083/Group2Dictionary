package Dictionary.DictionaryController;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.*;
import java.net.URL;

import Dictionary.Game.Wordle;

public class WordleController implements Initializable {
    private final Wordle wordle;
    private final String[] firstRowLetters = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private final String[] secondRowLetters = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    private final String[] thirdRowLetters = {"↵", "Z", "X", "C", "V", "B", "N", "M", "←"};
    private final int MAX_COLUMN = 5;
    private final int MAX_ROW = 6;
    private int CURRENT_ROW = 1;
    private int CURRENT_COLUMN = 1;

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    @FXML
    public GridPane keyboardRow2;
    @FXML
    public GridPane keyboardRow3;
    //    @FXML
//    public ImageView helpIcon;
//    @FXML
//    public ImageView githubIcon;
//    @FXML
//    public HBox titleHBox;
//    @FXML
//    public ImageView restartIcon;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createGrid();
        createKeyboard();
        wordle.generateRandomWord();
        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                onKeyPressed(event);
            }
        });
    }

    public WordleController() {
        wordle = new Wordle();
        gridPane = new GridPane();
        gridPane.requestFocus();
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

    // tạo ra giao diện keyboard
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
    private void setLabelText(int searchRow, int searchColumn, String input) {
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
    private String getLabelText(int searchRow, int searchColumn) {
        Label label = getLabel(searchRow, searchColumn);
        if (label != null)
            return label.getText().toLowerCase();
        return null;
    }

    // set style class cho chữ cái
    private void setLabelStyleClass(int searchRow, int searchColumn, String styleclass) {
        Label label = getLabel(searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().add(styleclass);
        }
    }

    //xóa style class
    private void clearLabelStyleClass(int searchRow, int searchColumn) {
        Label label = getLabel(searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    //update màu cho chữ cái
    private void updateRowColors(int searchRow) {
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
        seq.play();
    }

    //update keyboard sau khi nhập
    private void updateKeyboardColors() {
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
            else if (wordle.getAnswer().contains("" + currentCharacter))
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
            input.append(getLabelText(CURRENT_ROW, j));
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
        if ((CURRENT_COLUMN == MAX_COLUMN || CURRENT_COLUMN == 1)
                && !Objects.equals(getLabelText(CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(CURRENT_ROW, CURRENT_COLUMN, "");
            clearLabelStyleClass(CURRENT_ROW, CURRENT_COLUMN);
            setLabelStyleClass(CURRENT_ROW, CURRENT_COLUMN, "default-tile");
        } else if ((CURRENT_COLUMN > 1 && CURRENT_COLUMN < MAX_COLUMN)
                || (CURRENT_COLUMN == MAX_COLUMN && Objects.equals(getLabelText(CURRENT_ROW, CURRENT_COLUMN), ""))) {
            CURRENT_COLUMN--;
            setLabelText(CURRENT_ROW, CURRENT_COLUMN, "");
            clearLabelStyleClass(CURRENT_ROW, CURRENT_COLUMN);
            setLabelStyleClass(CURRENT_ROW, CURRENT_COLUMN, "default-tile");
        }
    }

    private void onLetterPressed(KeyEvent keyEvent) {
        if (Objects.equals(getLabelText(CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(CURRENT_ROW, CURRENT_COLUMN, keyEvent.getText());
            System.out.println(keyEvent.getText());
            Label label = getLabel(CURRENT_ROW, CURRENT_COLUMN);
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
            setLabelStyleClass(CURRENT_ROW, CURRENT_COLUMN, "tile-with-letter");
            if (CURRENT_COLUMN < MAX_COLUMN)
                CURRENT_COLUMN++;
        }
    }

    // xử lí submit
    private void onEnterPressed() {
        if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
            String guess = getWordFromCurrentRow().toLowerCase();
            if (guess.equals(wordle.getAnswer())) {
                updateRowColors(CURRENT_ROW);
                updateKeyboardColors();

            } else if (wordle.valid(guess)) {
                updateRowColors(CURRENT_ROW);
                updateKeyboardColors();
                CURRENT_ROW++;
                CURRENT_COLUMN = 1;
            }
        }
    }
}

