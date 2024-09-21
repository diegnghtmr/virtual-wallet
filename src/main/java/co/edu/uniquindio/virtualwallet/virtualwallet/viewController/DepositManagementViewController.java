package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DepositManagementViewController {

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
    private TableView<?> tblDeposit;

    @FXML
    private TableColumn<?, ?> tcAccount;

    @FXML
    private TableColumn<?, ?> tcAmount;

    @FXML
    private TableColumn<?, ?> tcCategory;

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
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onNew(ActionEvent event) {

    }

    @FXML
    void onNotification(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}

