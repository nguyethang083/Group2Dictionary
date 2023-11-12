package Dictionary.DictionaryController;

import Dictionary.Features.Voice;
import Dictionary.Entities.EngWord;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import static Dictionary.Entities.AllWord.allWord;
import static Dictionary.DatabaseConn.WordDAO;

public class DictionaryController implements Initializable {
    @FXML
    private AnchorPane content, container;

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
    private ImageView exampleContainer, synonymContainer, voiceButton, deleteAll, editButton, deleteIcon, saveButton, intro, exitProgram;

    @FXML
    private Text examplePrompt, synonymPrompt;

    @FXML
    private Rectangle rectangle;

    private String selectedWord;

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

    private void initImageViews() {
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
        comboBox.setOnAction(event -> updateView());
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
    public void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(DictionaryController.class.getResource(path)));
            setNode(component);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                intro.setVisible(false);
                setVisibility(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setVisibility(boolean isVisible) {
        List<Node> nodes = Arrays.asList(
                exampleContainer, synonymContainer, examplePrompt, definitionPrompt, synonymPrompt, rectangle,
                voiceButton, editButton, deleteIcon, meaning, wordLabel, phonetic, example, synonym, partsofspeech
        );

        for (Node node : nodes) {
            node.setVisible(isVisible);
        }
    }



    @FXML
    void playVoice(MouseEvent event) {
        if (selectedWord != null) {
            Voice.playVoice(selectedWord);
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
