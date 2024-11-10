package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StatisticsManagementViewController extends CoreViewController{

    private Stage averageBalanceStage;
    private Stage commonExpensesStage;
    private Stage frequentTransactionsStage;
    private Stage LevelSatisfaction;


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
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (currentStage != null) {
            currentStage.close();
        }

        openWindow("/view/admin-data-view.fxml", "Datos del Administrador", null);
    }

    @FXML
    void onOpenAverageBalance(ActionEvent event) {
        if (averageBalanceStage == null || !averageBalanceStage.isShowing()) {
            averageBalanceStage = new Stage();
            openWindow("/view/graphic-average-balance-view.fxml", "Average Balance", averageBalanceStage);
        } else {
            averageBalanceStage.toFront();
        }
    }

    @FXML
    void onLevelSatisfaction(ActionEvent event) {
        if (LevelSatisfaction == null || !LevelSatisfaction.isShowing()) {
            LevelSatisfaction = new Stage();
            openWindow("/view/average-satisfaction-view.fxml", "Level Satisfaction", LevelSatisfaction);
        } else {
            LevelSatisfaction.toFront();
        }

    }

    @FXML
    void onOpenCommonExpenses(ActionEvent event) {
        if (commonExpensesStage == null || !commonExpensesStage.isShowing()) {
            commonExpensesStage = new Stage();
            openWindow("/view/graphic-common-expenses-view.fxml", "Common Expenses", commonExpensesStage);
        } else {
            commonExpensesStage.toFront();
        }
    }

    @FXML
    void onOpenFrecuentTransactions(ActionEvent event) {
        if (frequentTransactionsStage == null || !frequentTransactionsStage.isShowing()) {
            frequentTransactionsStage = new Stage();
            openWindow("/view/graphic-transactions-frecuentes-view.fxml", "Frequent Transactions", frequentTransactionsStage);
        } else {
            frequentTransactionsStage.toFront();
        }
    }


    @FXML
    void initialize() {

    }
}
