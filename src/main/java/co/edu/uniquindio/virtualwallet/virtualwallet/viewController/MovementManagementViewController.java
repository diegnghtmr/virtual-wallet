package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.MovementManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IRecordViewController;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IReportGenerationViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class MovementManagementViewController extends CoreViewController implements IRecordViewController<TransactionDto> {
    MovementManagementController movementManagementController;
    User loggedUser;
    ObservableList<TransactionDto> transactionsListDto = FXCollections.observableArrayList();
    TransactionDto transactionSelected;

    @FXML
    private Button btnGenerateReport;

    @FXML
    private Button btnGetCurrentRecords;

    @FXML
    private Button btnGetAllRecords;

    @FXML
    private Button btnGetPreviousRecords;

    @FXML
    private Button btnGetSubsequentRecords;

    @FXML
    private Button btnNotification;

    @FXML
    private ComboBox<Account> cbAccount;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<TransactionDto> tblMovement;

    @FXML
    private TableColumn<TransactionDto, String> tcAccount;

    @FXML
    private TableColumn<TransactionDto, String> tcAmount;

    @FXML
    private TableColumn<TransactionDto, String> tcDateMovement;

    @FXML
    private TableColumn<TransactionDto, String> tcIdTransaction;

    @FXML
    private TableColumn<TransactionDto, String> tcState;

    @FXML
    private TableColumn<TransactionDto, String> tcTransactionType;

    @FXML
    void onGenerateReport(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/view/report-generation-view.fxml", "Generación de reportes", ownerStage);
    }

    @FXML
    public void onGetCurrentRecords(ActionEvent event) {
        getCurrentRecords();
    }

    @FXML
    void onGetAllRecords(ActionEvent event) {
        getAllRecords();
    }

    @FXML
    public void onGetPreviousRecords(ActionEvent event) {
        getPreviousRecords();

    }

    @FXML
    public void onGetSubsequentRecords(ActionEvent event) {
        getSubsequentRecords();
    }

    @FXML
    public void onNotification(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/view/notification-view.fxml", "Notificaciones", ownerStage);
    }

    @FXML
    public void initialize() {
        movementManagementController = new MovementManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();

    }

    @Override
    public void initView() {
        initDataBinding();
        getTransactionList();
        initializeDataComboBox();
        tblMovement.getItems().clear();
        tblMovement.setItems(transactionsListDto);
        listenerSelection();
    }


    @Override
    public void initDataBinding() {
        tcAccount.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().account().getBankName() + " - "
                        + cellData.getValue().account().getAccountNumber()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().amount())));
        tcDateMovement.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().date().toString()));
        tcIdTransaction.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().idTransaction()));
        tcState.setCellValueFactory(data -> {
            String statusKey = "transaction.status." + data.getValue().status();
            String localizedStatus = I18n.get(statusKey);
            return new SimpleStringProperty(localizedStatus);
        });
        tcTransactionType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().transactionType()));


    }

    private void getTransactionList() {
        String userId = loggedUser.getId();
        transactionsListDto.addAll(movementManagementController.getTransactionsByUser(userId));

    }

    private void initializeDataComboBox() {
        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(
                movementManagementController.getAccountsByUserId(loggedUser.getId()));

        initializeComboBox(cbAccount, accountDtoList,
                account -> account.getBankName() + " - " + account.getAccountNumber());

    }

    @Override
    public void listenerSelection() {
        tblMovement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            transactionSelected = newSelection;
            showInformation();
        });
    }

    private void showInformation() {
        if(transactionSelected != null) {
            cbAccount.setValue(transactionSelected.account());
            dpDate.setValue(transactionSelected.date());
        }
    }

    @Override
    public void clearFields() {
        cbAccount.getSelectionModel().clearSelection();
        dpDate.setValue(null);
    }

    @Override
    public void deselectTable() {
        tblMovement.getSelectionModel().clearSelection();
        transactionSelected = null;
    }

    @Override
    public boolean validateData() {
        String message = "";
        if (cbAccount.getValue() == null) {
            message += "La cuenta es requerida.\n";
        }
        if (dpDate.getValue() == null) {
            message += "La fecha es requerida.\n";
        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void getCurrentRecords() {
        if (validateData()) {
            Account selectedAccount = cbAccount.getValue();
            LocalDate selectedDate = dpDate.getValue();

            List<TransactionDto> filteredTransactions = transactionsListDto.stream()
                    .filter(transaction -> transaction.account().getAccountNumber().equals(selectedAccount.getAccountNumber()) &&
                            transaction.date().equals(selectedDate))
                    .collect(Collectors.toList());

            tblMovement.setItems(FXCollections.observableArrayList(filteredTransactions));
        }
    }

    private void getAllRecords() {
        tblMovement.setItems(transactionsListDto);
        clearFields();
        deselectTable();
    }

    private void getPreviousRecords() {
        if (validateData()) {
            Account selectedAccount = cbAccount.getValue();
            LocalDate selectedDate = dpDate.getValue();

            List<TransactionDto> filteredTransactions = transactionsListDto.stream()
                    .filter(transaction -> transaction.account().getAccountNumber().equals(selectedAccount.getAccountNumber()) &&
                            transaction.date().isBefore(selectedDate))
                    .collect(Collectors.toList());

            tblMovement.setItems(FXCollections.observableArrayList(filteredTransactions));
        }
    }

    private void getSubsequentRecords() {
        if (validateData()) {
            Account selectedAccount = cbAccount.getValue();
            LocalDate selectedDate = dpDate.getValue();

            List<TransactionDto> filteredTransactions = transactionsListDto.stream()
                    .filter(transaction -> transaction.account().getAccountNumber().equals(selectedAccount.getAccountNumber()) &&
                            transaction.date().isAfter(selectedDate))
                    .collect(Collectors.toList());

            tblMovement.setItems(FXCollections.observableArrayList(filteredTransactions));
        }
    }
}
