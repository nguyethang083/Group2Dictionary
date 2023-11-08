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
    private ImageView addWordMenu, Game1Menu;

    private searchController searchController;

    public void setSearchController(searchController searchController) {
        this.searchController = searchController;
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
        addWordMenu.setCursor(Cursor.HAND);
        addWordMenu.setOnMouseEntered(event -> {
            // This is the color overlay when the mouse enters the ImageView
            addWordMenu.setEffect(new ColorAdjust(0, 0, -0.5, 0));
        });

        addWordMenu.setOnMouseExited(event -> {
            // Remove the color overlay when the mouse exits the ImageView
            addWordMenu.setEffect(null);
        });

        addWordMenu.setOnMouseClicked(event -> {
            // This is the color overlay when the ImageView is clicked
            addWordMenu.setEffect(new ColorAdjust(0, -1, 0, 0));

            // Call your method to change the scene
            searchController.showComponent("/Views/addWord.fxml");

            // Remove the color overlay after the click action is done
            addWordMenu.setEffect(null);
        });

        Game1Menu.setOnMouseClicked(event -> {
            // This is the color overlay when the ImageView is clicked
            Game1Menu.setEffect(new ColorAdjust(0, -1, 0, 0));

            // Call your method to change the scene
            searchController.showComponent("/Views/QuizUI.fxml");

            // Remove the color overlay after the click action is done
            Game1Menu.setEffect(null);
        });
    }
}
