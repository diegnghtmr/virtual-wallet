package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.event.ActionEvent;

public class StartupViewController extends CoreViewController{

    public void onLogin(ActionEvent actionEvent) {
        browseWindow("/view/login-view.fxml", "Inicio de Sesión", actionEvent);
    }

    public void onRegistration(ActionEvent actionEvent) {
        browseWindow("/view/register-view.fxml", "Registro de Usuario", actionEvent);
    }
}
