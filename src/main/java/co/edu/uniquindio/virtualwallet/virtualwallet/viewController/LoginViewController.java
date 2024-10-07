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
    private void onStart(ActionEvent actionEvent) {
        start(actionEvent);
    }

    private void start(ActionEvent actionEvent) {
        try {
            String email = txtEmail.getText();
            String password = txtPassword.getText();

            Person validatedUser = loginController.validateLogin(email, password);
            loginController.saveSession(validatedUser);

            if (loginController.isFirstLogin(validatedUser)) {
                browseWindow("/validation-view.fxml", "Validation", actionEvent);
            } else {
                if (validatedUser instanceof User) {
                    browseWindow("/user-data-view.fxml", "User Panel", actionEvent);
                } else {
                    browseWindow("/adminPanel.fxml", "Admin Panel", actionEvent);
                }
            }
        } catch (Exception e) {
            showMessage(e.getMessage(), "Error de inicio de sesión", "La sesión no pudo ser iniciada" , Alert.AlertType.ERROR);
        }
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