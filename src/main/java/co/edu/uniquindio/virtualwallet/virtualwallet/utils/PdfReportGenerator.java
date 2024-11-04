package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfReportGenerator implements IReportGenerator {

    private Window ownerWindow;

    // Constructor para recibir el Window o Stage
    public PdfReportGenerator(Window ownerWindow) {
        this.ownerWindow = ownerWindow;
    }

    @Override
    public boolean generateReport(List<TransactionDto> data) {
        // Crear un FileChooser para que el usuario seleccione la ubicación
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte PDF");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(ownerWindow); // Pasamos el Window o Stage actual
        if (file != null) {
            Document document = new Document(PageSize.LETTER);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Agregar Título
                Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
                Paragraph title = new Paragraph("Reporte de Transacciones", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(Chunk.NEWLINE); // Añadir una línea en blanco

                // Crear Tabla
                PdfPTable table = new PdfPTable(6); // 6 columnas
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                // Establecer Anchos de Columna
                float[] columnWidths = {2f, 3f, 2f, 2f, 2f, 3f};
                table.setWidths(columnWidths);

                // Definir Fuente
                Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
                Font dataFont = new Font(Font.HELVETICA, 10);

                // Encabezados de Tabla
                String[] headers = {"ID", "Cuenta", "Monto", "Fecha", "Estado", "Tipo de Transacción"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(Color.LIGHT_GRAY);
                    table.addCell(cell);
                }

                // Filas de la Tabla
                for (TransactionDto transaction : data) {
                    table.addCell(new Phrase(transaction.idTransaction(), dataFont));
                    table.addCell(new Phrase(transaction.account().getBankName() + " - " + transaction.account().getAccountNumber(), dataFont));
                    table.addCell(new Phrase(String.valueOf(transaction.amount()), dataFont));
                    table.addCell(new Phrase(transaction.date().toString(), dataFont));
                    table.addCell(new Phrase(transaction.status(), dataFont));
                    table.addCell(new Phrase(transaction.transactionType(), dataFont));
                }

                document.add(table);

                document.close();
                return true; // Reporte generado exitosamente
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Ocurrió un error durante la generación
                // Manejar excepciones adecuadamente
            }
        } else {
            return false; // El usuario canceló la operación
        }
    }
}
