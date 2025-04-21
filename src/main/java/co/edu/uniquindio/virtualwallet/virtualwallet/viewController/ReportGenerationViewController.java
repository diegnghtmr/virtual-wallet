package co.edu.uniquindio.virtualwallet.virtualwallet.viewController;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.uniquindio.virtualwallet.virtualwallet.controller.ReportGenerationController;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.EmailAttachmentUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.ReportGeneration;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services.IReportGenerationViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class ReportGenerationViewController extends CoreViewController implements IReportGenerationViewController {
    ReportGenerationController reportGenerationController;
    User loggedUser;
    ObservableList<TransactionDto> transactionsListDto = FXCollections.observableArrayList();

    @FXML
    private Button btnClose;

    @FXML
    private Button btnGenerateCSV;

    @FXML
    private Button btnGeneratePDF;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    void onClose(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    public void onGenerateCSV(ActionEvent event) {
        generateCSV();
    }

    @FXML
    public void onGeneratePDF(ActionEvent event) {
        generatePDF();
    }

    @FXML
    void initialize() {
        reportGenerationController = new ReportGenerationController();
        loggedUser = (User) Session.getInstance().getPerson();
        getTransactionList();
    }

    private void getTransactionList() {
        String userId = loggedUser.getId();
        transactionsListDto.addAll(reportGenerationController.getTransactionsByUser(userId));
    }

    public boolean validateData() {
        String message = "";
        if (dpStartDate.getValue() == null) {
            message += "La fecha de inicio es obligatoria.\n";
        }
        if (dpEndDate.getValue() == null) {
            message += "La fecha de fin es obligatoria.\n";
        }
        if (dpStartDate.getValue() != null && dpEndDate.getValue() != null) {
            if (dpStartDate.getValue().isAfter(dpEndDate.getValue())) {
                message += "La fecha de inicio no puede ser posterior a la fecha de fin.\n";
            }
        }
        if (!message.isEmpty()) {
            showMessage("Notificación de validación", "Datos no válidos", message, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void generateCSV() {
        if (!validateData()) {
            return;
        }

        String userId = loggedUser.getId();
        double totalBalance = loggedUser.getTotalBalance(); // Obtener el saldo total del usuario
        LocalDate startDate = dpStartDate.getValue();
        LocalDate endDate = dpEndDate.getValue();

        // Filtrar las transacciones según el rango de fechas
        List<TransactionDto> filteredTransactions = transactionsListDto.stream()
                .filter(tx -> (tx.date().isEqual(startDate) || tx.date().isAfter(startDate)) &&
                        (tx.date().isEqual(endDate) || tx.date().isBefore(endDate)))
                .collect(Collectors.toList());

        // Crear una instancia de ReportGeneration para CSV
        ReportGeneration reportGeneration = new ReportGeneration("CSV");
        reportGeneration.addObserver(loggedUser); // Añadir al usuario como observador

        // Generar el reporte
        File csvFile = reportGeneration.generateReport(filteredTransactions, userId, totalBalance, startDate, endDate);

        if (csvFile != null && csvFile.exists()) {
            String userEmail = loggedUser.getEmail();
            String subject = "Reporte de Transacciones CSV - Usuario ID: " + userId;
            String message = "Estimado " + loggedUser.getFullName() + ", adjunto encontrará su reporte de transacciones en formato CSV correspondiente al período "
                    + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " al "
                    + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";

            EmailAttachmentUtil emailUtil = new EmailAttachmentUtil(userEmail, subject, message, csvFile);
            emailUtil.sendNotification();

            showMessage("Éxito", "CSV Generado y Enviado", "El reporte CSV ha sido generado y enviado a su correo.", Alert.AlertType.INFORMATION);
        } else {
            showMessage("Error", "CSV No Generado", "Ocurrió un error al generar el reporte CSV.", Alert.AlertType.ERROR);
        }
        reportGenerationController.generateSerialization();
    }

    private void generatePDF() {
        if (!validateData()) {
            return;
        }

        String userId = loggedUser.getId();
        double totalBalance = loggedUser.getTotalBalance(); // Obtener el saldo total del usuario
        LocalDate startDate = dpStartDate.getValue();
        LocalDate endDate = dpEndDate.getValue();

        // Filtrar las transacciones según el rango de fechas
        List<TransactionDto> filteredTransactions = transactionsListDto.stream()
                .filter(tx -> (tx.date().isEqual(startDate) || tx.date().isAfter(startDate)) &&
                        (tx.date().isEqual(endDate) || tx.date().isBefore(endDate)))
                .collect(Collectors.toList());

        // Crear una instancia de ReportGeneration para PDF
        ReportGeneration reportGeneration = new ReportGeneration("PDF");
        reportGeneration.addObserver(loggedUser); // Añadir al usuario como observador

        // Generar el reporte
        File pdfFile = reportGeneration.generateReport(filteredTransactions, userId, totalBalance, startDate, endDate);

        if (pdfFile != null && pdfFile.exists()) {
            String userEmail = loggedUser.getEmail();
            String subject = "Reporte de Transacciones - Usuario ID: " + userId;
            String message = "Estimado " + loggedUser.getFullName() + ", adjunto encontrará su reporte de transacciones en formato PDF correspondiente al período "
                    + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " al "
                    + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";

            EmailAttachmentUtil emailUtil = new EmailAttachmentUtil(userEmail, subject, message, pdfFile);
            emailUtil.sendNotification();

            showMessage("Éxito", "PDF Generado y Enviado", "El reporte PDF ha sido generado y enviado a su correo.", Alert.AlertType.INFORMATION);
        } else {
            showMessage("Error", "PDF No Generado", "Ocurrió un error al generar el reporte PDF.", Alert.AlertType.ERROR);
        }
        reportGenerationController.generateSerialization();
    }
}
