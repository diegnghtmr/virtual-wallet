package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvReportGenerator implements IReportGenerator {

    @Override
    public void generateReport(List<TransactionDto> data) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write CSV header
                writer.append("ID,Account,Amount,Date,Status,Transaction Type\n");
                // Write data rows
                for (TransactionDto transaction : data) {
                    writer.append(transaction.idTransaction()).append(",")
                            .append(transaction.account().getBankName()).append(" - ")
                            .append(transaction.account().getAccountNumber()).append(",")
                            .append(String.valueOf(transaction.amount())).append(",")
                            .append(transaction.date().toString()).append(",")
                            .append(transaction.status()).append(",")
                            .append(transaction.transactionType()).append("\n");
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        }
    }
}
