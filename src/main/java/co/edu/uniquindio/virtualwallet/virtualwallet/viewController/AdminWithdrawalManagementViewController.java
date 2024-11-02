package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminWithdrawalManagementViewController {

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


    }

}
