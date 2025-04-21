package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.CommonExpensesController;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CommonExpensesViewController extends CoreViewController {
    private Stage currentStage;
    CommonExpensesController  commonExpensesController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnReturn;

    @FXML
    private PieChart pc;

    @FXML
    private Label pcCommonExpenses;

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
        commonExpensesController = new CommonExpensesController();
        displayCommonExpenses();

    }

    private void displayCommonExpenses() {
        pc.getData().clear();
        Map<String, Double> expensesByCategory = commonExpensesController.calculateTotalExpenses();

        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
            pc.getData().add(data);
        }
    }


}
