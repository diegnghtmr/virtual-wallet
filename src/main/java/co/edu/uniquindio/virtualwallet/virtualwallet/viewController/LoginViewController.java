package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.LoginController;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
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
    void onRegister(ActionEvent event) {
        browseWindow("/view/register-view.fxml", "Registro de Usuario", event);
    }

    @FXML
    private void onStart(ActionEvent actionEvent) {
        start(actionEvent);
    }

    private void start(ActionEvent actionEvent) {
        try {
            String email = txtEmail.getText();
            String password = txtPassword.getText();

            Person validatedUser = loginController.validateLogin(email, password);

            if(validatedUser == null) {
                showMessage("El usuario no existe", "Error de inicio de sesión", "El usuario no existe", Alert.AlertType.ERROR);
            }else {

                loginController.saveSession(validatedUser);

                if (validatedUser instanceof User) {

                    if (!loginController.isVerified()) {
                        browseWindow("/view/validation-view.fxml", "Validación de Usuario", actionEvent);
                    } else {
                        browseWindow("/view/user-data-view.fxml", "Datos de Usuario", actionEvent);
                    }

                } else {
                    browseWindow("/view/admin-data-view.fxml", "Dashboard", actionEvent);
                }

            }

        } catch (Exception e) {
            showMessage("Error de inicio de sesión", e.getMessage(), "La sesión no pudo ser iniciada" , Alert.AlertType.ERROR);
        }
    }


    private void returnToStartup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/startup-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("BuckTrack");
            stage.show();

            closeWindow(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}