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
    private Label usernameNotification;

    @FXML
    private Label nameNotification;

    @FXML
    private Label passwordNotification;

    @FXML
    private PasswordField passwordfill;

    @FXML
    void signupButtonOnAction(ActionEvent event) throws SQLException {
        if (!usernamefill.getText().isBlank() && !passwordfill.getText().isBlank() && !firstnamefill.getText().isBlank() && !lastnamefill.getText().isBlank())
            signup();
        else {
            if (usernamefill.getText().isBlank()) usernameNotification.setText("Please enter your username.");
            if (firstnamefill.getText().isBlank() || lastnamefill.getText().isBlank()) nameNotification.setText("Please enter your name.");
            if (passwordfill.getText().isBlank()) passwordNotification.setText("Please enter your password.");
        }

    }

    public void signup() throws SQLException {
        String first = firstnamefill.getText();
        String last = lastnamefill.getText();
        String user = usernamefill.getText();
        String pass = passwordfill.getText();
        User newUser = new User(user, first, last, pass);
        if (UserDAO.addUser(newUser)) {
//            invalidLabel.setText("User has been registered successfully!");
            // Xử lí sau khi sign up thành công...
       } //else invalidLabel.setText("Account already exists. Please try again.");
    }

    private void setUsernameNotification() {
        usernamefill.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                usernameNotification.setText("Please enter your username.");
            } else if (UserDAO.checkNewUser(newValue)) {
                usernameNotification.setText("");
            } else {
                usernameNotification.setText("This username has been existed.");
            }
        });
    }

    private void setNameNotification() {
        firstnamefill.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                nameNotification.setText("Please enter your name.");
            } else if (lastnamefill.getText().isEmpty()) {
                nameNotification.setText("Please enter your name.");
            } else {
                nameNotification.setText("");
            }
        });

        lastnamefill.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                nameNotification.setText("Please enter your name.");
            } else if (firstnamefill.getText().isEmpty()) {
                nameNotification.setText("Please enter your name.");
            } else {
                nameNotification.setText("");
            }
        });
    }

    private void setPasswordNotification() {
        passwordfill.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                passwordNotification.setText("Please enter your password.");
            } else {
                usernameNotification.setText("");
            }
        });
    }

    @FXML
    private void returnToSignIn() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernamefill.setFocusTraversable(false);
        passwordfill.setFocusTraversable(false);
        setUsernameNotification();
        setNameNotification();
        setPasswordNotification();
    }

}
