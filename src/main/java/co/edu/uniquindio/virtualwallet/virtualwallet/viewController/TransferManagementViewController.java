package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.TransferManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.controller.WithdrawalManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.TransferDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.EventType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverManagement;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverView;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ITransactionViewController;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.security.SecureRandom;
import java.time.LocalDate;

public class TransferManagementViewController extends CoreViewController implements ITransactionViewController<TransferDto>, ObserverView {
    private TransferManagementController transferManagementController;
    private User loggedUser;
    private ObservableList<TransferDto> transferListDto = FXCollections.observableArrayList();
    private TransferDto transferSelected;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnNotification;

    @FXML
    private ComboBox<CategoryDto> cbCategory;

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
    private TextField txtIdReceivingAccount;

    @FXML
    private TextArea txtaDescription;

    @FXML
    public void onAdd(ActionEvent event) {
        addTransfer();
    }

    @FXML
    public void onNew(ActionEvent event) {
        clearFields();
        deselectTable();
    }

    @FXML
    public void onNotification(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/view/notification-view.fxml", "Notificaciones", ownerStage);

    }

    @FXML
    public void initialize() {
        transferManagementController = new TransferManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.ACCOUNT, this);
    }

    @Override
    public void initView() {
        initDataBinding();
        getTransfers();
        initializeDataComboBox();
        tblTransfer.getItems().clear();
        tblTransfer.setItems(transferListDto);
        listenerSelection();
    }

    @Override
    public void initDataBinding() {
        tcReceivingAccount.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().account().getBankName() + " - "
                        + cellData.getValue().account().getAccountNumber()));
        tcSourceAccount.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().account().getBankName() + " - "
                        + cellData.getValue().account().getAccountNumber()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().amount())));
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().date())));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().description())));
        tcCategory.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().category().name())));
        tcCommission.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().commission())));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().idTransaction())));
    }

    private void getTransfers() {
        String userId = loggedUser.getId();
        transferListDto.clear();
        transferListDto.addAll(transferManagementController.getTransfersByUser(userId));
    }

    private void initializeDataComboBox() {
        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(
                transferManagementController.getAccountsByUserId(loggedUser.getId()));
        ObservableList<CategoryDto> categoryDtoList = FXCollections.observableArrayList(
                transferManagementController.getCategoriesByUserId(loggedUser.getId()));

        initializeComboBox(cbSourceAccount, accountDtoList,
                account -> account.getBankName() + " - " + account.getAccountNumber());

        initializeComboBox(cbCategory, categoryDtoList, CategoryDto::name);
    }

    @Override
    public void listenerSelection() {
        tblTransfer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            transferSelected = newSelection;
            showInformation();
        });
    }

    private void showInformation() {
        if (transferSelected != null) {
            txtAmount.setText(String.valueOf(transferSelected.amount()));
            txtIdReceivingAccount.setText(transferSelected.receivingAccount().getAccountNumber());
            cbSourceAccount.setValue(transferSelected.account());
            cbCategory.setValue(transferSelected.category());
            txtaDescription.setText(transferSelected.description());
        }
    }

    private void addTransfer() {
        TransferDto transferDto = buildTransfer();
        if (transferDto == null) {
            showMessage("Error", "Datos no válidos", "La transferencia no es válida", Alert.AlertType.ERROR);
            return;
        }
        if (validateData(transferDto)) {
            if (showConfirmationMessage("¿Está seguro de realizar la transferencia?")) {
                if (transferManagementController.addTransfer(transferDto)) {
                    transferListDto.add(transferDto);
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

    private TransferDto buildTransfer() {
        // Generación de ID de transacción único
        SecureRandom random = new SecureRandom();
        String idNumber;
        do {
            idNumber = String.format("%09d", random.nextInt(1_000_000_000));
        } while (transferManagementController.isTransactionIdExists(idNumber));

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
                transferSelected.receivingAccount(),
                6000);
    }

    @Override

    public boolean validateData(TransferDto transferDto) {
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

    @Override
    public void clearFields() {
        txtAmount.clear();
        txtIdReceivingAccount.clear();
        txtaDescription.clear();
        cbSourceAccount.getSelectionModel().clearSelection();
        cbCategory.getSelectionModel().clearSelection();
    }

    @Override
    public void deselectTable() {
        tblTransfer.getSelectionModel().clearSelection();
        transferSelected = null;
    }

    @Override
    public void updateView(EventType event) {
        if (event == EventType.ACCOUNT) {
            initializeDataComboBox();
        }

    }



}
