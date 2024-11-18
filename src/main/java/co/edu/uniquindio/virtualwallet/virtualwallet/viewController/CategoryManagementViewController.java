package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.CategoryManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
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

import java.security.SecureRandom;
import java.util.ArrayList;

public class CategoryManagementViewController extends CoreViewController implements ICoreViewController<CategoryDto> {
    CategoryManagementController categoryManagementController;
    User loggedUser;
    ObservableList<CategoryDto> categoryListDto = FXCollections.observableArrayList();
    CategoryDto categorySelected;

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
    private TableView<CategoryDto> tblCatergory;

    @FXML
    private TableColumn<CategoryDto, String> tcDescription;

    @FXML
    private TableColumn<CategoryDto, String> tcId;

    @FXML
    private TableColumn<CategoryDto, String> tcName;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtaDescription;

    @FXML
    public void onAdd(ActionEvent event) {
        addCategory();
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
        removeCategory();
    }

    @FXML
    public void onUpdate(ActionEvent event) {
        updateCategory();
    }

    @FXML
    public void initialize() {
        categoryManagementController = new CategoryManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();
    }

    @Override
    public void initView() {
        initDataBinding();
        getCategories();
        tblCatergory.getItems().clear();
        tblCatergory.setItems(categoryListDto);
        listenerSelection();
    }

    @Override
    public void initDataBinding() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().id()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().name()));
        tcDescription.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().description()));
    }

    private void getCategories() {
        String userId = loggedUser.getId();
        categoryListDto.clear();
        categoryListDto.addAll(categoryManagementController.getCategoriesByUser(userId));
    }

    @Override
    public void listenerSelection() {
        tblCatergory.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                categorySelected = newSelection;
                showInformation(categorySelected);
        });
    }

    @Override
    public void showInformation(CategoryDto categorySelected) {
        if (categorySelected != null) {
            txtName.setText(categorySelected.name());
            txtaDescription.setText(categorySelected.description());
        }
    }

    @Override
    public boolean validateData(CategoryDto categoryDto) {
        String message = "";
        if (categoryDto.name().isEmpty()) {
            message += "El nombre de la categoría no puede estar vacío.\n";
        }
        if (categoryDto.description().isEmpty()) {
            message += "La descripción de la categoría no puede estar vacía.\n";
        }
        if (!message.isEmpty()) {
            showMessage("Validación de Datos", "Datos Inválidos", message, Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private CategoryDto buildCategoryDto() {
        String id;
        if (categorySelected != null) {
            id = categorySelected.id();
        } else {
            SecureRandom random = new SecureRandom();
            do {
                id = String.format("%09d", random.nextInt(1_000_000_000));
            } while (categoryManagementController.isCategoryExists(id));
        }
        String name = txtName.getText().trim();
        String description = txtaDescription.getText().trim();
        return new CategoryDto(id, name, description, new ArrayList<BudgetDto>(), new ArrayList<Transaction>());
    }

    private void addCategory() {
        CategoryDto newCategory = buildCategoryDto();
        if (validateData(newCategory)) {
            boolean success = categoryManagementController.addCategory(loggedUser.getId(), newCategory);
            if (success) {
                showMessage("Éxito", "Categoría Añadida", "La categoría se ha añadido correctamente.", Alert.AlertType.INFORMATION);
                getCategories();
                clearFields();
            } else {
                showMessage("Error", "Añadir Categoría", "No se pudo añadir la categoría.", Alert.AlertType.ERROR);
            }
        }
    }

    private void removeCategory() {
        if (categorySelected != null) {
            boolean confirm = showConfirmationMessage("¿Está seguro de que desea eliminar la categoría seleccionada?");
            if (confirm) {
                boolean success = categoryManagementController.removeCategory(loggedUser.getId(), categorySelected.id());
                if (success) {
                    showMessage("Éxito", "Categoría Eliminada", "La categoría se ha eliminado correctamente.", Alert.AlertType.INFORMATION);
                    getCategories();
                    clearFields();
                } else {
                    showMessage("Error", "Eliminar Categoría", "No se pudo eliminar la categoría.", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Información", "Sin Selección", "Por favor, seleccione una categoría para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private void updateCategory() {
        boolean categoryUpdated = false;
        CategoryDto categoryDto = buildCategoryDto();
        if (categorySelected != null) {
            if (validateData(categoryDto)) {
                if (!hasChanges(categorySelected, categoryDto)) {
                    showMessage("Información", "Sin Cambios", "No se han realizado cambios en la categoría.", Alert.AlertType.INFORMATION);
                    return;
                }
                int selecIndex = tblCatergory.getSelectionModel().getSelectedIndex();
                categoryUpdated = categoryManagementController.updateCategory(loggedUser.getId(), categorySelected, categoryDto);
                if (categoryUpdated) {
                    categoryListDto.set(selecIndex, categoryDto);
                    tblCatergory.refresh();
                    tblCatergory.getSelectionModel().select(selecIndex);
                    showMessage("Éxito", "Categoría Actualizada", "La categoría se ha actualizado correctamente.", Alert.AlertType.INFORMATION);
                    deselectTable();
                    clearFields();
                } else {
                    showMessage("Error", "Actualizar Categoría", "No se pudo actualizar la categoría.", Alert.AlertType.ERROR);
                }
            }
        } else {
            showMessage("Información", "Sin Selección", "Por favor, seleccione una categoría para actualizar.", Alert.AlertType.WARNING);
        }
    }

    private boolean hasChanges(CategoryDto categorySelected, CategoryDto categoryDto) {
        return !categorySelected.name().equals(categoryDto.name()) || !categorySelected.description().equals(categoryDto.description());
    }

    @Override
    public void clearFields() {
        txtaDescription.clear();
        txtName.clear();
    }

    @Override
    public void deselectTable() {
        tblCatergory.getSelectionModel().clearSelection();
        categorySelected = null;
    }

}
