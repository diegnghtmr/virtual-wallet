package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.TransferManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.TransferDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ITransactionViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransferManagementViewController extends CoreViewController implements ITransactionViewController<TransferDto> {
    TransferManagementController transferManagementController;
    User loggedUser;
    ObservableList<TransferDto> transferListDto = FXCollections.observableArrayList();
    TransferDto transferSelected;

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
    }

    @Override
    public boolean validateData(TransferDto transferDto) {
        return false;
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

}

