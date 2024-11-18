package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.WithdrawalManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.WithdrawalDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.EventType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverManagement;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverView;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ITransactionViewController;
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

public class WithdrawalManagementViewController extends CoreViewController implements ITransactionViewController<WithdrawalDto>, ObserverView {
    private WithdrawalManagementController withdrawalManagementController;
    private User loggedUser;
    private ObservableList<WithdrawalDto> withdrawalListDto = FXCollections.observableArrayList();
    private WithdrawalDto withdrawalSelected;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnNotification;

    @FXML
    private ComboBox<Account> cbAccount;

    @FXML
    private ComboBox<CategoryDto> cbCategory;

    @FXML
    private TableView<WithdrawalDto> tblWithdrawal;

    @FXML
    private TableColumn<WithdrawalDto, String> tcAccount;

    @FXML
    private TableColumn<WithdrawalDto, String> tcAmount;

    @FXML
    private TableColumn<WithdrawalDto, String> tcCategory;

    @FXML
    private TableColumn<WithdrawalDto, String> tcCommission;

    @FXML
    private TableColumn<WithdrawalDto, String> tcDate;

    @FXML
    private TableColumn<WithdrawalDto, String> tcDescription;

    @FXML
    private TableColumn<WithdrawalDto, String> tcId;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextArea txtaDescription;

    @FXML
    public void onAdd(ActionEvent event) {
        addWithdrawal();
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
        withdrawalManagementController = new WithdrawalManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.ACCOUNT, this);
    }

    @Override
    public void initView() {
        initDataBinding();
        getWithdrawals();
        initializeDataComboBox();
        tblWithdrawal.getItems().clear();
        tblWithdrawal.setItems(withdrawalListDto);
        listenerSelection();
    }

    @Override
    public void initDataBinding() {
        tcAccount.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().account().getBankName() + " - "
                        + cellData.getValue().account().getAccountNumber()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().amount())));
        tcCategory.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().category().name())));
        tcCommission.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().commission())));
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().date())));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().description())));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().idTransaction())));
    }

    private void getWithdrawals() {
        String userId = loggedUser.getId();
        withdrawalListDto.clear();
        withdrawalListDto.addAll(withdrawalManagementController.getWithdrawalsByUser(userId));
    }

    private void initializeDataComboBox() {
        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(
                withdrawalManagementController.getAccountsByUserId(loggedUser.getId()));
        ObservableList<CategoryDto> categoryDtoList = FXCollections.observableArrayList(
                withdrawalManagementController.getCategoriesByUserId(loggedUser.getId()));

        initializeComboBox(cbAccount, accountDtoList,
                account -> account.getBankName() + " - " + account.getAccountNumber());

        initializeComboBox(cbCategory, categoryDtoList, CategoryDto::name);
    }

    @Override
    public void listenerSelection() {
        tblWithdrawal.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            withdrawalSelected = newSelection;
            showInformation();
        });
    }

    private void showInformation() {
        if (withdrawalSelected != null) {
            txtAmount.setText(String.valueOf(withdrawalSelected.amount()));
            cbAccount.setValue(withdrawalSelected.account());
            cbCategory.setValue(withdrawalSelected.category());
            txtaDescription.setText(withdrawalSelected.description());
        }
    }

    private void addWithdrawal() {
        WithdrawalDto withdrawalDto = buildWithdrawal();
        if (withdrawalDto == null) {
            showMessage("Error", "Datos no validos", "El retiro no es valido", Alert.AlertType.ERROR);
            return;
        }
        if (validateData(withdrawalDto)) {
            if (withdrawalManagementController.userAddWithdrawal(withdrawalDto)) {
                withdrawalListDto.add(withdrawalDto);
                showMessage("Notificación", "Retiro exitoso", "El retiro se ha realizado con éxito", Alert.AlertType.INFORMATION);
                clearFields();
                ObserverManagement.getInstance().notifyObservers(EventType.WITHDRAWAL);

            } else {
                showMessage("Error", "Retiro no realizado", "No se pudo hacer el retiro", Alert.AlertType.ERROR);
            }
        }
    }

    private WithdrawalDto buildWithdrawal() {
        String idNumber;
        if (withdrawalSelected != null) {
            idNumber = withdrawalSelected.idTransaction();
        } else {
            SecureRandom random = new SecureRandom();
            do {
                idNumber = String.format("%09d", random.nextInt(1_000_000_000));
            } while (withdrawalManagementController.isTransactionIdExists(idNumber));
        }

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
        return new WithdrawalDto(
                idNumber,
                LocalDate.now(),
                amount,
                txtaDescription.getText(),
                cbCategory.getValue(),
                cbAccount.getValue(),
                TransactionStatus.PENDING.name(),
                4000,
                3000000
        );


    }

    @Override
    public boolean validateData(WithdrawalDto withdrawalDto) {
        String message = "";
        if (withdrawalDto.account() == null) {
            message += "La cuenta de origen es requerida.\n";
        }
        if (withdrawalDto.amount() > withdrawalDto.withdrawalLimit()) {
            message += "El monto de retiro excede el límite permitido de " + withdrawalDto.withdrawalLimit() + ".\n";
        }
        if (withdrawalDto.category() == null) {
            message += "La categoría es requerida.\n";
        }
        if (withdrawalDto.description().isEmpty()) {
            message += "La descripción es requerida.\n";
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
        txtaDescription.clear();
        cbAccount.getSelectionModel().clearSelection();
        cbCategory.getSelectionModel().clearSelection();
    }

    @Override
    public void deselectTable() {
        tblWithdrawal.getSelectionModel().clearSelection();
        withdrawalSelected = null;
    }

    @Override
    public void updateView(EventType event) {
        if (event == EventType.ACCOUNT) {
            initializeDataComboBox();
        }

    }
}
