package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AdminTransferManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.TransferDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.EventType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverManagement;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdminTransferManagementViewController extends CoreViewController implements ObserverView {
    Administrator loggedAdmin;
    ObservableList<TransferDto> transferDtoList = FXCollections.observableArrayList();
    TransferDto transferSelected;
    AdminTransferManagementController adminTransferManagementController;

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
    private ComboBox<CategoryDto> cbCategory;

    @FXML
    private ComboBox<Account> cbReceivingAccount;

    @FXML
    private ComboBox<Account> cbSourceAccount;

    @FXML
    private TableView<TransferDto> tblTransfer;

    @FXML
    private TableColumn<TransferDto, String> tcAmount;

    @FXML
    private TableColumn<TransferDto, String> tcCategory;

    @FXML
    private TableColumn<TransferDto, String> tcCommission;

    @FXML
    private TableColumn<TransferDto, String> tcDate;

    @FXML
    private TableColumn<TransferDto, String> tcDescription;

    @FXML
    private TableColumn<TransferDto, String> tcId;

    @FXML
    private TableColumn<TransferDto, String> tcReceivingAccount;

    @FXML
    private TableColumn<TransferDto, String> tcSourceAccount;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextArea txtaDescription;

    @FXML
    void onAdd(ActionEvent event) {
        addTransfer();

    }


    @FXML
    void onHome(ActionEvent event) {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (currentStage != null) {
            currentStage.close();
        }

        openWindow("/view/admin-data-view.fxml", "Datos del Administrador", null);

    }

    @FXML
    void onNew(ActionEvent event) {
        clearFields();


    }

    @FXML
    void initialize() {
        adminTransferManagementController = new AdminTransferManagementController();
        loggedAdmin = (Administrator) Session.getInstance().getPerson();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.ACCOUNT, this);

    }

    private void initView() {
        initDataBinding();
        getTransfer();
        initializeDataComboBox();
        tblTransfer.getItems().clear();
        tblTransfer.setItems(transferDtoList);
        listenerSelection();

    }

    private void initDataBinding() {
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().amount())));
        tcCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().category().name()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idTransaction()));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().description()));
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().date().toString()));
        tcCommission.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().commission())));
        tcSourceAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().account().getBankName() + " - " + cellData.getValue().account().getAccountNumber()));
        tcReceivingAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().receivingAccount().getBankName() + " - " + cellData.getValue().receivingAccount().getAccountNumber()));
    }

    private void getTransfer() {
        transferDtoList.clear();
        transferDtoList.addAll(adminTransferManagementController.getTransfers());
    }

    private void initializeDataComboBox() {
        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(adminTransferManagementController.getAccounts());
        cbSourceAccount.setItems(accountDtoList);
        cbReceivingAccount.setItems(accountDtoList);

        cbSourceAccount.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Filtrar la lista de cuentas, excluyendo la cuenta seleccionada como origen
                ObservableList<Account> filteredList = accountDtoList.filtered(account -> !account.equals(newSelection));
                cbReceivingAccount.setItems(filteredList);
            } else {
                cbReceivingAccount.setItems(accountDtoList);
            }

        });
        ObservableList<CategoryDto> categoryDtoList = FXCollections.observableArrayList(adminTransferManagementController.getCategories());
        cbCategory.setItems(categoryDtoList);

        initializeComboBox(cbReceivingAccount, accountDtoList, account -> account.getBankName() + " - " + account.getAccountNumber());
        initializeComboBox(cbSourceAccount, accountDtoList, account -> account.getBankName() + " - " + account.getAccountNumber());
        initializeComboBox(cbCategory, categoryDtoList, CategoryDto::name);
    }

    private void listenerSelection() {
        tblTransfer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            transferSelected = newSelection;
            showInformation(transferSelected);
        });
    }

    private void showInformation(TransferDto transferSelected) {
        if (transferSelected != null) {
            txtaDescription.setText(transferSelected.description());
            txtAmount.setText(String.valueOf(transferSelected.amount()));
            cbCategory.setValue(transferSelected.category());
            cbReceivingAccount.setValue(transferSelected.receivingAccount());
            cbSourceAccount.setValue(transferSelected.account());
        }
    }

    private void clearFields() {
        txtAmount.clear();
        txtaDescription.clear();
        cbCategory.getSelectionModel().clearSelection();
        cbSourceAccount.getSelectionModel().clearSelection();
        cbReceivingAccount.getSelectionModel().clearSelection();
    }

    private void addTransfer() {
        TransferDto transferDto = buildTransfer();
        if (transferDto == null) {
            showMessage("Error", "Datos no válidos", "La transferencia no es válida", Alert.AlertType.ERROR);
            return;
        }
        if (validateData(transferDto)) {
            if (showConfirmationMessage("¿Está seguro de realizar la transferencia?")) {
                if (adminTransferManagementController.performTransfer(transferDto)) {
                    transferDtoList.add(transferDto);
                    showMessage("Notificación", "Transferencia exitosa", "La transferencia se ha realizado con éxito", Alert.AlertType.INFORMATION);
                    clearFields();
                    ObserverManagement.getInstance().notifyObservers(EventType.TRANSFER);

                } else {
                    showMessage("Error", "Transferencia no realizada", "La cuenta no tiene saldo suficiente", Alert.AlertType.ERROR);

                }

            } else {
                showMessage("Operación cancelada", "Transferencia no realizada", "Ha cancelado la transferencia.", Alert.AlertType.WARNING);
            }
        }
    }


    private boolean validateData(TransferDto transferDto) {
        String message = "";
        if (transferDto.account() == null) {
            message += "La cuenta de origen es requerida.\n";
        }
        if (transferDto.receivingAccount() == null) {
            message += "La cuenta de destino es requerida.\n";
        }
        if (transferDto.amount() <= 0) {
            message += "El monto no puede ser negativo.\n";
        }
        if (transferDto.description().isEmpty()) {
            message += "La descripción es requerida.\n";
        }
        if (transferDto.category() == null) {
            message += "La categoría es requerida.\n";

        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private TransferDto buildTransfer() {
        // Generación de ID de transacción único
        SecureRandom random = new SecureRandom();
        String idNumber;
        do {
            idNumber = String.format("%09d", random.nextInt(1_000_000_000));
        } while (adminTransferManagementController.isTransactionIdExists(idNumber));

        // Validar y convertir el monto
        String amountText = txtAmount.getText();
        if (amountText.isEmpty()) {
            showMessage("Error", "El monto no puede estar vacío", "Por favor, ingrese un monto válido", Alert.AlertType.ERROR);
            return null;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showMessage("Error", "Monto inválido", "El monto debe ser un número válido.", Alert.AlertType.ERROR);
            return null;
        }

        return new TransferDto(
                idNumber,
                LocalDate.now(),
                amount,
                txtaDescription.getText(),
                cbCategory.getValue(),
                cbSourceAccount.getValue(),
                TransactionStatus.PENDING.name(),
                cbReceivingAccount.getValue(),
                300);
    }

    @Override
    public void updateView(EventType event) {
        if (event == EventType.ACCOUNT) {
            initializeDataComboBox();
        }

    }
}
