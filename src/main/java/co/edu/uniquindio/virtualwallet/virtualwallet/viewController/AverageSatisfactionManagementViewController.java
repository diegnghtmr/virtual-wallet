package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AverageSatisfactionManagementController;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AverageSatisfactionManagementViewController {

    AverageSatisfactionManagementController averageSatisfactionManagementController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnReturn;

    @FXML
    private Label labelPercentage;

    @FXML
    private ProgressBar pbSatisfactionBar;


    @FXML
    void onReturn(ActionEvent event) {
        Stage currentStage = (Stage) btnReturn.getScene().getWindow();

        if (currentStage != null) {
            currentStage.close();
        }

    }

    @FXML
    void initialize() {
        averageSatisfactionManagementController = new AverageSatisfactionManagementController();
        getAverageRating();

    }

    private void getAverageRating() {
        double averageRating = averageSatisfactionManagementController.getAverageRating();

        double normalizedRating = averageRating / 5.0;
        pbSatisfactionBar.setProgress(normalizedRating);

        int percentage = (int) (normalizedRating * 100);
        labelPercentage.setText(percentage + "%");
    }

}
