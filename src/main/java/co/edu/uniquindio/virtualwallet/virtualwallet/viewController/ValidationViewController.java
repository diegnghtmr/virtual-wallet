package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.ValidationController;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ValidationViewController extends CoreViewController {
    private ValidationController validationController;

    @FXML
    private Button btnVerifyCode;

    @FXML
    private TextField txtVerificationCode;

    @FXML
    void onVerifyCode(ActionEvent event) {
        verifyCode(event);
    }

    @FXML
    void onResendCode(ActionEvent event) {
        resendCode(event);
    }

    @FXML
    void initialize() {
        validationController = new ValidationController();
    }

    private void verifyCode(ActionEvent event) {
        try {
            String verificationCode = txtVerificationCode.getText();
            if (verificationCode.isEmpty()) {
                showMessage("Código de verificación vacío", "Ingrese un código de verificación", "Por favor ingrese el código de verificación que ha recibido en su correo electrónico.", Alert.AlertType.WARNING);
                return;
            }
            if (validationController.verifyCode(verificationCode)) {
                showMessage("Verificación exitosa", "Código verificado correctamente", "Su cuenta ha sido verificada exitosamente.", Alert.AlertType.INFORMATION);
                closeWindow(event);
                browseWindow("/view/user-data-view.fxml", "User Panel", event);
            }else{
                showMessage("Código incorrecto", "Código de verificación incorrecto", "El código de verificación ingresado no es correcto.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage(), "Error de verificación", "La verificación no pudo ser completada", Alert.AlertType.ERROR);        }
    }

    private void resendCode(ActionEvent event) {
        try {
            if (showConfirmationMessage("¿Está seguro de que desea reenviar el código de verificación?")) {
                String id = Session.getInstance().getPerson().getId();
                String email = Session.getInstance().getPerson().getEmail();
                validationController.resendVerificationCode(id, email);
                showMessage("Código reenviado", "Código de verificación reenviado", "Se ha reenviado el código de verificación a su correo electrónico.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showMessage(e.getMessage(), "Error de reenvío", "No se pudo reenviar el código de verificación", Alert.AlertType.ERROR);
        }
    }



}
