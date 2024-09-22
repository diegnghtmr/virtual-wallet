package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.WithdrawalDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.ITransactionViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class WithdrawalManagementViewController extends CoreViewController implements ITransactionViewController<WithdrawalDto> {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnNotification;

    @FXML
    private ComboBox<?> cbAccount;

    @FXML
    private ComboBox<?> cbCategory;

    @FXML
    private TableView<?> tblWithdrawal;

    @FXML
    private TableColumn<?, ?> tcAccount;

    @FXML
    private TableColumn<?, ?> tcAmount;

    @FXML
    private TableColumn<?, ?> tcCategory;

    @FXML
    private TableColumn<?, ?> tcCommission;

    @FXML
    private TableColumn<?, ?> tcDate;

    @FXML
    private TableColumn<?, ?> tcDescription;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TextField txtAmount;

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
    public boolean validateData(WithdrawalDto withdrawalDto) {
        return false;
    }

    @Override
    public void clearFields() {

    }

    @Override
    public void deselectTable() {

    }

}
