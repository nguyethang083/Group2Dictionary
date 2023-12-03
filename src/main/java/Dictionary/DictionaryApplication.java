package Dictionary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import Dictionary.DictionaryController.WordleController;

import java.io.IOException;
import java.util.Objects;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = loadFXML("/Views/LogIn.fxml");
        primaryStage.setTitle("VLexi Dictionary App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
