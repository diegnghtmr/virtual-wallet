package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IOscashViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class OscashViewController extends CoreViewController implements IOscashViewController {

    @FXML
    private Button btnNotification;

    @FXML
    private Button btnSendMessage;

    @FXML
    private Button btnSendRating;

    @FXML
    private ComboBox<?> cbPercentageQuality;

    @FXML
    private TextField txtQuestion;

    @FXML
    private TextArea txtaAnswer;

    @FXML
    public void onNotification(ActionEvent event) {

    }

    @FXML
    public void onSendMessage(ActionEvent event) {

    }

    @FXML
    public void onSendRating(ActionEvent event) {

    }

    @FXML
    public void initialize() {

    }

}
