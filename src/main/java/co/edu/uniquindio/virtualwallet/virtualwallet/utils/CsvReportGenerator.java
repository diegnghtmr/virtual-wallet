package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import com.opencsv.CSVWriter;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                 CSVWriter writer = new CSVWriter(osw,
                         CSVWriter.DEFAULT_SEPARATOR,
                         CSVWriter.NO_QUOTE_CHARACTER,
                         CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                         CSVWriter.DEFAULT_LINE_END)) {

                // Escribir BOM para UTF-8
                osw.write('\uFEFF');

                // Escribir encabezados
                String[] headers = {"ID", "Cuenta", "Monto", "Fecha", "Estado", "Tipo de Transacción"};
                writer.writeNext(headers);

                // Escribir datos
                for (TransactionDto transaction : data) {
                    String[] rowData = {
                            transaction.idTransaction(),
                            transaction.account().getBankName() + " - " + transaction.account().getAccountNumber(),
                            String.valueOf(transaction.amount()),
                            transaction.date().toString(),
                            transaction.status(),
                            transaction.transactionType()
                    };
                    writer.writeNext(rowData);
                }

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
