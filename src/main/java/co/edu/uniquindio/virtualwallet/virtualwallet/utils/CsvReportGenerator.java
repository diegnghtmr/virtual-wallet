package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CsvReportGenerator implements IReportGenerator {

    private String userId;

    // Formato de fechas
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "CO"));

    public CsvReportGenerator(String userId) {
        this.userId = userId;
    }

    @Override
    public File generateReport(List<TransactionDto> data, double totalBalance, LocalDate startDate, LocalDate endDate) {
        // Crear el archivo en el directorio temporal
        String tempDir = System.getProperty("java.io.tmpdir");
        File file = new File(tempDir, userId + ".csv");

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

            // Variables para calcular ingresos y gastos
            double totalIncome = 0.0;
            double totalExpenses = 0.0;

            // Escribir datos
            for (TransactionDto transaction : data) {
                // Formatear fecha y monto
                String formattedDate = transaction.date().format(DATE_FORMAT);
                String formattedAmount = String.format(Locale.getDefault(), "%.2f", transaction.amount());

                // Internacionalizar el estado
                String statusKey = "transaction.status." + transaction.status();
                String localizedStatus = I18n.get(statusKey);

                String[] rowData = {
                        transaction.idTransaction(),
                        transaction.account().getBankName() + " - " + transaction.account().getAccountNumber(),
                        formattedAmount,
                        formattedDate,
                        localizedStatus, // Usar el estado localizado
                        transaction.transactionType()
                };
                writer.writeNext(rowData);

                // Calcular ingresos y gastos
                String transactionType = transaction.transactionType().toUpperCase();
                if (transactionType.equals("DEPÓSITO")) {
                    totalIncome += transaction.amount();
                } else if (transactionType.equals("TRANSFERENCIA") || transactionType.equals("RETIRO")) {
                    totalExpenses += transaction.amount();
                }
            }

            // Añadir líneas en blanco para separar los datos de los totales
            writer.writeNext(new String[]{}); // Línea en blanco

            // Escribir Resumen de Transacciones
            writer.writeNext(new String[]{"Resumen de Transacciones"});
            writer.writeNext(new String[]{"Fecha de Inicio", startDate.format(DATE_FORMAT)});
            writer.writeNext(new String[]{"Fecha de Fin", endDate.format(DATE_FORMAT)});
            writer.writeNext(new String[]{"Saldo Actual", String.format("%.2f", totalBalance)});
            writer.writeNext(new String[]{"Ingresos Totales", String.format("%.2f", totalIncome)});
            writer.writeNext(new String[]{"Gastos Totales", String.format("%.2f", totalExpenses)});

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Ocurrió un error durante la generación
        }
    }
}
