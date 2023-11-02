package DictionaryCommandLine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.util.Objects;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Hello!");
        //stage.setScene(scene);
        //stage.show();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/hello-view.fxml")));
        primaryStage.setTitle("Sample");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


