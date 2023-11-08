package Dictionary.DictionaryController;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;

public class sidePanelController implements Initializable {
    @FXML
    private ImageView closeMenu;

    @FXML
    private ImageView addWordMenu;

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
        addWordMenu.setOnMouseClicked(event -> searchController.showComponent("/Views/addWord.fxml"));
    }
}
