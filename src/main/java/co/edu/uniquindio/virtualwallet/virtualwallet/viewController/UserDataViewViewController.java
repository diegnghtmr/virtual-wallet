package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IUserManagementViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserDataViewViewController extends CoreViewController implements IUserManagementViewController<UserDto> {
    @FXML
    private Button btnGoDashboard;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField dpDateBirth;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtRegistrationDate;

    @FXML
    private TextField txtUserName;

    @FXML
    public void onGoDashboard(ActionEvent event) {

    }

    @FXML
    public void onLogout(ActionEvent event) {

    }

    @FXML
    public void onUpdate(ActionEvent event) {

    }

    @FXML
    public void initialize() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initDataBinding() {

    }

    @Override
    public void listenerSelection() {

    }

    @Override
    public boolean validateData(UserDto userDto) {
        return false;
    }

}
