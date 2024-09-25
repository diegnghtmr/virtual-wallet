package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.MovementManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IRecordViewController;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IReportGenerationViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MovementManagementViewController extends CoreViewController implements IRecordViewController<TransactionDto>, IReportGenerationViewController {
    MovementManagementController movementManagementController;
    ObservableList<TransactionDto> transactionsListDto = FXCollections.observableArrayList();
    TransactionDto transactionSelected;


    @FXML
    private Button btnGenerateCSV;

    @FXML
    private Button btnGeneratePDF;

    @FXML
    private Button btnGetCurrentRecords;

    @FXML
    private Button btnGetPreviousRecords;

    @FXML
    private Button btnGetSubsequentRecords;

    @FXML
    private Button btnNotification;

    @FXML
    private ComboBox<AccountDto> cbAccount;

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
    public void onGenerateCSV(ActionEvent event) {

    }

    @FXML
    public void onGeneratePDF(ActionEvent event) {

    }

    @FXML
    public void onGetCurrentRecords(ActionEvent event) {

    }

    @FXML
    public void onGetPreviousRecords(ActionEvent event) {
        getPreviousRecords();

    }


    @FXML
    public void onGetSubsequentRecords(ActionEvent event) {

    }

    @FXML
    public void onNotification(ActionEvent event) {

    }

    @FXML
    public void initialize() {
//        movementManagementController = new MovementManagementController();
//        initView();

    }

    @Override
    public void initView() {
//        initDataBinding();
//        getTransactionList();
//        loadDataComboBox();
//        tblMovement.getItems().clear();
//        tblMovement.setItems(transactionsListDto);
//        listenerSelection();
//

    }


    @Override
    public void initDataBinding() {
//        tcAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().account() !=null? cellData.getValue().account().accountNumber(): ""));
//        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().amount())));
//        tcDateMovement.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().date())));
//        tcState.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().state()));
//        tcIdTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().idTransaction())));
//        tcTransactionType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().transactionType()));


    }
    private void getTransactionList() {
//        transactionsListDto.addAll(movementManagementController.getTransactionList());
//

    }

    private void loadDataComboBox() {
//        List<AccountDto> accountListData = movementManagementController.getAccountList();
//        ObservableList<AccountDto> accountList = FXCollections.observableArrayList(accountListData != null ? accountListData : Collections.emptyList());
//
//        initializeComboBox(cbAccount, accountList, account -> account != null ? account.toString() : "");
    }

//    protected  <T> void initializeComboBox(ComboBox<T> comboBox, ObservableList<T> items, Function<T, String> displayFunction) {
//        comboBox.setItems(items);
//        comboBox.setCellFactory(lv -> new ListCell<T>() {
//            @Override
//            protected void updateItem(T item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(empty ? "" : displayFunction.apply(item));
//            }
//        });
//        comboBox.setButtonCell(new ListCell<T>() {
//            @Override
//            protected void updateItem(T item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(empty ? "" : displayFunction.apply(item));
//            }
//        });
//    }

    @Override
    public void listenerSelection() {
//        tblMovement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
//            transactionSelected = newSelection;
//            showInformationMovement(transactionSelected);
//        });

    }

    private void showInformationMovement(TransactionDto transactionSelected) {
//        if (transactionSelected != null) {
//            cbAccount.setValue(transactionSelected.account());
//            dpDate.setValue(transactionSelected.date());
//        }
    }

    @Override
    public boolean validateData(TransactionDto transactionDto) {
        return false;
    }

    @Override
    public void clearFields() {

    }

    @Override
    public void deselectTable() {
    }

    private void getPreviousRecords() {
//        LocalDate date = dpDate.getValue();
//        AccountDto accountDto = cbAccount.getValue();
//        if(date != null && accountDto !=null) {
//            transactionsListDto.clear();
//            transactionsListDto.addAll(movementManagementController.getPreviousRecords(date, accountDto));
//        }


    }


}
