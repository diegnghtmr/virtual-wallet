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
        showOptions();
    }

    private void showOptions() {
        String options = "Bienvenido al Asistente Financiero Virtual.\n" +
                "Por favor, elige una opción:\n" +
                "1. Obtener sugerencias de ahorro\n" +
                "2. Obtener recomendaciones de inversión\n" +
                "3. Obtener gastos más comunes\n";
        txtaAnswer.setText(options);
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
        String inputUser = txtQuestion.getText().trim();
        if (inputUser.isEmpty()) {
            return;
        }
        int option;
        try {
            option = Integer.parseInt(inputUser);
        } catch (NumberFormatException e) {
            txtaAnswer.setText("Entrada no válida. Por favor, ingresa un número.");
            txtQuestion.clear();
            return;
        }

        switch (option) {
            case 0:
                // Reset and show options again
                showOptions();
                break;
            case 1:
                // Provide savings suggestions based on transaction history
                String savingsSuggestions = oscashController.getSavingsSuggestions(loggedUser.getId());
                txtaAnswer.setText(savingsSuggestions);
                txtaAnswer.appendText("\nIngresa 0 para volver al menú principal.");
                break;
            case 2:
                // Provide investment recommendations
                String investmentRecommendations = oscashController.getInvestmentRecommendations(loggedUser.getId());
                txtaAnswer.setText(investmentRecommendations);
                txtaAnswer.appendText("\nIngresa 0 para volver al menú principal.");
                break;
            case 3:
                // Show most common expenses
                String commonExpenses = oscashController.getCommonExpenses(loggedUser.getId());
                txtaAnswer.setText(commonExpenses);
                txtaAnswer.appendText("\nIngresa 0 para volver al menú principal.");
                break;
            default:
                txtaAnswer.setText("Opción no válida. Por favor, elige una opción del menú.");
                txtaAnswer.appendText("\nIngresa 0 para volver al menú principal.");
                break;
        }
        txtQuestion.clear();
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
