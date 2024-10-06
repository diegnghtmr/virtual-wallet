package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.event.ActionEvent;

public class StartupViewController extends CoreViewController{

    public void onLogin(ActionEvent actionEvent) {
        browseWindow("/login-view.fxml", "Banco - Iniciar Sesión", actionEvent);
    }

    public void onRegistration(ActionEvent actionEvent) {
        browseWindow("/register-view.fxml", "Banco - Registro", actionEvent);
    }
}
