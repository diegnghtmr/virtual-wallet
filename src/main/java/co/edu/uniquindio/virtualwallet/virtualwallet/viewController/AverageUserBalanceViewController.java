package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AverageUserBalanceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AverageUserBalanceViewController extends CoreViewController {

    private Stage currentStage; // Para guardar la referencia de la ventana actual


    AverageUserBalanceController averageUserBalanceController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnReturn;

    @FXML
    private BarChart<String, Double> bcAverageBalance;

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
        averageUserBalanceController = new AverageUserBalanceController();
        displayAverageBalance();

    }

    private void displayAverageBalance() {
        double averageBalance = averageUserBalanceController.getAverageUserBalance(); // Obtener el saldo promedio del controlador

        System.out.println("Average Balance: " + averageBalance);

        // Agregar el saldo promedio al gráfico
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Saldo Promedio");
        series.getData().add(new XYChart.Data<>("Promedio", averageBalance));

        bcAverageBalance.getData().clear();  // Limpiar el gráfico antes de añadir datos nuevos
        bcAverageBalance.getData().add(series);


    }


}
