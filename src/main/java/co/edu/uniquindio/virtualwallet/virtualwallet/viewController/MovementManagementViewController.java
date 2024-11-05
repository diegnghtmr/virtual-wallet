package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.MovementManagementController;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IRecordViewController;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IReportGenerationViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;


public class MovementManagementViewController extends CoreViewController implements IRecordViewController<TransactionDto>, IReportGenerationViewController {
    MovementManagementController movementManagementController;
    User loggedUser;
    ObservableList<TransactionDto> transactionsListDto = FXCollections.observableArrayList();
    TransactionDto transactionSelected;

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
    private ComboBox<Account> cbAccount;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<TransactionDto> tblMovement;

    @FXML
    private TableColumn<TransactionDto, String> tcAccount;

    @FXML
    private TableColumn<TransactionDto, String> tcAmount;

    @FXML
    private TableColumn<TransactionDto, String> tcDateMovement;

    @FXML
    private TableColumn<TransactionDto, String> tcIdTransaction;

    @FXML
    private TableColumn<TransactionDto, String> tcState;

    @FXML
    private TableColumn<TransactionDto, String> tcTransactionType;

    @FXML
    public void onGenerateCSV(ActionEvent event) {
        generateCSV();
    }

    @FXML
    public void onGeneratePDF(ActionEvent event) {
        generatePDF();
    }

    @FXML
    public void onGetCurrentRecords(ActionEvent event) {
        getCurrentRecords();
    }

    @FXML
    public void onGetPreviousRecords(ActionEvent event) {
        getPreviousRecords();

    }


    @FXML
    public void onGetSubsequentRecords(ActionEvent event) {
        getSubsequentRecords();
    }

    @FXML
    public void onNotification(ActionEvent event) {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openWindow("/view/notification-view.fxml", "Notificaciones", ownerStage);
    }

    @FXML
    public void initialize() {
        movementManagementController = new MovementManagementController();
        loggedUser = (User) Session.getInstance().getPerson();
        initView();

    }

    @Override
    public void initView() {
        initDataBinding();
        getTransactionList();
        initializeDataComboBox();
        tblMovement.getItems().clear();
        tblMovement.setItems(transactionsListDto);
        listenerSelection();
    }


    @Override
    public void initDataBinding() {
        tcAccount.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().account().getBankName() + " - "
                        + cellData.getValue().account().getAccountNumber()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().amount())));
        tcDateMovement.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().date().toString()));
        tcIdTransaction.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().idTransaction()));
        tcState.setCellValueFactory(data -> {
            String statusKey = "transaction.status." + data.getValue().status();
            String localizedStatus = I18n.get(statusKey);
            return new SimpleStringProperty(localizedStatus);
        });
        tcTransactionType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().transactionType()));


    }

    private void getTransactionList() {
        String userId = loggedUser.getId();
        transactionsListDto.addAll(movementManagementController.getTransactionsByUser(userId));

    }
    private void initializeDataComboBox() {
        ObservableList<Account> accountDtoList = FXCollections.observableArrayList(
                movementManagementController.getAccountsByUserId(loggedUser.getId()));

        initializeComboBox(cbAccount, accountDtoList,
                account -> account.getBankName() + " - " + account.getAccountNumber());

    }


    @Override
    public void listenerSelection() {
        tblMovement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            transactionSelected = newSelection;
            showInformation();
        });
    }

    private void showInformation() {
        if(transactionSelected != null) {
            cbAccount.setValue(transactionSelected.account());
            dpDate.setValue(transactionSelected.date());
        }
    }

    @Override
    public void clearFields() {
        cbAccount.getSelectionModel().clearSelection();
        dpDate.setValue(null);
    }

    @Override
    public void deselectTable() {
        tblMovement.getSelectionModel().clearSelection();
        transactionSelected = null;
    }

    @Override
    public boolean validateData(TransactionDto transactionDto) {
        return false;
    }

    private void generateCSV() {
        String userId = loggedUser.getId();

        // Crear una instancia de ReportGeneration para CSV
        ReportGeneration reportGeneration = new ReportGeneration("CSV");
        reportGeneration.addObserver(loggedUser); // Añadir al usuario como observador

        // Generar el reporte
        File csvFile = reportGeneration.generateReport(transactionsListDto, userId);

        if (csvFile != null && csvFile.exists()) {
            String userEmail = loggedUser.getEmail();
            String subject = "Reporte de Transacciones CSV - Usuario ID: " + userId;
            String message = "Estimado " + loggedUser.getFullName() + ", adjunto encontrará su reporte de transacciones en formato CSV.";

            EmailAttachmentUtil emailUtil = new EmailAttachmentUtil(userEmail, subject, message, csvFile);
            emailUtil.sendNotification();

            showMessage("Éxito", "CSV Generado y Enviado", "El reporte CSV ha sido generado y enviado a su correo.", Alert.AlertType.INFORMATION);
        } else {
            showMessage("Error", "CSV No Generado", "Ocurrió un error al generar el reporte CSV.", Alert.AlertType.ERROR);
        }
        movementManagementController.generateSerialization();
    }


    private void generatePDF() {
        String userId = loggedUser.getId();

        // Crear una instancia de ReportGeneration para PDF
        ReportGeneration reportGeneration = new ReportGeneration("PDF");
        reportGeneration.addObserver(loggedUser); // Añadir al usuario como observador

        // Generar el reporte
        File pdfFile = reportGeneration.generateReport(transactionsListDto, userId);

        if (pdfFile != null && pdfFile.exists()) {
            String userEmail = loggedUser.getEmail();
            String subject = "Reporte de Transacciones - Usuario ID: " + userId;
            String message = "Estimado " + loggedUser.getFullName() + ", adjunto encontrará su reporte de transacciones en formato PDF.";

            EmailAttachmentUtil emailUtil = new EmailAttachmentUtil(userEmail, subject, message, pdfFile);
            emailUtil.sendNotification();

            showMessage("Éxito", "PDF Generado y Enviado", "El reporte PDF ha sido generado y enviado a su correo.", Alert.AlertType.INFORMATION);
        } else {
            showMessage("Error", "PDF No Generado", "Ocurrió un error al generar el reporte PDF.", Alert.AlertType.ERROR);
        }
        movementManagementController.generateSerialization();
    }


    private void getCurrentRecords() {
        //logic to get current records
    }

    private void getPreviousRecords() {
        //logic to get previous records
    }

    private void getSubsequentRecords() {
        //logic to get subsequent records
    }
}
