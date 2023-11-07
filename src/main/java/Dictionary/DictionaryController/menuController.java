package Dictionary.DictionaryController;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class menuController implements Initializable {
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger myHamburger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox vbox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/sidePanel.fxml")));
            drawer.setSidePane(vbox);
        } catch (IOException e) {
            System.out.println("Hai");
        }

        myHamburger.setCursor(Cursor.HAND);

        myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), myHamburger);
            st.setByX(0.15);
            st.setByY(0.15);
            st.setCycleCount(2);
            st.setAutoReverse(true);
            st.play();

            if(drawer.isOpened())
                drawer.close();
            else
                drawer.open();
        });
    }

}
