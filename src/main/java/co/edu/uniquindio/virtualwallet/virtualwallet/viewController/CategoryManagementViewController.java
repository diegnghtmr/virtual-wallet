package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.CategoryManagementController;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        return false;
    }

    private void addCategory() {
    }

    private void removeCategory() {
    }

    private void updateCategory() {
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
