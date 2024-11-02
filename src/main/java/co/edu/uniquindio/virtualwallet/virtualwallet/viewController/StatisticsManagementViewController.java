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

public class StatisticsManagementViewController extends CoreViewController{

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
        openWindow("adminData-view.fxml", "Datos", (Stage) btnHome.getScene().getWindow());
    }

    @FXML
    void onOpenAverageBalance(ActionEvent event) {
        openWindow("graphic-average-balance-view.fxml", "Average Balance", (Stage) btnOpenAverageBalance.getScene().getWindow());
    }

    @FXML
    void onOpenCommonExpenses(ActionEvent event) {
        openWindow("graphic-common-expenses-view.fxml", "Common Expenses", (Stage) btnOpenCommonExpenses.getScene().getWindow());
    }

    @FXML
    void onOpenFrecuentTransactions(ActionEvent event) {
        openWindow("graphic-transactions-frecuentes-view.fxml", "Frequent Transactions", (Stage) btnOpenFrecuentTransactions.getScene().getWindow());
    }

    @FXML
    void initialize() {

    }
}
