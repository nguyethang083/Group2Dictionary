package Dictionary.DictionaryController;

import Dictionary.DictionaryApplication;
import com.jfoenix.controls.JFXDrawer;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class sidePanelController implements Initializable {
    @FXML
    private ImageView closeMenu, exitApp;

    @FXML
    private ImageView addWordMenu, Game1Menu, searchMenu, Game2Menu, translateMenu, RecentsMenu;

    private DictionaryController dictionaryController;
    private JFXDrawer drawer;

    public void setDictionaryController(DictionaryController dictionaryController) {
        this.dictionaryController = dictionaryController;
    }

    public void setDrawer(JFXDrawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeMenu.setOnMouseClicked(event -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), closeMenu);
            tt.setByY(-10);
            tt.setCycleCount(2);
            tt.setAutoReverse(true);
            tt.play();

            if (drawer.isOpened()) {
                drawer.close();
                drawer.setMouseTransparent(true);
            }
        });
        setupImageView(searchMenu, "/Views/Dictionary.fxml");
        setupImageView(addWordMenu, "/Views/addWord.fxml");
        setupImageView(Game1Menu, "/Views/QuizMenu.fxml");
        setupImageView(Game2Menu, "/Views/WordleMenu.fxml");
        setupImageView(translateMenu, "/Views/Translate.fxml");
        setupImageView(RecentsMenu, "/Views/Recents.fxml");

        Tooltip exitAppTooltip = new Tooltip("Log Out");
        exitAppTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(exitApp, exitAppTooltip);
    }

    public void setupImageView(ImageView imageView, String scenePath) {
        imageView.setOnMouseEntered(event -> {
            imageView.setEffect(new ColorAdjust(0, 0, -0.5, 0));
        });

        imageView.setOnMouseExited(event -> {
            imageView.setEffect(null);
        });

        imageView.setOnMouseClicked(event -> {
            imageView.setEffect(new ColorAdjust(0, -1, 0, 0));

            // Load the specified scene
            dictionaryController.showComponent(scenePath);

            if (drawer.isOpened()) {
                drawer.close();
                drawer.setMouseTransparent(true);
            }

            imageView.setEffect(null);
        });
    }

    @FXML
    void exitApp(MouseEvent event) {
        try {
            Parent root = DictionaryApplication.loadFXML("/Views/LogIn.fxml");
            Stage stage = (Stage) exitApp.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
