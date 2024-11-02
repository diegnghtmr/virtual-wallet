package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class StatisticsManagementViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnOpenAverageBalance;

    @FXML
    private Button btnOpenCommonExpenses;

    @FXML
    private Button btnOpenFrecuentTransactions;

    @FXML
    void onHome(ActionEvent event) {
        loadView("adminData-view.fxml");
    }

    @FXML
    void onOpenAverageBalance(ActionEvent event) {
        loadView("graphic-average-balance-view.fxml");
    }

    @FXML
    void onOpenCommonExpenses(ActionEvent event) {
        loadView("graphic-common-expenses-view.fxml");
    }

    @FXML
    void onOpenFrecuentTransactions(ActionEvent event) {
        loadView("graphic-transactions-frecuentes-view.fxml");
    }

    private void loadView(String fxmlFile) {
        try {

            Parent newView = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene currentScene = btnHome.getScene();
            currentScene.setRoot(newView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
    }
}
