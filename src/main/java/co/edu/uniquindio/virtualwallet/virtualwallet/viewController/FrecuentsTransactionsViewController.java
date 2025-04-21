package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.FrecuentsTransactionsController;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FrecuentsTransactionsViewController extends CoreViewController {

    private Stage currentStage;

    FrecuentsTransactionsController frecuentsTransactionsController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Number> bcTransactionsFrequent;

    @FXML
    private Button btnReturn;

    @FXML
    void onReturn(ActionEvent event) {
        // Cerrar la ventana actual
        Stage currentStage = (Stage) btnReturn.getScene().getWindow();

        if (currentStage != null) {
            currentStage.close();
        }

    }

    @FXML
    void initialize() {
        frecuentsTransactionsController = new FrecuentsTransactionsController();
        displayFrecuentsTransactions();

    }

    private void displayFrecuentsTransactions() {
        bcTransactionsFrequent.getData().clear();

        List<User> usersWithMostTransactions = frecuentsTransactionsController.getUsersWithMostTransactions();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Usuarios con mas transacciones");

        for (User user : usersWithMostTransactions) {
            int transactionCount = frecuentsTransactionsController.countTransaction(user);
            series.getData().add(new XYChart.Data<>(user.getFullName(), transactionCount));
        }
        bcTransactionsFrequent.getData().add(series);

    }

}
