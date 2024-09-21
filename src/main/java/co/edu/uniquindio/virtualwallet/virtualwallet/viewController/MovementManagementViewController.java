package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class MovementManagementViewController {

    @FXML
    private Button btnGenerateCSV;

    @FXML
    private Button btnGeneratePDF;

    @FXML
    private Button btnGetCurrentRecords;

    @FXML
    private Button btnGetPreviousRecords;

    @FXML
    private Button btnGetSubsequentRecords;

    @FXML
    private Button btnNotification;

    @FXML
    private ChoiceBox<?> cbAccount;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<?> tblMovement;

    @FXML
    private TableColumn<?, ?> tcAccount;

    @FXML
    private TableColumn<?, ?> tcAmount;

    @FXML
    private TableColumn<?, ?> tcDateMovement;

    @FXML
    private TableColumn<?, ?> tcIdTransaction;

    @FXML
    private TableColumn<?, ?> tcState;

    @FXML
    private TableColumn<?, ?> tcTransactionType;

    @FXML
    void onGenerateCSV(ActionEvent event) {

    }

    @FXML
    void onGeneratePDF(ActionEvent event) {

    }

    @FXML
    void onGetCurrentRecords(ActionEvent event) {

    }

    @FXML
    void onGetPreviousRecords(ActionEvent event) {

    }

    @FXML
    void onGetSubsequentRecords(ActionEvent event) {

    }

    @FXML
    void onNotification(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
