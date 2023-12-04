package Dictionary.DictionaryController;

import Dictionary.DictionaryApplication;
import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static Dictionary.DatabaseConn.CurrentUser;
import static Dictionary.DatabaseConn.UserDAO;

public class LogInController {
    @FXML
    private JFXButton LogInButton;

    @FXML
    private PasswordField password;

    @FXML
    private Text signUpPage, LogInError;

    @FXML
    private TextField username;

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        String userid = username.getText();
        String pass = password.getText();
        if (!userid.isBlank() && !pass.isBlank() && UserDAO.checkValidUser(userid, pass)) {
            CurrentUser = userid;
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
            pause.setOnFinished(e -> {
                try {
                    Parent root = DictionaryApplication.loadFXML("/Views/Dictionary.fxml");
                    Stage stage = (Stage) LogInButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            pause.play();
        } else {
            LogInError.setVisible(true);
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
            visiblePause.setOnFinished(e -> LogInError.setVisible(false));
            visiblePause.play();
        }
    }


    @FXML
    void changetoSignUp(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SignUp.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) LogInButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

