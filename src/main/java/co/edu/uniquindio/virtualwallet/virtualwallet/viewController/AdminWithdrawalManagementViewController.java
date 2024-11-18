package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AdminWithdrawalManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.WithdrawalDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
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

public class AdminWithdrawalManagementViewController extends CoreViewController implements ObserverView {
    Administrator administrator;
    ObservableList<WithdrawalDto> withdrawalDtoList = FXCollections.observableArrayList();
    WithdrawalDto withdrawalSelected;
    AdminWithdrawalManagementController adminWithdrawalManagementController;

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
    void onAdd(ActionEvent event) {
        addWithdrawal();

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

    private void clearFields() {
        txtAmount.clear();
        txtaDescription.clear();
        cbCategory.getSelectionModel().clearSelection();
        cbAccount.getSelectionModel().clearSelection();
    }

    @FXML
    void initialize() {
        adminWithdrawalManagementController = new AdminWithdrawalManagementController();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.ACCOUNT, this);


    }

    private void initView() {
        initDataBinding();
        getWithdrawal();
        initializeDataComboBox();
        tblWithdrawal.getItems().clear();
        tblWithdrawal.setItems(withdrawalDtoList);
        listenerSelection();

    }


    private void initDataBinding() {
        tcAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().account().getBankName() + " - " + cellData.getValue().account().getAccountNumber()));
        tcCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().category().name()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().amount())));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().description()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idTransaction()));
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().date().toString()));
        tcCommission.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().commission())));
    }

    private void getWithdrawal() {
        withdrawalDtoList.clear();
        withdrawalDtoList.addAll(adminWithdrawalManagementController.getWithdrawals());
    }

    private void initializeDataComboBox() {
        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(adminWithdrawalManagementController.getAccounts());
        cbAccount.setItems(accountDtoList);

        ObservableList<CategoryDto> categoryDtoList = FXCollections.observableArrayList(adminWithdrawalManagementController.getCategories());
        cbCategory.setItems(categoryDtoList);

        initializeComboBox(cbAccount, accountDtoList, account -> account.getBankName() + " - " + account.getAccountNumber());
        initializeComboBox(cbCategory, categoryDtoList, CategoryDto::name);

    }

    private void listenerSelection() {
        tblWithdrawal.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            withdrawalSelected = newSelection;
            showInformation(withdrawalSelected);
        });
    }

    private void showInformation(WithdrawalDto withdrawalSelected) {
        if (withdrawalSelected != null) {
            txtaDescription.setText(withdrawalSelected.description());
            txtAmount.setText(String.valueOf(withdrawalSelected.amount()));
            cbAccount.setValue(withdrawalSelected.account());
            cbCategory.setValue(withdrawalSelected.category());

        }
    }

    private void addWithdrawal() {
        WithdrawalDto withdrawalDto = buildWithdrawal();
        if (withdrawalDto == null) {
            showMessage("Error", "Datos no validos", "El retiro no es valido", Alert.AlertType.ERROR);
            return;
        }
        if (validateData(withdrawalDto)) {
            if (adminWithdrawalManagementController.addWithdrawal(withdrawalDto)) {
                withdrawalDtoList.add(withdrawalDto);
                showMessage("Notificación", "Retiro exitoso", "El retiro se ha realizado con éxito", Alert.AlertType.INFORMATION);
                clearFields();
                ObserverManagement.getInstance().notifyObservers(EventType.WITHDRAWAL);

            } else {
                showMessage("Error", "Retiro no realizado", "No se pudo hacer el retiro", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateData(WithdrawalDto withdrawalDto) {
        String message = "";
        if (withdrawalDto.account() == null){
            message += "La cuenta de origen es requerida.\n";
        }
        if(withdrawalDto.amount() > withdrawalDto.withdrawalLimit()){
            message += "El monto de retiro excede el límite permitido de " + withdrawalDto.withdrawalLimit() + ".\n";
        }
        if(withdrawalDto.category() == null){
            message += "La categoría es requerida.\n";
        }
        if(withdrawalDto.description().isEmpty()){
            message += "La descripción es requerida.\n";
        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private WithdrawalDto buildWithdrawal() {
        SecureRandom random = new SecureRandom();
        String idNumber;
        do {
            idNumber = String.format("%09d", random.nextInt(1_000_000_000));
        } while (adminWithdrawalManagementController.isTransactionIdExists(idNumber));

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
                200,
                3000000
        );


    }

    @Override
    public void updateView(EventType event) {
        if(event == EventType.ACCOUNT) {
            initializeDataComboBox();
        }

    }
}
