package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvReportGenerator implements IReportGenerator {

    private Window ownerWindow;

    public CsvReportGenerator(Window ownerWindow) {
        this.ownerWindow = ownerWindow;
    }

    @Override
    public boolean generateReport(List<TransactionDto> data) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte CSV");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(ownerWindow);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Escribir encabezados y datos
                writer.append("ID,Cuenta,Monto,Fecha,Estado,Tipo de Transacción\n");
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
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false; // Ocurrió un error durante la generación
                // Manejar excepciones adecuadamente
            }
        } else {
            return false; // El usuario canceló la operación
        }
    }
}