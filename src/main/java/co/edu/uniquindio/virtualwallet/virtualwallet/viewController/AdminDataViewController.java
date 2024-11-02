package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AdministratorDataController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.AdministratorDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IUserManagementViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminDataViewController extends CoreViewController implements IUserManagementViewController<AdministratorDto> {
    Administrator person;
    AdministratorDataController administratorDataController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnGoDashboard;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btncloseSession;

    @FXML
    private TextField txtAdminName;

    @FXML
    private TextField txtDateBirth;

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
    public void onLogout(ActionEvent event) {
        Session.getInstance().closeSession();
        browseWindow("/view/startup-view.fxml", "BuckTrack", event);

    }

    @FXML
    public void onGoDashboard(ActionEvent event) {
        browseWindow("/view/administrator-container-view.fxml", "Dashboard", event);

    }

    @FXML
    public void onUpdate(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        person = (Administrator) Session.getInstance().getPerson();
        administratorDataController = new AdministratorDataController();
        initView();

    }

    @Override
    public void initView() {
        showInformation();
    }

    private void showInformation() {
        txtId.setText(person.getId());
        txtAdminName.setText(person.getFullName());
        txtEmail.setText(person.getEmail());
        txtPassword.setText(person.getEmail());
        txtPhone.setText(person.getPhoneNumber());
        txtDateBirth.setText(String.valueOf(person.getBirthDate()));
        txtRegistrationDate.setText(String.valueOf(person.getRegistrationDate()));
    }

    @Override
    public boolean validateData(AdministratorDto object) {
        return false;
    }

}
