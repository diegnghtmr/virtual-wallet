package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.AdminWithdrawalManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.WithdrawalDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminWithdrawalManagementViewController extends CoreViewController {
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

    }

    @FXML
    void onHome(ActionEvent event) {

    }

    @FXML
    void onNew(ActionEvent event) {

    }

    @FXML
    void initialize() {
        adminWithdrawalManagementController = new AdminWithdrawalManagementController();
        initView();


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
        if (withdrawalSelected !=null) {
            txtaDescription.setText(withdrawalSelected.description());
            txtAmount.setText(String.valueOf(withdrawalSelected.amount()));
            cbAccount.setValue(withdrawalSelected.account());
            cbCategory.setValue(withdrawalSelected.category());

        }
    }

}
