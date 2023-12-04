package Dictionary.DictionaryController;

import Dictionary.DictionaryApplication;
import Dictionary.Entities.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private Text returnSignIn;

    @FXML
    private PasswordField passwordfill;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void signupButtonOnAction(ActionEvent event) throws SQLException {
        if (!usernamefill.getText().isBlank() && !passwordfill.getText().isBlank() && !firstnamefill.getText().isBlank() && !lastnamefill.getText().isBlank()) {
            progressBar.setVisible(true);
            signup();
        }
        else {
            if (usernamefill.getText().isBlank()) usernameNotification.setText("Please enter your username.");
            if (firstnamefill.getText().isBlank() || lastnamefill.getText().isBlank())
                nameNotification.setText("Please enter your name.");
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
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i <= 100; i++) {
                        final int progress = i;
                        Platform.runLater(() -> progressBar.setProgress(progress / 100.0));
                        Thread.sleep(7);
                    }
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                try {
                    Parent root = DictionaryApplication.loadFXML("/Views/LogIn.fxml");
                    Stage stage = (Stage) firstnamefill.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                    progressBar.setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            new Thread(task).start();
        }
    }

    private void setUsernameNotification() {
        usernamefill.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
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
            if (newValue.isEmpty()) {
                nameNotification.setText("Please enter your name.");
            } else if (lastnamefill.getText().isEmpty()) {
                nameNotification.setText("Please enter your name.");
            } else {
                nameNotification.setText("");
            }
        });

        lastnamefill.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
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
            if (newValue.isEmpty()) {
                passwordNotification.setText("Please enter your password.");
            } else {
                passwordNotification.setText("");
            }
        });
    }

    @FXML
    private void returnToSignIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LogIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnSignIn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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