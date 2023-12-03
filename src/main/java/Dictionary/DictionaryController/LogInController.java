package Dictionary.DictionaryController;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static Dictionary.DatabaseConn.UserDAO;
import static Dictionary.DatabaseConn.CurrentUser;
public class LogInController {
    private Stage stage;

    @FXML
    private Label invalidLabel;

    @FXML
    private PasswordField passwordfill;

    @FXML
    private TextField usernamefill;

    @FXML
    private ProgressBar loading;

    private Timeline timeline;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private Button signupbutton;

    public AnchorPane getMainpane() {
        return mainpane;
    }

    public String getUsernamefill() {
        return usernamefill.getText();
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        String userid = usernamefill.getText();
        String pass = passwordfill.getText();
        if (!userid.isBlank() && !pass.isBlank()) {
            if (UserDAO.checkValidUser(userid, pass)) {
                invalidLabel.setText("Congratulations!!!");
                CurrentUser = userid;
                // chuyển sang scene chính
            }
            invalidLabel.setText("Invalid Login. Please try again.");
        } else {
            invalidLabel.setText("Please enter username and password.");
        }
    }

    @FXML
    public void signupButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SignUpController.class.getResource("/Views/signup.fxml"));
            Parent root = fxmlLoader.load();
            SignUpController signupController = fxmlLoader.getController();
            signupController.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    private void loading(Scene scene) {
        if (timeline != null) {
            timeline.stop();
        }
        Duration Duration = new Duration(1);
        timeline = new Timeline(new KeyFrame(Duration, e -> {
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

    public void initializeStage(Stage window) {
        this.stage = window;
    }
}
