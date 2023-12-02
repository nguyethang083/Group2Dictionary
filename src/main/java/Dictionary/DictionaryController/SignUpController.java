package Dictionary.DictionaryController;

import Dictionary.Entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Dictionary.DatabaseConn.UserDAO;

public class SignUpController implements Initializable {
    @FXML
    private TextField firstnamefill, lastnamefill, usernamefill;

    @FXML
    private Label invalidLabel;

    @FXML
    private PasswordField passwordfill;

    @FXML
    private ProgressBar loading;

    private Timeline timeline;
    private Stage stage;

    public String getUsernamefill() {
        return usernamefill.getText();
    }

    @FXML
    void signupButtonOnAction(ActionEvent event) throws SQLException {
        if (!usernamefill.getText().isBlank() && !passwordfill.getText().isBlank() && !firstnamefill.getText().isBlank() && !lastnamefill.getText().isBlank())
            signup();
        else
            invalidLabel.setText("Please enter your information.");
    }

    public void signup() throws SQLException {
        String first = firstnamefill.getText();
        String last = lastnamefill.getText();
        String user = usernamefill.getText();
        String pass = passwordfill.getText();
        User newUser = new User(user, first, last, pass);
        if (UserDAO.addUser(newUser)) {
            invalidLabel.setText("User has been registered successfully!");
            // Xử lí sau khi sign up thành công...
        }
        else invalidLabel.setText("Account already exists. Please try again.");
    }

    private void loading(Scene scene) {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            loading.setOpacity(1);
            loading.setProgress(loading.getProgress() + 0.25);
            if (loading.getProgress() >= 1.0) {
                timeline.stop();
                //Pauses 1 second.
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> stage.setScene(scene));
                pause.play();
            }
        }));
        //Set cycle of timeline
        timeline.setCycleCount(-1);
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernamefill.setFocusTraversable(false);
        passwordfill.setFocusTraversable(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void returnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/data/fxml/background.fxml"));
            Parent root = fxmlLoader.load();

            LogInController welcomeController = fxmlLoader.getController();
            welcomeController.initializeStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
