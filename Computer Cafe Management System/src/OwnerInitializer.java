import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class OwnerInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/OwnerDashBoardForm.fxml"))));
        primaryStage.setTitle("Computer Cafe Management System");
        Image image = new Image("/assert/images/logo.png");
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }
}
