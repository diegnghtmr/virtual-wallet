package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AccountManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ICoreViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class AccountManagementViewController extends CoreViewController implements ICoreViewController<AccountDto> {
    AccountManagementController accountManagementController;
    User loggedUser;
    ObservableList<AccountDto> accountsListDto = FXCollections.observableArrayList();
    AccountDto accountSelected;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnNotification;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cbAccountType;

    @FXML
    private TableView<AccountDto> tblAccount;

    @FXML
    private TableColumn<AccountDto, String> tcAccountNumber;

    @FXML
    private TableColumn<AccountDto, String> tcAccountType;

    @FXML
    private TableColumn<AccountDto, String> tcBankName;

    @FXML
    private TableColumn<AccountDto, String> tcBalance;

    @FXML
    private TextField txtAccountNumber;

    @FXML
    private TextField txtBankName;

    // Event Handling Methods
    // ----------------------

    @FXML
    public void onAdd(ActionEvent event) {
        addAccount();
    }

    @FXML
    public void onNew(ActionEvent event) {
        clearFields();
        deselectTable();
    }

    @FXML
    public void onNotification(ActionEvent event) {

    }

    @FXML
    public void onRemove(ActionEvent event) {
        removeAccount();
    }

    @FXML
    public void onUpdate(ActionEvent event) {
        updateAccount();
    }

    // Initialization Methods
    // ----------------------

    @FXML
    public void initialize() {
        accountManagementController = new AccountManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();
    }

    @Override
    public void initView() {
        initDataBinding();
        getAccounts();
        initializeDataComboBox();
        tblAccount.getItems().clear();
        tblAccount.setItems(accountsListDto);
        listenerSelection();
    }

    @Override
    public void initDataBinding() {
        tcAccountNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().accountNumber()));
        tcAccountType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().accountType()));
        tcBankName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().bankName()));
        tcBalance.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().balance())));
    }

    @Override
    public void listenerSelection() {
        tblAccount.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            accountSelected = newSelection;
            showInformation(accountSelected);
        });
    }

    private void getAccounts() {
        String userId = loggedUser.getId();
        accountsListDto.addAll(accountManagementController.getAccountsByUserId(userId));
    }

    private void initializeDataComboBox() {
        ObservableList<String> accountTypes = FXCollections.observableArrayList(
                accountManagementController.getAccountTypes());

        initializeComboBox(cbAccountType, accountTypes, item -> item);
    }

    // Account Management Methods
    // --------------------------

    private void addAccount() {
        AccountDto accountDto = buildAccountDto();
        if (accountDto == null) {
            showMessage("Error", "Datos no válidos", "El tipo de cuenta seleccionado no es válido", Alert.AlertType.ERROR);
            return;
        }
        if (validateData(accountDto)) {
            if (accountManagementController.addAccountDto(accountDto)) {
                accountsListDto.add(accountDto);
                showMessage("Notificación", "Cuenta agregada",
                        "La cuenta ha sido agregada con éxito", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showMessage("Error", "Cuenta no agregada",
                        "La cuenta no pudo ser agregada", Alert.AlertType.ERROR);
            }
        }
    }

    private AccountDto buildAccountDto() {
        String accountType = cbAccountType.getValue();

        if ("AHORROS".equals(accountType)) {
            return new SavingsAccountDto(
                    0, // Balance inicial
                    txtBankName.getText(), // Nombre del banco
                    txtAccountNumber.getText(), // Número de cuenta
                    loggedUser, // Usuario asociado
                    new ArrayList<TransferDto>(), // Lista de transferencias asociadas
                    new ArrayList<DepositDto>(), // Lista de depósitos asociadas
                    new ArrayList<WithdrawalDto>() // Lista de retiros asociados
            );
        } else if ("CORRIENTE".equals(accountType)) {
            return new CheckingAccountDto(
                    0, // Balance inicial
                    txtBankName.getText(), // Nombre del banco
                    txtAccountNumber.getText(), // Número de cuenta
                    loggedUser, // Usuario asociado
                    new ArrayList<TransferDto>(), // Lista de transferencias asociadas
                    new ArrayList<DepositDto>(), // Lista de depósitos asociadas
                    new ArrayList<WithdrawalDto>(), // Lista de retiros asociados
                    580000 // Límite de sobregiro
            );
        } else {
            return null;
        }
    }

    private void removeAccount() {
        boolean accountRemoved = false;
        if (accountSelected != null) {
            if (showConfirmationMessage("¿Está seguro de eliminar la cuenta seleccionada?")) {
                accountRemoved = accountManagementController.removeAccount(accountSelected);
                if (accountRemoved) {
                    accountsListDto.remove(accountSelected);
                    deselectTable();
                    clearFields();
                    showMessage("Notificación", "Cuenta eliminada",
                            "La cuenta ha sido eliminada con éxito", Alert.AlertType.INFORMATION);
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
                accountUpdated = accountManagementController.updateAccount(accountSelected, accountDto);
                if (accountUpdated) {
                    accountsListDto.set(selectedIndex, accountDto);
                    tblAccount.refresh();
                    tblAccount.getSelectionModel().select(selectedIndex);
                    showMessage("Notificación", "Cuenta actualizada",
                            "La cuenta ha sido actualizada con éxito", Alert.AlertType.INFORMATION);
                    deselectTable();
                    clearFields();
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

    // Utility Methods
    // ---------------

    @Override
    public void showInformation(AccountDto accountSelected) {
        if (accountSelected != null) {
            txtAccountNumber.setText(accountSelected.accountNumber());
            txtBankName.setText(accountSelected.bankName());
            cbAccountType.setValue(accountSelected.accountType());
        }
    }

    @Override
    public boolean validateData(AccountDto accountDto) {
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
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    @Override
    public void clearFields() {
        txtAccountNumber.clear();
        txtBankName.clear();
        cbAccountType.getSelectionModel().clearSelection();
    }

    @Override
    public void deselectTable() {
        tblAccount.getSelectionModel().clearSelection();
        accountSelected = null;
    }

    private boolean hasChanges(AccountDto accountSelected, AccountDto accountDto) {
        return !accountSelected.accountNumber().equals(accountDto.accountNumber()) ||
                !accountSelected.bankName().equals(accountDto.bankName()) ||
                !accountSelected.accountType().equals(accountDto.accountType());
    }
}