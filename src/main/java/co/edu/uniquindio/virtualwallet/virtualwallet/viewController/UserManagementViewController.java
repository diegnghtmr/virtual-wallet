package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.UserManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UserManagementViewController extends CoreViewController {

    Administrator loggedAdmin;
    ObservableList<UserDto> userDtoList = FXCollections.observableArrayList();
    UserDto userSelected;

    UserManagementController userManagementController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private DatePicker dpRegistrationDate;

    @FXML
    private TableView<UserDto> tblUser;

    @FXML
    private TableColumn<UserDto, String> tcAddress;

    @FXML
    private TableColumn<UserDto, String> tcBirthdate;

    @FXML
    private TableColumn<UserDto, String> tcEmail;

    @FXML
    private TableColumn<UserDto, String> tcId;

    @FXML
    private TableColumn<UserDto, String> tcNameUser;

    @FXML
    private TableColumn<UserDto, String> tcPassword;

    @FXML
    private TableColumn<UserDto, String> tcPhoneNumber;

    @FXML
    private TableColumn<UserDto, String> tcRegistrationDate;

    @FXML
    private TableColumn<UserDto, String> tcTotalBalance;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNameUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    void onAdd(ActionEvent event) {
        addUser();

    }

    @FXML
    public void onHome(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (currentStage != null) {
            currentStage.close();
        }

        openWindow("/view/admin-data-view.fxml", "Datos del Administrador", null);
    }

    @FXML
    void onNew(ActionEvent event) {
        clearFields();
        deselectTable();

    }

    @FXML
    void onRemove(ActionEvent event) {
        removeUser();

    }


    @FXML
    void onUpdate(ActionEvent event) {
        updateUser();

    }


    @FXML
    void initialize() {
        userManagementController = new UserManagementController();
        loggedAdmin = (Administrator) Session.getInstance().getPerson();
        initView();

    }

    private void initView() {
        initDataBinding();
        getUsers();
        tblUser.getItems().clear();
        tblUser.setItems(userDtoList);
        listenerSelection();

    }

    private void initDataBinding() {
        tcNameUser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fullName()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcBirthdate.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().birthDate())));
        tcPhoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().phoneNumber()));
        tcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));
        tcAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().address()));
        tcRegistrationDate.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().registrationDate())));
        tcPassword.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().password()));
        tcTotalBalance.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().totalBalance())));

    }

    private void getUsers() {
        userDtoList.clear();
        userDtoList.addAll(userManagementController.getUsers());
    }

    private void listenerSelection() {
        tblUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            userSelected = newSelection;
            showInformation(userSelected);
        });

    }

    private void showInformation(UserDto userSelected) {
        if (userSelected != null) {
            txtNameUser.setText(userSelected.fullName());
            txtId.setText(userSelected.id());
            txtPhoneNumber.setText(userSelected.phoneNumber());
            txtEmail.setText(userSelected.email());
            txtAddress.setText(userSelected.address());
            txtPassword.setText(userSelected.password());
            dpBirthdate.setValue(userSelected.birthDate());
            dpRegistrationDate.setValue(userSelected.registrationDate());
        }
    }

    private void addUser() {
        UserDto userDto = buildUserDto();
        if (userDto == null) {
            showMessage("Error", "Datos no validos", "El tipo de usuario seleccionado no es valido", Alert.AlertType.ERROR);
            return;
        }
        if (validateData(userDto)) {
            if (userManagementController.addUser(userDto)) {
                userDtoList.add(userDto);
                showMessage("Notificación", "Usuario agregado", "El usuario ha sido aagregado con éxito", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showMessage("Error", "Usuario no agregado", "El usuario no pudo ser agregado", Alert.AlertType.ERROR);
            }
        }

    }

    private void removeUser() {
        boolean userRemoved = false;
        if (userSelected != null) {
            if (showConfirmationMessage("¿Está seguro de eliminar el cliente seleccionado?")) {
                userRemoved = userManagementController.removeUser(userSelected);
                if (userRemoved) {
                    userDtoList.remove(userSelected);
                    showMessage("Notificación", "Usuario eliminado", "El usuario ha sido eliminado con éxito", Alert.AlertType.INFORMATION);
                    clearFields();
                } else {
                    showMessage("Error", "Usuario no eliminado",
                            "El usuario no pudo ser eliminado", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Error", "Usuario no seleccionado",
                    "Por favor, seleccione un usuario para eliminar", Alert.AlertType.ERROR);
        }
    }

    private void updateUser() {
        boolean userUpdated = false;
        UserDto userDto = buildUserDto();
        if (userSelected != null) {
            if (validateData(userDto)){
                if(!hasChanges(userSelected, userDto)) {
                    showMessage("Error", "Sin cambios", "No se puede actualizar el usuario sin cambios", Alert.AlertType.INFORMATION);
                    return;
                }
                int selectedIndex = tblUser.getSelectionModel().getSelectedIndex();
                userUpdated = userManagementController.updateUser(userSelected, userDto);
                if(userUpdated){
                    userDtoList.set(selectedIndex, userDto);
                    tblUser.refresh();
                    tblUser.getSelectionModel().select(selectedIndex);
                    showMessage("Notificación", "Usuario actualizado",
                            "El Usuario ha sido actualizado con éxito", Alert.AlertType.INFORMATION);
                    deselectTable();
                    clearFields();
                } else {
                    showMessage("Error", "Usuario no actualizado",
                            "El usuario no pudo ser actualizado", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Error", "Usuario no seleccionado",
                    "Por favor, seleccione un usuario para actualizar", Alert.AlertType.ERROR);
        }
    }

    private boolean hasChanges(UserDto userSelected, UserDto userDto) {
        boolean hasChanges = false;

        hasChanges = !userSelected.id().equals(userDto.id()) ||
                !userSelected.address().equals(userDto.address()) ||
                !userSelected.email().equals(userDto.email()) ||
                !userSelected.password().equals(userDto.password()) ||
                !userSelected.phoneNumber().equals(userDto.phoneNumber()) ||
                !userSelected.fullName().equals(userDto.fullName()) ||
                !userSelected.birthDate().equals(userDto.birthDate()) ||
                !userSelected.registrationDate().equals(userDto.registrationDate());

        return hasChanges;

    }

    private boolean validateData(UserDto userDto) {
        String message = "";
        LocalDate today = LocalDate.now();
        if (userDto.fullName().isEmpty()) {
            message += "El nombre del usuario es requerido.\n";
        }
        if (userDto.id().isEmpty()) {
            message += "El id del usuario es requerido.\n";
        }
        if (userDto.birthDate() == null) {
            message += "La fecha de nacimiento es requerida.\n";
        } else {
            LocalDate dateNeeded = today.minusYears(18);
            if (userDto.birthDate().isAfter(dateNeeded)) {
                message += "El usuario debe ser mayor de 18.\n";
            }
        }
        if (userDto.registrationDate() == null) {
            message += "La fecha del registro es requerida.\n";
        } else if (userDto.registrationDate().isAfter(today)) {
            message += "La fecha del registro no puede ser futura.\n";
        }
        if (userDto.password().isEmpty()) {
            message += "La contraseña es requerida.\n";
        }
        if (userDto.phoneNumber().isEmpty()) {
            message += "El número de teléfono es requerido.\n";
        }
        if (userDto.email().isEmpty()) {
            message += "El email es requerido.\n";
        }
        if (userDto.address().isEmpty()) {
            message += "La dirección es requerida.\n";
        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no validos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private UserDto buildUserDto() {
        String name = txtNameUser.getText().trim();
        String id = txtId.getText().trim();
        LocalDate birthDate = dpBirthdate.getValue();
        LocalDate registrationDate = dpRegistrationDate.getValue();
        String phoneNumber = txtPhoneNumber.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String password = txtPassword.getText().trim();

        return new UserDto(
                id, name, phoneNumber, email, password, birthDate, registrationDate, address,
                0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private void clearFields() {
        txtPassword.clear();
        txtAddress.clear();
        txtId.clear();
        txtNameUser.clear();
        txtEmail.clear();
        txtPhoneNumber.clear();
        dpBirthdate.setValue(null);
        dpRegistrationDate.setValue(null);
    }

    private void deselectTable() {
        tblUser.getSelectionModel().clearSelection();
        userSelected = null;
    }


}
