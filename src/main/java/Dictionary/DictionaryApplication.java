package Dictionary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import Dictionary.DictionaryController.WordleController;

import java.util.Objects;

public class DictionaryApplication extends Application {
    private static Parent dictionary;

    @Override
    public void init() throws Exception {
        super.init();
        // Preload the Dictionary scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dictionary.fxml"));
        dictionary = loader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/LogIn.fxml")));
        primaryStage.setTitle("VLexi Dictionary App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static Parent getDictionary() {
        return dictionary;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
