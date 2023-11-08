package Dictionary.DictionaryController;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;

public class sidePanelController implements Initializable {
    @FXML
    private ImageView closeMenu;

    @FXML
    private ImageView addWordMenu, Game1Menu, searchMenu;

    private DictionaryController dictionaryController;

    public void setDictionaryController(DictionaryController dictionaryController) {
        this.dictionaryController = dictionaryController;
    }

    private JFXDrawer drawer; // You need to pass the drawer instance from searchController to here

    public void setDrawer(JFXDrawer drawer) {
        this.drawer = drawer;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeMenu.setCursor(Cursor.HAND);
        closeMenu.setOnMouseClicked(event -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), closeMenu);
            tt.setByY(-10);
            tt.setCycleCount(2);
            tt.setAutoReverse(true);
            tt.play();

            if(drawer.isOpened()) {
                drawer.close();
            }
        });
        setupImageView(searchMenu, "/Views/Dictionary.fxml");
        setupImageView(addWordMenu, "/Views/addWord.fxml");
        setupImageView(Game1Menu, "/Views/QuizUI.fxml");
    }

    public void setupImageView(ImageView imageView, String scenePath) {
        imageView.setCursor(Cursor.HAND);
        imageView.setOnMouseEntered(event -> {
            // This is the color overlay when the mouse enters the ImageView
            imageView.setEffect(new ColorAdjust(0, 0, -0.5, 0));
        });

        imageView.setOnMouseExited(event -> {
            // Remove the color overlay when the mouse exits the ImageView
            imageView.setEffect(null);
        });

        imageView.setOnMouseClicked(event -> {
            imageView.setEffect(new ColorAdjust(0, -1, 0, 0));

            // Load the specified scene
            dictionaryController.showComponent(scenePath);
            imageView.setEffect(null);
        });
    }
}
