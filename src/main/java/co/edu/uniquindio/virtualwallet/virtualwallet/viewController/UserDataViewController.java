package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.UserDataController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.BudgetDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IUserManagementViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;

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
        boolean userUpdated = false;
        UserDto userDto = buildUserDto();
        if (validateData(userDto)) {
            if (!hasChanges(person, userDto)) {
                showMessage("Información", "No se han realizado cambios",
                        "Por favor, realice cambios para actualizar", Alert.AlertType.INFORMATION);
                return;
            }
            userUpdated = userDataController.updateUser(person, userDto);
            if (userUpdated) {
                showMessage("Información", "Usuario actualizado",
                        "El usuario ha sido actualizado correctamente", Alert.AlertType.INFORMATION);
                person = userDataController.getUserById(person.getId());
                Session.getInstance().setPerson(person);
                showInformation();
            } else {
                showMessage("Error", "Usuario no actualizado",
                        "No se pudo actualizar el usuario", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean hasChanges(User person, UserDto userDto) {
        return !person.getFullName().equals(userDto.fullName()) ||
                !person.getPhoneNumber().equals(userDto.phoneNumber()) ||
                !person.getEmail().equals(userDto.email()) ||
                !person.getPassword().equals(userDto.password()) ||
                !person.getBirthDate().equals(userDto.birthDate()) ||
                !person.getAddress().equals(userDto.address()) ||
                person.getTotalBalance() != userDto.totalBalance();
    }

    private UserDto buildUserDto() {
        return new UserDto(
                txtId.getText(),
                txtUserName.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                LocalDate.parse(dpDateBirth.getText()),
                LocalDate.parse(txtRegistrationDate.getText()),
                txtAddress.getText(),
                Double.parseDouble(txtBalance.getText()),
                new ArrayList<BudgetDto>(), // Empty list for budgetList
                new ArrayList<Account>(), // Empty list for associatedAccounts
                new ArrayList<CategoryDto>(), // Empty list for categories
                new ArrayList<NotificationUtil>()  // Empty list for notificationUtils
        );
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
        String message = "";
        if (userDto.fullName().isEmpty()) {
            message += "El nombre no puede estar vacío\n";
        }
        if (userDto.phoneNumber().isEmpty()) {
            message += "El número de teléfono no puede estar vacío\n";
        }
        if (userDto.email().isEmpty()) {
            message += "El correo electrónico no puede estar vacío\n";
        }
        if (userDto.password().isEmpty()) {
            message += "La contraseña no puede estar vacía\n";
        }
        if (userDto.birthDate() == null) {
            message += "La fecha de nacimiento no puede estar vacía\n";
        }
        if (userDto.address().isEmpty()) {
            message += "La dirección no puede estar vacía\n";
        }
        if (userDto.totalBalance() < 0) {
            message += "El saldo total no puede ser negativo\n";
        }
        if (!message.isEmpty()) {
            showMessage("Error", message, "Por favor, corrija los siguientes errores", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

}
