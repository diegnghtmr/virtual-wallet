package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.event.ActionEvent;

public class StartupViewController extends CoreViewController{

    public void onLogin(ActionEvent actionEvent) {
        browseWindow("/login.fxml", "Banco - Iniciar Sesión", actionEvent);
    }

    public void onRegistration(ActionEvent actionEvent) {
        browseWindow("/register.fxml", "Banco - Registro", actionEvent);
    }
}
