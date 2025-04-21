package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AdminDepositController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.DepositDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
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

public class AdminDepositManagementViewController extends CoreViewController implements ObserverView {
    Administrator loggedAdmin;

    ObservableList<DepositDto> depositsListDto = FXCollections.observableArrayList();
    DepositDto depositSelected;
    AdminDepositController adminDepositController;

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
    private TableView<DepositDto> tblDeposit;

    @FXML
    private TableColumn<DepositDto, String> tcAccount;

    @FXML
    private TableColumn<DepositDto, String> tcAmount;

    @FXML
    private TableColumn<DepositDto, String> tcCategory;

    @FXML
    private TableColumn<DepositDto, String> tcDate;

    @FXML
    private TableColumn<DepositDto, String> tcDescription;

    @FXML
    private TableColumn<DepositDto, String> tcId;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextArea txtaDescription;

    @FXML
    void onAdd(ActionEvent event) {
        addDeposit();

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
        adminDepositController = new AdminDepositController();
        loggedAdmin = (Administrator) Session.getInstance().getPerson();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.ACCOUNT, this);
    }

    private void initView() {
        initDataBinding();
        getDeposits();
        initializeDataComboBox();
        tblDeposit.getItems().clear();
        tblDeposit.setItems(depositsListDto);
        listenerSelection();
    }


    private void initDataBinding() {
        tcAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().account().getBankName() + "-" + cellData.getValue().account().getAccountNumber()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idTransaction()));
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().date().toString()));
        tcCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().category().name()));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().description()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().amount())));

    }

    private void getDeposits() {
        depositsListDto.clear();
        depositsListDto.addAll(adminDepositController.getDeposits());
    }

    private void initializeDataComboBox() {
        ObservableList<CategoryDto> categoryDtoList = FXCollections.observableArrayList(adminDepositController.getCategories());
        cbCategory.setItems(categoryDtoList);

        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(adminDepositController.getAccounts());
        cbAccount.setItems(accountDtoList);

        initializeComboBox(cbAccount, accountDtoList, account -> account.getBankName() + " - " + account.getAccountNumber());

        initializeComboBox(cbCategory, categoryDtoList, CategoryDto::name);
    }

    private void listenerSelection() {
        tblDeposit.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            depositSelected = newSelection;
            showInformation(depositSelected);
        });
    }

    private void showInformation(DepositDto depositSelected) {
        if (depositSelected != null) {
            txtaDescription.setText(depositSelected.description());
            txtAmount.setText(String.valueOf(depositSelected.amount()));
            cbAccount.setValue(depositSelected.account());
            cbCategory.setValue(depositSelected.category());
        }


    }

    private void addDeposit() {
        DepositDto depositDto = buildDeposit();
        if (depositDto == null) {
            showMessage("Error", "Datos no validos", "El tipo de depósito no es valido", Alert.AlertType.ERROR);
            return;
        }
        if (validateData(depositDto)) {
            if (adminDepositController.adminAddDeposit(depositDto)) {
                depositDto = depositDto.withStatus(TransactionStatus.ACCEPTED.name());
                depositsListDto.add(depositDto);
                showMessage("Notificación", "depósito agregado", "el deposito ha sido agregado con éxito", Alert.AlertType.INFORMATION);
                clearFields();
                ObserverManagement.getInstance().notifyObservers(EventType.DEPOSIT);

                String depositMessage = I18n.getFormatted(
                        "notification.message.DEPOSIT",
                        depositDto.statusType(),
                        depositDto.idTransaction()
                );
                NotificationUtil depositNotification = new NotificationUtil(
                        depositMessage,
                        LocalDate.now(),
                        NotificationType.TRANSACTION
                );
                User user = adminDepositController.searchUserDeposit(depositDto);
                user.update(depositNotification);
            } else {
                showMessage("Error", "Depósito no agregado", "El depósito no ha sido agregado con éxito", Alert.AlertType.ERROR);
                depositDto = depositDto.withStatus(TransactionStatus.REJECTED.name());
                depositsListDto.add(depositDto);
            }
        }
    }

    private void clearFields() {
        txtAmount.clear();
        txtaDescription.clear();
        cbCategory.getSelectionModel().clearSelection();
        cbAccount.getSelectionModel().clearSelection();

    }

    private DepositDto buildDeposit() {
        SecureRandom random = new SecureRandom();
        String idNumber;
        do {
            idNumber = String.format("%09d", random.nextInt(1_000_000_000));
        } while (adminDepositController.isTransactionIdExists(idNumber));

        String amountText = txtAmount.getText();
        if (amountText.isEmpty()) {
            showMessage("Error", "El monto no puede estar vacío",
                    "Por favor, ingrese un monto válido", Alert.AlertType.ERROR);
            return null;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        }catch (NumberFormatException e){
            showMessage("Error", "Monto inválido", "El monto debe ser un número válido.", Alert.AlertType.ERROR);
            return null;
        }
        return new DepositDto(
                idNumber, // idTransaction, generated randomly and checked for uniqueness
                LocalDate.now(), // date, the current date
                amount, // amount, the value from txtAmount field
                txtaDescription.getText(), // description, the value from txtaDescription field
                cbCategory.getValue(), // category, the selected value from cbCategory
                cbAccount.getValue(), // account, the selected value from cbAccount
                TransactionStatus.PENDING.name()
        );
    }

    private boolean validateData(DepositDto depositDto) {
        String message = "";
        if (depositDto.account() == null) {
            message += "La cuenta es requerida.\n";
        }
        if (depositDto.category() == null) {
            message += "La categoría es requerida.\n";
        }
        if (depositDto.amount() < 0) {
            message += "El monto no puede ser negativo.\n";
        }
        if (depositDto.description().isEmpty()) {
            message += "La descripción es requerida.\n";
        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }


    @Override
    public void updateView(EventType event) {
        if (event == EventType.ACCOUNT) {
            initializeDataComboBox();
        }

    }
}
