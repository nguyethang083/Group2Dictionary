package Dictionary.DictionaryController;

import Dictionary.Features.TranslateAPI;
import Dictionary.Features.VoiceAPI;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class TranslateController {

    @FXML
    private TextArea textToTranslate, translatedText;

    @FXML
    private JFXButton translateButton;

    @FXML
    private Rectangle transContainer;

    @FXML
    private ImageView swapIcon;

    @FXML
    private ComboBox<String> sourceLanguageComboBox;

    @FXML
    private ComboBox<String> targetLanguageComboBox;

    @FXML
    private ImageView VoiceLeft;

    @FXML
    private ImageView VoiceRight;

    private Map<String, String> languageCodes;

    @FXML
    public void initialize() {
        ObservableList<String> languages = FXCollections.observableArrayList("French", "Vietnamese", "Korean", "English", "Japanese", "Chinese");
        sourceLanguageComboBox.setItems(languages);
        targetLanguageComboBox.setItems(languages);

        sourceLanguageComboBox.setValue("Vietnamese");
        targetLanguageComboBox.setValue("English");

        languageCodes = new HashMap<>();
        languageCodes.put("French", "fr");
        languageCodes.put("Vietnamese", "vi");
        languageCodes.put("Korean", "ko");
        languageCodes.put("English", "en");
        languageCodes.put("Japanese", "ja");
        languageCodes.put("Chinese", "zh");

        translateButton.setVisible(false);
        transContainer.setVisible(false);
        VoiceLeft.setVisible(false);
        VoiceRight.setVisible(false);

        textToTranslate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                translateButton.setVisible(true);
                transContainer.setVisible(true);
                translateButton.setDisable(false);
                VoiceLeft.setVisible(true);
                VoiceLeft.setDisable(false);
            } else {
                translateButton.setVisible(false);
                transContainer.setVisible(false);
                translateButton.setDisable(true);
                VoiceLeft.setVisible(false);
                VoiceLeft.setDisable(true);
            }
        });

        translatedText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                VoiceRight.setVisible(true);
                VoiceRight.setDisable(false);
            } else {
                VoiceRight.setVisible(false);
                VoiceRight.setDisable(true);
            }
        });
    }

    @FXML
    void swapLanguages(MouseEvent event) {
        String temp = sourceLanguageComboBox.getValue();
        sourceLanguageComboBox.setValue(targetLanguageComboBox.getValue());
        targetLanguageComboBox.setValue(temp);
    }

    @FXML
    void translateText(MouseEvent event) {
        String sourceText = textToTranslate.getText();
        String sourceLanguage = languageCodes.get(sourceLanguageComboBox.getValue());
        String targetLanguage = languageCodes.get(targetLanguageComboBox.getValue());

        // Create a new thread for the translation process
        Thread thread = new Thread(() -> {
            try {
                String translation = TranslateAPI.translateWord(sourceText, sourceLanguage, targetLanguage);

                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    translatedText.setText(translation);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Start the thread
        thread.start();
    }

    @FXML
    void PlayVoiceT (MouseEvent event) {
        String targetLanguage = languageCodes.get(targetLanguageComboBox.getValue());

        // Create a new thread for the voiceplay process
        Thread thread = new Thread(() -> {
            try {
                VoiceAPI.textToSpeech(translatedText.getText(),targetLanguage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Start the thread
        thread.start();
    }

    @FXML
    void PlayVoiceS (MouseEvent event) {
        String sourceLanguage = languageCodes.get(sourceLanguageComboBox.getValue());

        // Create a new thread for the voiceplay process
        Thread thread = new Thread(() -> {
            try {
                VoiceAPI.textToSpeech(textToTranslate.getText(),sourceLanguage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Start the thread
        thread.start();
    }
}
