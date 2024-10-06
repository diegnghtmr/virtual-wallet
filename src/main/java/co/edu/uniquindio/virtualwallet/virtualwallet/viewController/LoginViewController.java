package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.LoginController;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController extends CoreViewController{

    private LoginController loginController;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnStart;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void initialize() {
        loginController = new LoginController();
    }

    @FXML
    void onReturn(ActionEvent actionEvent) {
        returnToStartup(actionEvent);
    }

    @FXML
    private void onStart(ActionEvent actionEvent) {
        start(actionEvent);
    }

    private void start(ActionEvent actionEvent) {
        if (validateForm()) {
            try {
                Person person = loginController.validateLogin(txtEmail.getText(), txtPassword.getText());
                showMessage("¡Inicio de sesión exitoso!", "Bienvenido " + person.getFullName(), "Has iniciado sesión correctamente.", Alert.AlertType.INFORMATION);
                closeWindow(actionEvent);
                browseWindow("/user-data-view.fxml", "User Data View", actionEvent);
            } catch (Exception e) {
                showMessage("Error de inicio de sesión", "No se pudo iniciar sesión", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showMessage("Por favor, complete todos los campos", "Error de validación", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);        }
    }

    private boolean validateForm() {
        return txtEmail.getText() != null && !txtEmail.getText().isEmpty()
                && txtPassword.getText() != null && !txtPassword.getText().isEmpty();
    }


    private void returnToStartup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/startup-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("App Byte Bank");
            stage.show();

            closeWindow(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}