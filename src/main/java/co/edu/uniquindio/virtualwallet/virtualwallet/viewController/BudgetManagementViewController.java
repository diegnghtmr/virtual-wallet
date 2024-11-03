package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.BudgetManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.BudgetDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ICoreViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BudgetManagementViewController extends CoreViewController implements ICoreViewController<BudgetDto> {
    BudgetManagementController budgetManagementController;
    User loggedUser;
    ObservableList<BudgetDto> budgetListDto = FXCollections.observableArrayList();
    BudgetDto budgetSelected;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnNotification;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<CategoryDto> cbCategory;

    @FXML
    private TableView<BudgetDto> tblBudget;

    @FXML
    private TableColumn<BudgetDto, String> tcAmountSpent;

    @FXML
    private TableColumn<BudgetDto, String> tcCategory;

    @FXML
    private TableColumn<BudgetDto, String> tcId;

    @FXML
    private TableColumn<BudgetDto, String> tcName;

    @FXML
    private TableColumn<BudgetDto, String> tcTotalAmount;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTotalAmount;

    @FXML
    public void onAdd(ActionEvent event) {
        addBudget();
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
    public void onRemove(ActionEvent event) {
        removeBudget();
    }

    @FXML
    public void onUpdate(ActionEvent event) {
        updateBudget();
    }

    @FXML
    public void initialize() {
        budgetManagementController = new BudgetManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();
    }

    @Override
    public void initView() {
        initDataBinding();
        getBudgets();
        initializeDataComboBox();
        tblBudget.getItems().clear();
        tblBudget.setItems(budgetListDto);
        listenerSelection();
    }

    @Override
    public void initDataBinding() {
        tcCategory.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().category().name()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().name()));
        tcTotalAmount.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().totalAmount())));
        tcAmountSpent.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().amountSpent())));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().id())));
    }

    private void getBudgets() {
        String userId = loggedUser.getId();
        budgetListDto.addAll(budgetManagementController.getBudgetsByUser(userId));
    }

    private void initializeDataComboBox() {
        ObservableList<CategoryDto> categoryDtoList = FXCollections.observableArrayList(
                budgetManagementController.getCategoriesByUserId(loggedUser.getId()));

        initializeComboBox(cbCategory, categoryDtoList, CategoryDto::name);
    }

    @Override
    public void listenerSelection() {
        tblBudget.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            budgetSelected = newSelection;
            showInformation(budgetSelected);
        });
    }

    @Override
    public void showInformation(BudgetDto budgetSelected) {
        if (budgetSelected != null) {
            txtNombre.setText(budgetSelected.name());
            txtTotalAmount.setText(String.valueOf(budgetSelected.totalAmount()));
            cbCategory.setValue(budgetSelected.category());
        }
    }

    private void addBudget() {
    }

    @Override
    public boolean validateData(BudgetDto budgetDto) {
        return false;
    }

    private void removeBudget() {
    }

    private void updateBudget() {
    }

    @Override
    public void clearFields() {
        txtNombre.clear();
        txtTotalAmount.clear();
        cbCategory.getSelectionModel().clearSelection();
    }

    @Override
    public void deselectTable() {
        tblBudget.getSelectionModel().clearSelection();
        budgetSelected = null;
    }

}
