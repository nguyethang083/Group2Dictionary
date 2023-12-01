package Dictionary.DictionaryController;

import Dictionary.Entities.EngWord;
import Dictionary.Entities.SavedWord;
import Dictionary.Features.VoiceAPI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import static Dictionary.DatabaseConn.SavedWordDAO;
import static Dictionary.Entities.AllWord.allWord;
import static Dictionary.DatabaseConn.WordDAO;
import static Dictionary.DatabaseConn.CurrentUser;

public class DictionaryController implements Initializable {
    @FXML
    private AnchorPane content;

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
    private ImageView exampleContainer, synonymContainer, voiceButton, deleteAll, saveThisWord,
            editButton, deleteIcon, saveButton, intro, exitProgram;

    @FXML
    private Text examplePrompt, synonymPrompt, saveMyWordsMenu;

    @FXML
    private Rectangle rectangle;

    private String selectedWord;

    Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/images/saveIcon.png")).toExternalForm());
    Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/images/savedIcon.png")).toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initImageViews();
        initComboBox();
        initHamburger();
        try {
            initDrawer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSelectedWord(String word) {
        this.selectedWord = word;
    }

    private void initImageViews() {
        saveThisWord.setImage(image1);
        List<ImageView> imageViews = Arrays.asList(voiceButton, deleteAll, editButton, deleteIcon, saveButton);
        for (ImageView imageView : imageViews) {
            imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> imageView.setOpacity(0.5));
            imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> imageView.setOpacity(1.0));
        }
    }

    private void initComboBox() {
        ObservableList<String> words = FXCollections.observableArrayList();
        for (EngWord engword : allWord) {
            words.add(engword.getWord());
        }
        comboBox.setItems(words);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateComboBox(newValue));
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setSelectedWord(newValue);
                updateView();
            }
        });
    }

    private void initHamburger() {
        myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            if (!drawer.isOpened()) {
                drawer.open();
                drawer.setMouseTransparent(false);
            }
        });

        myHamburger.setOnMouseClicked(event -> myHamburger.setVisible(false));
        drawer.setOnDrawerClosed(event -> myHamburger.setVisible(true));
    }

    private void initDrawer() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/sidePanel.fxml"));
            VBox vbox = loader.load();
            drawer.setSidePane(vbox);

            sidePanelController sidePanelController = loader.getController();
            sidePanelController.setDrawer(drawer);
            sidePanelController.setDictionaryController(this);
    }

    public void setNode(Node node) {
        content.getChildren().clear();
        content.getChildren().add(node);
    }

    @FXML
    public Object showComponent(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane component = loader.load();
            setNode(component);
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateComboBox(String newValue) {
        if (newValue.isEmpty()) {
            comboBox.hide();
        } else {
            ObservableList<EngWord> filteredEngWords = getFilteredEngWords(newValue);
            // Extract the word strings from the EngWord objects
            ObservableList<String> filteredWords = FXCollections.observableArrayList();
            for (EngWord engWord : filteredEngWords) {
                filteredWords.add(engWord.getWord());
            }
            comboBox.setItems(filteredWords);
            if (!filteredWords.isEmpty() && !comboBox.isShowing()) {
                comboBox.show();
            } else if (filteredWords.isEmpty()) {
                comboBox.hide();
            }
        }
    }


    public ObservableList<EngWord> getFilteredEngWords(String newValue) {
        ObservableList<EngWord> filteredWords = FXCollections.observableArrayList();
        try {
            List<EngWord> words = WordDAO.containWordByString(newValue);
            for (EngWord e : words) {
                filteredWords.add(e);
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
    void saveToMyWords(MouseEvent event) throws SQLException {
        if (saveThisWord.getImage().equals(image2)) {
            saveThisWord.setImage(image1);
            SavedWord savedWord = new SavedWord();
            EngWord engWord = WordDAO.queryWordByString(selectedWord);
            long EngId = engWord.getId();
            savedWord.setEnglish_id(EngId);
            savedWord.setUser_id(CurrentUser);
            SavedWordDAO.deleteTuple(savedWord);
        } else {
            saveThisWord.setImage(image2);
            SavedWord saveWord = new SavedWord();
            EngWord engWord = WordDAO.queryWordByString(selectedWord);
            long EngId = engWord.getId();
            saveWord.setEnglish_id(EngId);
            saveWord.setUser_id(CurrentUser);
            SavedWordDAO.addSavedWord(saveWord);
        }
    }

    @FXML
    void switchToMyWords(MouseEvent event) throws SQLException {
        MyWordsController controller = (MyWordsController) showComponent("/Views/MyWords.fxml");
        controller.setCurrentUser(CurrentUser);
        List<EngWord> savedWords = SavedWordDAO.queryListWordByUser();
        controller.displaySavedWords(savedWords);
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

    // Temporary alert
    @FXML
    void deleteWord(MouseEvent event) throws SQLException {
        WordDAO.deleteWordByString(selectedWord);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Xóa thành công");
        alert.showAndWait();
        setVisibility(false);
    }

    public void updateView() {
        //selectedWord = comboBox.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            try {
                EngWord engWord = WordDAO.queryWordByString(selectedWord);
                wordLabel.setText(engWord.getWord());
                phonetic.setText(engWord.getPronunciation());
                partsofspeech.setText(engWord.getType().toLowerCase());
                meaning.setText(engWord.getMeaning());
                synonym.setText(engWord.getSynonym());
                example.setText(engWord.getExample());
                intro.setVisible(false);
                setVisibility(true);

                SavedWord savedWord = new SavedWord();
                savedWord.setEnglish_id(engWord.getId());
                savedWord.setUser_id(CurrentUser);
                if (SavedWordDAO.idExists(savedWord)) {
                    saveThisWord.setImage(image2);
                } else {
                    saveThisWord.setImage(image1);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setVisibility(boolean isVisible) {
        List<Node> nodes = Arrays.asList(
                exampleContainer, synonymContainer, examplePrompt, definitionPrompt, synonymPrompt, rectangle, saveThisWord,
                voiceButton, editButton, deleteIcon, meaning, wordLabel, phonetic, example, synonym, partsofspeech
        );

        for (Node node : nodes) {
            node.setVisible(isVisible);
        }
    }



    @FXML
    void playVoice(MouseEvent event) {
        if (selectedWord != null) {
            VoiceAPI.textToSpeech(selectedWord, "en");
        }
    }

    @FXML
    void clearSearchField(MouseEvent event) {
        searchField.clear();
    }

    @FXML
    void exitProgram(MouseEvent event) {
        Platform.exit();
    }

}
