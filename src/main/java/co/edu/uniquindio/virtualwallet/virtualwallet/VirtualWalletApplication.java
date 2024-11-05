package co.edu.uniquindio.virtualwallet.virtualwallet;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.ModelFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VirtualWalletApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualWalletApplication.class.getResource("/view/startup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BuckTrack");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Llamar al método para guardar los datos al cerrar la aplicación
        ModelFactory.getInstance().generateSerialization();
        System.out.println("Datos guardados al cerrar la aplicación.");
    }

    public static void main(String[] args) {
        launch();
    }
}