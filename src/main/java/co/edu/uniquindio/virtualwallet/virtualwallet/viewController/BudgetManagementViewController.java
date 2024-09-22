package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.BudgetDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ICoreViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BudgetManagementViewController extends CoreViewController implements ICoreViewController<BudgetDto> {
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
    private ChoiceBox<?> cbCategory;

    @FXML
    private TableView<?> tblBudget;

    @FXML
    private TableColumn<?, ?> tcCategory;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcamountSpent;

    @FXML
    private TableColumn<?, ?> tctotalAmount;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txttotalAmount;

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
    public void showInformation(BudgetDto budgetSelected) {

    }

    @Override
    public boolean validateData(BudgetDto budgetDto) {
        return false;
    }

    @Override
    public void clearFields() {

    }

    @Override
    public void deselectTable() {

    }

}
