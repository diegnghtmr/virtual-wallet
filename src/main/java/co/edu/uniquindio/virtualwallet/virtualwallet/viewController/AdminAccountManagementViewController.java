package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AdminAccountManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.controller.CoreController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CheckingAccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.SavingsAccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.EventType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverManagement;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverView;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ICoreViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AdminAccountManagementViewController extends CoreViewController implements ObserverView {
    Administrator loggedAdmin;

    ObservableList<AccountDto> accountsListDto = FXCollections.observableArrayList();
    AccountDto accountSelected;

    AdminAccountManagementController adminAccountManagementController;

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
    private ComboBox<String> cbAccountType;

    @FXML
    private ComboBox<User> cbUser;

    @FXML
    private TableView<AccountDto> tblAccount;

    @FXML
    private TableColumn<AccountDto, String> tcAccountNumber;

    @FXML
    private TableColumn<AccountDto, String> tcAccountType;

    @FXML
    private TableColumn<AccountDto, String> tcBalance;

    @FXML
    private TableColumn<AccountDto, String> tcBankName;

    @FXML
    private TableColumn<AccountDto, String> tcIdUser;

    @FXML
    private TableColumn<AccountDto, String> tcNameUser;

    @FXML
    private TextField txtAccountNumber;

    @FXML
    private TextField txtBankName;

    @FXML
    public void onAdd(ActionEvent event) {
        addAccount();


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
    public void onNew(ActionEvent event) {
        clearFields();
        deselectTable();

    }

    @FXML
    public void onRemove(ActionEvent event) {
        removeAccount();

    }



    @FXML
    public void onUpdate(ActionEvent event) {
        updateAccount();

    }

    @FXML
    public void initialize() {
        adminAccountManagementController = new AdminAccountManagementController();
        loggedAdmin = (Administrator) Session.getInstance().getPerson();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.USER, this);
        ObserverManagement.getInstance().addObserver(EventType.TRANSFER,this);
        ObserverManagement.getInstance().addObserver(EventType.WITHDRAWAL, this);
        ObserverManagement.getInstance().addObserver(EventType.DEPOSIT, this);


    }

    private void initView() {
        initDataBinding();
        getAccounts();
        initializeDataComboBox();
        tblAccount.getItems().clear();
        tblAccount.setItems(accountsListDto);
        listenerSelection();

    }

    private void initDataBinding() {
        tcAccountNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().accountNumber()));
        tcAccountType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().accountType()));
        tcBankName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().bankName()));
        tcAccountNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().accountNumber()));
        tcBalance.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().balance())));
        tcIdUser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().user().getId()));
        tcNameUser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().user().getFullName()));
    }

    private void listenerSelection() {
        tblAccount.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            accountSelected = newSelection;
            showInformation(accountSelected);
        });
    }

    private void showInformation(AccountDto accountSelected) {
        if (accountSelected != null) {
            txtAccountNumber.setText(accountSelected.accountNumber());
            txtBankName.setText(accountSelected.bankName());
            cbAccountType.setValue(accountSelected.accountType());

            cbUser.getSelectionModel().select(
                    cbUser.getItems().stream()
                            .filter(user -> user.getFullName().equals(accountSelected.user().getFullName()))
                            .findFirst()
                            .orElse(null)
            );
        }
    }


    private void getAccounts() {
        accountsListDto.clear();
        accountsListDto.addAll(adminAccountManagementController.getAccounts());

    }




    private void initializeDataComboBox() {
        // combobox de cuentas
        ObservableList<String> accountTypes = FXCollections.observableArrayList(adminAccountManagementController.getAccountsTypes());
        initializeComboBox(cbAccountType, accountTypes, item -> item);

        // combobox de usuarios
        ObservableList<User> userList = FXCollections.observableArrayList(adminAccountManagementController.getUserList());
        cbUser.setItems(userList);

        // Usar un StringConverter para mostrar solo el ID del usuario
        cbUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return (user != null) ? user.getId() : ""; // Muestra el ID
            }

            @Override
            public User fromString(String s) {
                return null;
            }


        });
    }


    private void addAccount() {
        AccountDto accountDto =  buildAccountDto();
        if(accountDto == null) {
            showMessage("Error", "Datos no válidos", "El tipo de cuenta seleccionado no es válido", Alert.AlertType.ERROR);
            return;
        }
        if(validateData(accountDto)){
            if(adminAccountManagementController.adminAddAccount(accountDto)) {
                accountsListDto.add(accountDto);
                showMessage("Notificación", "Cuenta agregada",
                        "La cuenta ha sido agregada con éxito", Alert.AlertType.INFORMATION);
                clearFields();
                ObserverManagement.getInstance().notifyObservers(EventType.ACCOUNT);
            }else {
                showMessage("Error", "Cuenta no agregada",
                        "La cuenta no pudo ser agregada", Alert.AlertType.ERROR);
            }
        }
    }

    private void removeAccount() {
        boolean accountRemoved = false;
        if (accountSelected != null) {
            if (showConfirmationMessage("¿Está seguro de eliminar la cuenta seleccionada?")) {
                accountRemoved = adminAccountManagementController.adminRemoveAccount(accountSelected);
                if (accountRemoved) {
                    accountsListDto.remove(accountSelected); // Asegúrate de que `accountSelected` sea la instancia correcta
                    showMessage("Notificación", "Cuenta eliminada",
                            "La cuenta ha sido eliminada con éxito", Alert.AlertType.INFORMATION);
                    clearFields();
                    ObserverManagement.getInstance().notifyObservers(EventType.ACCOUNT);
                } else {
                    showMessage("Error", "Cuenta no eliminada",
                            "La cuenta no pudo ser eliminada", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Error", "Cuenta no seleccionada",
                    "Por favor, seleccione una cuenta para eliminar", Alert.AlertType.ERROR);
        }
    }

    private void updateAccount() {
        boolean accountUpdated = false;
        AccountDto accountDto = buildAccountDto();
        if (accountSelected != null) {
            if (validateData(accountDto)) {
                if (!hasChanges(accountSelected, accountDto)) {
                    showMessage("Error", "Sin cambios",
                            "No se puede actualizar la cuenta sin cambios", Alert.AlertType.INFORMATION);
                    return;
                }
                int selectedIndex = tblAccount.getSelectionModel().getSelectedIndex();
                accountUpdated = adminAccountManagementController.adminUpdateAccount(accountSelected, accountDto);
                if (accountUpdated) {
                    accountsListDto.set(selectedIndex, accountDto);

                    tblAccount.refresh();
                    tblAccount.getSelectionModel().select(selectedIndex);
                    showMessage("Notificación", "Cuenta actualizada",
                            "La cuenta ha sido actualizada con éxito", Alert.AlertType.INFORMATION);
                    deselectTable();
                    clearFields();
                    ObserverManagement.getInstance().notifyObservers(EventType.ACCOUNT);
                } else {
                    showMessage("Error", "Cuenta no actualizada",
                            "La cuenta no pudo ser actualizada", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Error", "Cuenta no seleccionada",
                    "Por favor, seleccione una cuenta para actualizar", Alert.AlertType.ERROR);
        }
    }

    private void deselectTable() {
        tblAccount.getSelectionModel().clearSelection();
        accountSelected = null;
    }

    private boolean hasChanges(AccountDto accountSelected, AccountDto accountDto) {
        boolean hasChanges = false;

        // Compara los atributos de AccountDto
        hasChanges = !accountSelected.accountNumber().equals(accountDto.accountNumber()) ||
                !accountSelected.bankName().equals(accountDto.bankName()) ||
                !accountSelected.accountType().equals(accountDto.accountType());

        // Compara las propiedades del usuario
        User selectedUser = accountSelected.user();
        User newUser = accountDto.user();
        hasChanges = hasChanges || !selectedUser.getId().equals(newUser.getId()) ||
                !selectedUser.getFullName().equals(newUser.getFullName());

        return hasChanges;
    }



    private void clearFields() {
        txtAccountNumber.clear();
        txtBankName.clear();
        cbAccountType.getSelectionModel().clearSelection();
        cbUser.getSelectionModel().clearSelection();
    }

    private boolean validateData(AccountDto accountDto) {
        String message = "";
        if (accountDto.accountNumber().isEmpty()) {
            message += "El número de cuenta es requerido.\n";
        }
        if (accountDto.bankName().isEmpty()) {
            message += "El nombre del banco es requerido.\n";
        }
        if (accountDto.accountType().isEmpty()) {
            message += "El tipo de cuenta es requerido.\n";
        }

        if(accountDto.user() == null){
            message += "El usuario asociado es requerido.\n";

        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }


        return true;
    }

    private AccountDto buildAccountDto() {
        // Obtener el tipo de cuenta seleccionado
        String accountType = cbAccountType.getValue();

        // Obtener los valores de los campos necesarios para la cuenta
        String bankName = txtBankName.getText().trim();
        String accountNumber = txtAccountNumber.getText().trim();
        User user = cbUser.getValue();

        // Crear el objeto adecuado según el tipo de cuenta
        if (I18n.get("account.type.savings").equals(accountType)) {
            return new SavingsAccountDto(
                    0.0, // Balance inicial
                    bankName, // Nombre del banco
                    accountNumber, // Número de cuenta
                    user, // Usuario asociado
                    new ArrayList<>(), // Lista de transferencias asociadas
                    new ArrayList<>(), // Lista de depósitos asociadas
                    new ArrayList<>()  // Lista de retiros asociados
            );
        } else if (I18n.get("account.type.checking").equals(accountType)) {
            return new CheckingAccountDto(
                    0.0, // Balance inicial
                    bankName, // Nombre del banco
                    accountNumber, // Número de cuenta
                    user, // Usuario asociado
                    new ArrayList<>(), // Lista de transferencias asociadas
                    new ArrayList<>(), // Lista de depósitos asociadas
                    new ArrayList<>(), // Lista de retiros asociados
                    580000.0 // Límite de sobregiro
            );
        } else {
            return null; // Retorna null si el tipo de cuenta no es válido
        }
    }


    @Override
    public void updateView(EventType event) {
        switch (event) {
            case USER:
                initializeDataComboBox();
                break;
            case TRANSFER:
            case WITHDRAWAL:
            case DEPOSIT:
                getAccounts();
                tblAccount.refresh();
                break;
            default:
                break;
        }
    }

}
