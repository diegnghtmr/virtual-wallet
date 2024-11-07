package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.OscashController;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IOscashViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class OscashViewController extends CoreViewController implements IOscashViewController {
    OscashController oscashController;
    User loggedUser;

    @FXML
    private Button btnNotification;

    @FXML
    private Button btnSendMessage;

    @FXML
    private Button btnSendRating;

    @FXML
    private ComboBox<String> cbPercentageQuality;

    @FXML
    private VBox chatContainer;

    @FXML
    private TextField txtQuestion;

    @FXML
    private TextArea txtaAnswer;

    @FXML
    public void onNotification(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/view/notification-view.fxml", "Notificaciones", ownerStage);
    }

    @FXML
    public void onSendMessage(ActionEvent event) {
        sendMessage();
    }

    @FXML
    public void onSendRating(ActionEvent event) {
        sendRating();
    }

    @FXML
    public void initialize() {
        oscashController = new OscashController();
        loggedUser = (User) Session.getInstance().getPerson();
        initializeDataComboBox();
        initView();
    }

    private void initView() {
        // Verificar si el usuario ya ha votado
        if (loggedUser.isVoted()) {
            cbPercentageQuality.setDisable(true);
            btnSendRating.setDisable(true);
            updateComboBoxWithMessage(I18n.get("notification.message.THANKS_FOR_VOTING"));
        }

        txtaAnswer.setEditable(false);
    }

    private void initializeDataComboBox() {
        ObservableList<String> percentageQuality = FXCollections.observableArrayList(
                oscashController.getPercentageQuality());

        initializeComboBox(cbPercentageQuality, percentageQuality, item -> item);
    }

    private void sendMessage() {
        
    }

    private void sendRating() {
        String selectedRating = cbPercentageQuality.getValue();
        if (selectedRating != null) {
            // Mostrar mensaje de agradecimiento
            showMessage("Gracias", "Calificación enviada",
                    "Gracias por dar tu calificación: " + selectedRating, Alert.AlertType.INFORMATION);
            
            // Deshabilitar el ComboBox y el botón de enviar calificación
            cbPercentageQuality.setDisable(true);
            btnSendRating.setDisable(true);

            // Establecer texto de agradecimiento en el ComboBox
            updateComboBoxWithMessage(I18n.get("notification.message.THANKS_FOR_VOTING"));

            // Marcar que el usuario ha votado y guardar el estado
            loggedUser.setVoted(true);
            oscashController.addVotedUser(selectedRating);

            // Crear y enviar la notificación de agradecimiento
            NotificationUtil thankYouNotification = NotificationUtil.builder()
                    .message(I18n.get("notification.message.THANKS_FOR_RATING"))
                    .date(LocalDate.now())
                    .type(NotificationType.INFORMATION)
                    .build();
            loggedUser.update(thankYouNotification);
        } else {
            showMessage("Error", "Calificación no enviada",
                    "Por favor selecciona una calificación antes de enviar.", Alert.AlertType.WARNING);
        }
    }

    private void updateComboBoxWithMessage(String message) {
        cbPercentageQuality.getItems().clear();
        cbPercentageQuality.setValue(message);
        cbPercentageQuality.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(message);
                } else {
                    setText(item);
                }
            }
        });
    }

}
