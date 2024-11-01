package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.UserDataController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IUserManagementViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserDataViewController extends CoreViewController implements IUserManagementViewController<UserDto> {
    User person;
    UserDataController userDataController;

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
        browseWindow("/view/user-container-view.fxml", "Dashboard", event);
    }

    @FXML
    public void onLogout(ActionEvent event) {
        Session.getInstance().closeSession();
        browseWindow("/view/startup-view.fxml", "BuckTrack", event);
    }

    @FXML
    public void onUpdate(ActionEvent event) {
        updateUser();
    }

    @FXML
    public void initialize() {
        person = (User) Session.getInstance().getPerson();
        userDataController = new UserDataController();
        initView();
    }

    @Override
    public void initView() {
        showInformation();
    }

    private void updateUser() {

    }

    private void showInformation() {
        txtId.setText(person.getId());
        txtUserName.setText(person.getFullName());
        txtEmail.setText(person.getEmail());
        txtPassword.setText(person.getPassword());
        txtPhone.setText(person.getPhoneNumber());
        txtAddress.setText(person.getAddress());
        dpDateBirth.setText(String.valueOf(person.getBirthDate()));
        txtBalance.setText(String.valueOf(person.getTotalBalance()));
        txtRegistrationDate.setText(String.valueOf(person.getRegistrationDate()));
    }


    @Override
    public boolean validateData(UserDto userDto) {
        return false;
    }

}
