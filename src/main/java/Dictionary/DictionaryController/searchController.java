package Dictionary.DictionaryController;

import Dictionary.DictionaryCommandLine.VoiceFunction;
import Dictionary.models.EngWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import static Dictionary.models.AllWord.allWord;
import static Dictionary.DatabaseConn.WordDAO;

public class searchController implements Initializable {
    @FXML
    public AnchorPane container;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger myHamburger;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextArea meaning, synonym, example;

    @FXML
    private TextField partsofspeech, phonetic, definitionPrompt, searchField;

    @FXML
    private Label wordLabel;
    @FXML
    private ImageView exampleContainer, synonymContainer, voiceButton, deleteAll, editButton, deleteIcon, saveButton;

    @FXML
    private Text examplePrompt, synonymPrompt;

    @FXML
    private Rectangle rectangle;

    private String selectedWord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> words = FXCollections.observableArrayList();
        for (EngWord engword : allWord) {
            words.add(engword.getWord());
        }
        comboBox.setItems(words);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateComboBox(newValue));
        comboBox.setOnAction(event -> updateView());

        myHamburger.setCursor(Cursor.HAND);

        myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            if (!drawer.isOpened())
                drawer.open();
        });

        myHamburger.setOnMouseClicked(event -> myHamburger.setVisible(false));
        drawer.setOnDrawerClosed(event -> myHamburger.setVisible(true));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/sidePanel.fxml"));
            VBox vbox = loader.load();
            drawer.setSidePane(vbox);

            sidePanelController sidePanelController = loader.getController();
            sidePanelController.setDrawer(drawer);
            sidePanelController.setSearchController(this);

            //Node sidePanel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }
    @FXML
    public void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(searchController.class.getResource(path)));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateComboBox(String newValue) {
        if (newValue.isEmpty()) {
            comboBox.hide();
        } else {
            ObservableList<String> filteredWords = getFilteredWords(newValue);
            comboBox.setItems(filteredWords);
            if (!filteredWords.isEmpty() && !comboBox.isShowing()) {
                comboBox.show();
            } else if (filteredWords.isEmpty()) {
                comboBox.hide();
            }
        }
    }

    private ObservableList<String> getFilteredWords(String newValue) {
        ObservableList<String> filteredWords = FXCollections.observableArrayList();
        try {
            List<EngWord> words = WordDAO.containWordByString(newValue);
            for (EngWord e : words) {
                filteredWords.add(e.getWord());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filteredWords;
    }
    @FXML
    void editWord(MouseEvent event) {
        partsofspeech.setEditable(true);
        example.setEditable(true);
        meaning.setEditable(true);
        saveButton.setVisible(true);
    }

    @FXML
    void saveWord(MouseEvent event) {
        String newType = partsofspeech.getText();
        String newMeaning = meaning.getText();
        String newExample = example.getText();

        try {
            WordDAO.updateType(selectedWord, newType);
            WordDAO.updateExample(selectedWord, newExample);
            WordDAO.updateMeaning(selectedWord, newMeaning);
            partsofspeech.setEditable(false);
            meaning.setEditable(false);
            example.setEditable(false);
            saveButton.setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void deleteWord(MouseEvent event) throws SQLException {
        WordDAO.deleteWordByString(selectedWord);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Xóa thành công");
        alert.showAndWait();
        setVisibility(false);
    }
    private void updateView() {
        selectedWord = comboBox.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            try {
                EngWord engWord = WordDAO.queryWordByString(selectedWord);
                wordLabel.setText(engWord.getWord());
                phonetic.setText(engWord.getPronunciation());
                partsofspeech.setText(engWord.getType().toLowerCase());
                meaning.setText(engWord.getMeaning());
                synonym.setText(engWord.getSynonym());
                example.setText(engWord.getExample());
                setVisibility(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setVisibility(boolean isVisible) {
        exampleContainer.setVisible(isVisible);
        synonymContainer.setVisible(isVisible);
        examplePrompt.setVisible(isVisible);
        definitionPrompt.setVisible(isVisible);
        synonymPrompt.setVisible(isVisible);
        rectangle.setVisible(isVisible);
        voiceButton.setVisible(isVisible);
        editButton.setVisible(isVisible);
        deleteIcon.setVisible(isVisible);
    }

    @FXML
    void playVoice(MouseEvent event) {
        if (selectedWord != null) {
            VoiceFunction.playVoice(selectedWord);
        }
    }

    @FXML
    void clearSearchField(MouseEvent event) {
        searchField.clear();
    }

    @FXML
    void setOpacityPressed(MouseEvent event) {
        voiceButton.setOpacity(0.5);
    }

    @FXML
    void setOpacityReleased(MouseEvent event) {
        voiceButton.setOpacity(1.0);
    }

    @FXML
    void setOnMouseEntered(MouseEvent event) {
        voiceButton.setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited(MouseEvent event) {
        voiceButton.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void setOpacityPressed1(MouseEvent event) {
        deleteAll.setOpacity(0.5);
    }

    @FXML
    void setOpacityReleased1(MouseEvent event) {
        deleteAll.setOpacity(1.0);
    }

    @FXML
    void setOnMouseEntered1(MouseEvent event) {
        deleteAll.setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited1(MouseEvent event) {
        deleteAll.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void setOpacityPressed2(MouseEvent event) {
        editButton.setOpacity(0.5);
    }

    @FXML
    void setOpacityReleased2(MouseEvent event) {
        editButton.setOpacity(1.0);
    }

    @FXML
    void setOnMouseEntered2(MouseEvent event) {
        editButton.setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited2(MouseEvent event) {
        editButton.setCursor(Cursor.DEFAULT);
    }
}
