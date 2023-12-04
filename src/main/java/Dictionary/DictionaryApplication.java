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
    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        //Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Hello!");
        //stage.setScene(scene);
        //stage.show();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/Dictionary.fxml")));
        primaryStage.setTitle("VLexi Dictionary App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}


