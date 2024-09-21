package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BudgetManagementViewController {
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
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onNew(ActionEvent event) {

    }

    @FXML
    void onNotification(ActionEvent event) {

    }

    @FXML
    void onRemove(ActionEvent event) {

    }

    @FXML
    void onUpdate(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert btnNew != null : "fx:id=\"btnNew\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert btnNotification != null : "fx:id=\"btnNotification\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert btnRemove != null : "fx:id=\"btnRemove\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert cbCategory != null : "fx:id=\"cbCategory\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert tblBudget != null : "fx:id=\"tblBudget\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert tcCategory != null : "fx:id=\"tcCategory\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert tcId != null : "fx:id=\"tcId\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert tcName != null : "fx:id=\"tcName\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert tcamountSpent != null : "fx:id=\"tcamountSpent\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert tctotalAmount != null : "fx:id=\"tctotalAmount\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'budget-management-view.fxml'.";
        assert txttotalAmount != null : "fx:id=\"txttotalAmount\" was not injected: check your FXML file 'budget-management-view.fxml'.";

    }

}
