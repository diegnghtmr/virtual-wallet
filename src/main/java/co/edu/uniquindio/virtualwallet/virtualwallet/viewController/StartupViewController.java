package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.event.ActionEvent;

public class StartupViewController extends CoreViewController{

    public void onLogin(ActionEvent actionEvent) {
        browseWindow("/login-view.fxml", "Inicio de Sesión", actionEvent);
    }

    public void onRegistration(ActionEvent actionEvent) {
        browseWindow("/register-view.fxml", "Registro de Usuario", actionEvent);
    }
}
