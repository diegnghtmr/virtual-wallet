package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ICoreViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CategoryManagementViewController extends CoreViewController implements ICoreViewController<CategoryDto> {

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
    private TableView<?> tblCatergory;

    @FXML
    private TableColumn<?, ?> tcDescription;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtaDescription;

    @FXML
    public void onAdd(ActionEvent event) {

    }

    @FXML
    public void onNew(ActionEvent event) {

    }

    @FXML
    public void onNotification(ActionEvent event) {

    }

    @FXML
    public void onRemove(ActionEvent event) {

    }

    @FXML
    public void onUpdate(ActionEvent event) {

    }

    @FXML
    public void initialize() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initDataBinding() {

    }

    @Override
    public void listenerSelection() {

    }

    @Override
    public void showInformation(CategoryDto categorySelected) {

    }

    @Override
    public boolean validateData(CategoryDto categoryDto) {
        return false;
    }

    @Override
    public void clearFields() {

    }

    @Override
    public void deselectTable() {

    }

}
