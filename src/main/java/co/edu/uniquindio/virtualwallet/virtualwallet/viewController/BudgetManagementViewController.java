package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.BudgetManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.BudgetDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.EventType;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.observer.ObserverView;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ICoreViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.security.SecureRandom;

public class BudgetManagementViewController extends CoreViewController implements ICoreViewController<BudgetDto>, ObserverView {
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
        budgetListDto.clear();
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

    @Override
    public boolean validateData(BudgetDto budgetDto) {
        String message = "";
        if (budgetDto.name().isEmpty()) {
            message += "El nombre del presupuesto es requerido.\n";
        }
        if (budgetDto.totalAmount() <= 0) {
            message += "El monto total debe ser mayor a cero.\n";
        }
        if (budgetDto.category() == null) {
            message += "La categoría es requerida.\n";
        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private BudgetDto buildBudgetDto() {
        String id;
        if (budgetSelected != null) {
            id = budgetSelected.id();
        } else {
            SecureRandom random = new SecureRandom();
            do {
                id = String.format("%09d", random.nextInt(1_000_000_000));
            } while (budgetManagementController.isBudgetIdExists(id));
        }
        String name = txtNombre.getText();
        double totalAmount = 0;
        try {
            totalAmount = Double.parseDouble(txtTotalAmount.getText());
        } catch (NumberFormatException e) {
            showMessage("Error", "Monto total inválido", "El monto total debe ser un número", Alert.AlertType.ERROR);
            return null;
        }
        double amountSpent = (budgetSelected != null) ? budgetSelected.amountSpent() : 0;
        CategoryDto category = cbCategory.getSelectionModel().getSelectedItem();
        User user = loggedUser;

        return new BudgetDto(id, name, totalAmount, amountSpent, category, user);
    }

    private void addBudget() {
        BudgetDto budgetDto = buildBudgetDto();
        if (budgetDto == null) {
            return;
        }
        if (validateData(budgetDto)) {
            if (budgetManagementController.addBudget(budgetDto)) {
                showMessage("Notificación", "Presupuesto agregado", "El presupuesto se agregó correctamente", Alert.AlertType.INFORMATION);
                getBudgets();
                clearFields();
            } else {
                showMessage("Error", "Error al agregar presupuesto", "No se pudo agregar el presupuesto", Alert.AlertType.ERROR);
            }
        }
    }

    private void removeBudget() {
        if (budgetSelected != null) {
            boolean confirmation = showConfirmationMessage("¿Está seguro de eliminar el presupuesto?");
            if (confirmation) {
                if (budgetManagementController.removeBudget(budgetSelected)) {
                    showMessage("Notificación", "Presupuesto eliminado", "El presupuesto se eliminó correctamente", Alert.AlertType.INFORMATION);
                    getBudgets();
                    clearFields();
                } else {
                    showMessage("Error", "Error al eliminar presupuesto", "No se pudo eliminar el presupuesto", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Advertencia", "Ningún presupuesto seleccionado", "Seleccione un presupuesto para eliminar", Alert.AlertType.WARNING);
        }
    }

    private void updateBudget() {
        boolean budgetUpdated = false;
        BudgetDto budgetDto = buildBudgetDto();
        if (budgetDto == null) {
            return;
        }
        if (budgetSelected != null) {
            if (validateData(budgetDto)) {
                if (!hasChanges(budgetSelected, budgetDto)) {
                    showMessage("Error", "Sin cambios", "No se realizaron cambios en el presupuesto", Alert.AlertType.WARNING);
                    return;
                }
                int selectedIndex = tblBudget.getSelectionModel().getSelectedIndex();
                budgetUpdated = budgetManagementController.updateBudget(budgetSelected, budgetDto);
                if (budgetUpdated) {
                    budgetListDto.set(selectedIndex, budgetDto);
                    tblBudget.refresh();
                    tblBudget.getSelectionModel().select(selectedIndex);
                    showMessage("Notificación", "Presupuesto actualizado", "El presupuesto se actualizó correctamente", Alert.AlertType.INFORMATION);
                    deselectTable();
                    clearFields();
                } else {
                    showMessage("Error", "Error al actualizar presupuesto", "No se pudo actualizar el presupuesto", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Advertencia", "Ningún presupuesto seleccionado", "Seleccione un presupuesto para actualizar", Alert.AlertType.WARNING);
        }
    }

    private boolean hasChanges(BudgetDto budgetSelected, BudgetDto budgetDto) {
        return !budgetSelected.name().equals(budgetDto.name()) ||
                budgetSelected.totalAmount() != budgetDto.totalAmount() ||
                !budgetSelected.category().id().equals(budgetDto.category().id());
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

    @Override
    public void updateView(EventType event) {
        if (event == EventType.CATEGORY) {
            initializeDataComboBox();
        }
    }
}
