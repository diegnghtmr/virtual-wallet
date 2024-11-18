package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.DepositManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.DepositDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.EventType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverManagement;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
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

public class DepositManagementViewController extends CoreViewController implements ITransactionViewController<DepositDto>, ObserverView {
    DepositManagementController depositManagementController;
    User loggedUser;
    ObservableList<DepositDto>  depositListDto = FXCollections.observableArrayList();
    DepositDto depositSelected;

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
    public void onAdd(ActionEvent event) {
        addDeposit();
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
        depositManagementController = new DepositManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.ACCOUNT, this);
    }

    @Override
    public void initView() {
        initDataBinding();
        getDeposits();
        initializeDataComboBox();
        tblDeposit.getItems().clear();
        tblDeposit.setItems(depositListDto);
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
                cellData.getValue().category().name()));
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().date().toString()));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().description()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().idTransaction()));
    }

    private void getDeposits() {
        String userId = loggedUser.getId();
        depositListDto.clear();
        depositListDto.addAll(depositManagementController.getDepositsByUser(userId));
    }

    private void initializeDataComboBox() {
        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(
                depositManagementController.getAccountsByUserId(loggedUser.getId()));
        ObservableList<CategoryDto> categoryDtoList = FXCollections.observableArrayList(
                depositManagementController.getCategoriesByUserId(loggedUser.getId()));

//        cbAccount.setItems(accountDtoList);
//        cbCategory.setItems(categoryDtoList);

        initializeComboBox(cbAccount, accountDtoList,
                account -> account.getBankName() + " - " + account.getAccountNumber());

        initializeComboBox(cbCategory, categoryDtoList, CategoryDto::name);
    }

    @Override
    public void listenerSelection() {
        tblDeposit.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            depositSelected = newSelection;
            showInformation();
        });
    }

    private void showInformation() {
        if (depositSelected != null) {
            cbAccount.setValue(depositSelected.account());
            cbCategory.setValue(depositSelected.category());
            txtAmount.setText(String.valueOf(depositSelected.amount()));
            txtaDescription.setText(depositSelected.description());
        }
    }

    private void addDeposit() {
        DepositDto depositDto = buildDeposit();
        if (depositDto == null) {
            return;
        }
        if (validateData(depositDto)) {
            if (depositManagementController.addDeposit(depositDto)) {
                depositListDto.add(depositDto);
                showMessage("Depósito", "Depósito agregado correctamente",
                        "El depósito ha sido agregado correctamente", Alert.AlertType.INFORMATION);
                clearFields();
                ObserverManagement.getInstance().notifyObservers(EventType.DEPOSIT);
            } else {
                showMessage("Error", "Error al agregar el depósito",
                        "Ha ocurrido un error al agregar el depósito, por favor intente nuevamente", Alert.AlertType.ERROR);
            }
        }
    }

    private DepositDto buildDeposit() {
        String idNumber;
        if (depositSelected != null) {
            idNumber = depositSelected.idTransaction();
        } else {
            SecureRandom random = new SecureRandom();
            do {
                idNumber = String.format("%09d", random.nextInt(1_000_000_000));
            } while (depositManagementController.isTransactionIdExists(idNumber));
        }

        String amountText = txtAmount.getText();
        if (amountText.isEmpty()) {
            showMessage("Error", "El monto no puede estar vacío",
                    "Por favor, ingrese un monto válido", Alert.AlertType.ERROR);
            return null;
        }
        return new DepositDto(
                idNumber, // idTransaction, generated randomly and checked for uniqueness
                LocalDate.now(), // date, the current date
                Double.parseDouble(txtAmount.getText()), // amount, the value from txtAmount field
                txtaDescription.getText(), // description, the value from txtaDescription field
                cbCategory.getValue(), // category, the selected value from cbCategory
                cbAccount.getValue(), // account, the selected value from cbAccount
                TransactionStatus.PENDING.name()
        );
    }

    @Override
    public boolean validateData(DepositDto depositDto) {
        String message = "";
        if (depositDto.amount() <= 0) {
            message += "El monto debe ser mayor a cero\n";
        }
        if (depositDto.account() == null) {
            message += "Debe seleccionar una cuenta\n";
        }
        if (depositDto.category() == null) {
            message += "Debe seleccionar una categoría\n";
        }
        if (depositDto.description().isEmpty()) {
            message += "Debe ingresar una descripción\n";
        }
        if (!message.isEmpty()) {
            showMessage("Error", message,
                    "Por favor, corrija estos errores", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    @Override
    public void clearFields() {
        cbAccount.getSelectionModel().clearSelection();
        cbCategory.getSelectionModel().clearSelection();
        txtAmount.clear();
        txtaDescription.clear();
    }

    @Override
    public void deselectTable() {
        tblDeposit.getSelectionModel().clearSelection();
        depositSelected = null;
    }

    @Override
    public void updateView(EventType event) {
        if (event == EventType.ACCOUNT) {
            initializeDataComboBox();
        }
    }
}

