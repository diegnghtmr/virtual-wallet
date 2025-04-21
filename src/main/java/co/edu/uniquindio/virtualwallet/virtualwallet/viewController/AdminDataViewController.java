package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AdministratorDataController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.AdministratorDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IUserManagementViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        updateAdmin();

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
        txtPassword.setText(person.getPassword());
        txtPhone.setText(person.getPhoneNumber());
        txtDateBirth.setText(String.valueOf(person.getBirthDate()));
        txtRegistrationDate.setText(String.valueOf(person.getRegistrationDate()));
    }

    private void updateAdmin() {
        boolean adminUpdate = false;
        AdministratorDto administratorDto = buildAdminDto();
        if (validateData(administratorDto)) {
            if (!hasChanges(person, administratorDto)) {
                showMessage("Información", "No se han realizado cambios", "Por favor, realice cambios para actualizar", Alert.AlertType.INFORMATION);
                return;
            }
            adminUpdate = administratorDataController.updateAdmin(person, administratorDto);
            if (adminUpdate) {
                showMessage("Información", "Usuario actualizado", "El usuario ha sido actualizado correctamente", Alert.AlertType.INFORMATION);
                person = administratorDataController.getAdminById(person.getId());
                Session.getInstance().setPerson(person);
                showInformation();
            } else {
                showMessage("Error", "Usuario no actualizado",
                        "No se pudo actualizar el usuario", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean hasChanges(Administrator person, AdministratorDto administratorDto) {
        return !person.getFullName().equals(administratorDto.fullName()) ||
                !person.getPhoneNumber().equals(administratorDto.phoneNumber()) ||
                !person.getBirthDate().equals(administratorDto.birthDate());
    }

    private AdministratorDto buildAdminDto() {
        return new AdministratorDto(
                txtId.getText(),
                txtAdminName.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                LocalDate.parse(txtDateBirth.getText()),
                LocalDate.parse(txtRegistrationDate.getText()));
    }

    @Override
    public boolean validateData(AdministratorDto administratorDto) {
        String message = "";
        if (administratorDto.id().isEmpty()) {
            message += "El nombre no puede estar vacío\n";
        }
        if (administratorDto.phoneNumber().isEmpty()) {
            message += "El número de teléfono no puede estar vacío\n";
        }
        if (administratorDto.birthDate() == null) {
            message += "La fecha de nacimiento no puede estar vacía\n";
        }
        if (!message.isEmpty()) {
            showMessage("Error", message, "Por favor, corrija los siguientes errores", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }


}
