package co.edu.uniquindio.virtualwallet.virtualwallet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VirtualWalletApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualWalletApplication.class.getResource("/user-container-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BuckTrack");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}