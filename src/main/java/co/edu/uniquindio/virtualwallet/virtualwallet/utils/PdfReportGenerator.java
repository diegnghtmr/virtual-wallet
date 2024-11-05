package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PdfReportGenerator implements IReportGenerator {

    private Window ownerWindow;

    // Colores corporativos
    private static final Color PRIMARY_COLOR = new Color(8, 14, 72);    // #080e48
    private static final Color SECONDARY_COLOR = new Color(25, 113, 194); // #1971c2

    // Formato de fechas y montos
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

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
            Document document = new Document(PageSize.LETTER, 50, 50, 120, 70); // Ajustamos los márgenes para el encabezado y pie de página
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
                // Agregar evento para encabezado y pie de página
                HeaderFooterPageEvent event = new HeaderFooterPageEvent();
                writer.setPageEvent(event);

                document.open();

                // Agregar espacio después del encabezado
                document.add(Chunk.NEWLINE);

                // Añadir título "Detalle de Movimientos" centrado y más grande
                Font sectionTitleFont = new Font(Font.HELVETICA, 18, Font.BOLD, PRIMARY_COLOR); // Aumentamos el tamaño de 14 a 18
                Paragraph sectionTitle = new Paragraph("Detalle de Movimientos", sectionTitleFont);
                sectionTitle.setAlignment(Element.ALIGN_CENTER); // Centramos el título
                document.add(sectionTitle);

                document.add(Chunk.NEWLINE); // Añadir una línea en blanco

                // Crear Tabla
                PdfPTable table = new PdfPTable(6); // 6 columnas
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(20f);

                // Establecer Anchos de Columna
                float[] columnWidths = {2f, 3f, 2f, 2f, 2f, 3f};
                table.setWidths(columnWidths);

                // Definir Fuentes
                Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
                Font dataFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);

                // Encabezados de Tabla
                String[] headers = {"ID", "Cuenta", "Monto", "Fecha", "Estado", "Tipo de Transacción"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(PRIMARY_COLOR);
                    cell.setPadding(5);
                    table.addCell(cell);
                }

                // Filas de la Tabla
                boolean alternate = false;
                for (TransactionDto transaction : data) {
                    // Formatear datos
                    String formattedDate = transaction.date().format(DATE_FORMAT);
                    String formattedAmount = CURRENCY_FORMAT.format(transaction.amount());

                    // Definir color de fondo alternado
                    Color rowColor = alternate ? Color.WHITE : new Color(240, 240, 240);
                    alternate = !alternate;

                    // ID
                    PdfPCell idCell = new PdfPCell(new Phrase(transaction.idTransaction(), dataFont));
                    idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    idCell.setBackgroundColor(rowColor);
                    idCell.setPadding(5);
                    table.addCell(idCell);

                    // Cuenta
                    PdfPCell accountCell = new PdfPCell(new Phrase(transaction.account().getBankName() + " - " + transaction.account().getAccountNumber(), dataFont));
                    accountCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    accountCell.setBackgroundColor(rowColor);
                    accountCell.setPadding(5);
                    table.addCell(accountCell);

                    // Monto
                    PdfPCell amountCell = new PdfPCell(new Phrase(formattedAmount, dataFont));
                    amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    amountCell.setBackgroundColor(rowColor);
                    amountCell.setPadding(5);
                    table.addCell(amountCell);

                    // Fecha
                    PdfPCell dateCell = new PdfPCell(new Phrase(formattedDate, dataFont));
                    dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    dateCell.setBackgroundColor(rowColor);
                    dateCell.setPadding(5);
                    table.addCell(dateCell);

                    // Estado
                    PdfPCell statusCell = new PdfPCell(new Phrase(transaction.status(), dataFont));
                    statusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    statusCell.setBackgroundColor(rowColor);
                    statusCell.setPadding(5);
                    table.addCell(statusCell);

                    // Tipo de Transacción
                    PdfPCell typeCell = new PdfPCell(new Phrase(transaction.transactionType(), dataFont));
                    typeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    typeCell.setBackgroundColor(rowColor);
                    typeCell.setPadding(5);
                    table.addCell(typeCell);
                }

                document.add(table);

                // Añadir Resumen de Transacciones
                Font summaryFont = new Font(Font.HELVETICA, 12, Font.BOLD, SECONDARY_COLOR);
                Paragraph summary = new Paragraph("Resumen de Transacciones", summaryFont);
                summary.setAlignment(Element.ALIGN_LEFT);
                document.add(summary);

                // Calcular el total de montos
                double totalAmount = data.stream().mapToDouble(TransactionDto::amount).sum();
                String formattedTotalAmount = CURRENCY_FORMAT.format(totalAmount);

                Paragraph total = new Paragraph("Total: " + formattedTotalAmount, dataFont);
                total.setAlignment(Element.ALIGN_LEFT);
                document.add(total);

                // Añadir espacio antes de la firma
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);

                // Añadir la firma
                Font signatureFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
                Paragraph signatureLine = new Paragraph("______________________________", signatureFont);
                signatureLine.setAlignment(Element.ALIGN_CENTER);
                document.add(signatureLine);

                Paragraph signatureName = new Paragraph("Jhon Oscar Zalazar", signatureFont);
                signatureName.setAlignment(Element.ALIGN_CENTER);
                document.add(signatureName);

                Paragraph signatureTitle = new Paragraph("Gerente de BuckTrack", signatureFont);
                signatureTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(signatureTitle);

                document.close();
                return true; // Reporte generado exitosamente
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Ocurrió un error durante la generación
            }
        } else {
            return false; // El usuario canceló la operación
        }
    }

    // Clase interna para manejar el encabezado y pie de página
    class HeaderFooterPageEvent extends PdfPageEventHelper {
        private Image logo;
        private Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD, PRIMARY_COLOR);
        private Font footerFont = new Font(Font.HELVETICA, 9, Font.NORMAL, Color.GRAY);

        public HeaderFooterPageEvent() {
            try {
                // Cargar el logo
                URL imageUrl = getClass().getResource("/img/logo.png");
                if (imageUrl != null) {
                    logo = Image.getInstance(imageUrl);
                    logo.scaleAbsolute(50, 50);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Manejar el caso en que no se pueda cargar el logo
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Rectangle pageSize = document.getPageSize();

            // Encabezado
            try {
                if (logo != null) {
                    // Posicionar el logo en el encabezado
                    logo.setAbsolutePosition(document.leftMargin(), pageSize.getHeight() - 70);
                    cb.addImage(logo);
                }

                // Añadir el nombre de la institución en el encabezado
                ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Phrase("BuckTrack", headerFont),
                        document.leftMargin() + 60, pageSize.getHeight() - 50, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Pie de página
            try {
                // Datos de contacto
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase("Contacto: virtualwalletuq@gmail.com", footerFont),
                        (pageSize.getWidth()) / 2, document.bottomMargin() - 20, 0);

                // Disclaimers legales
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase("Este documento es confidencial y para uso exclusivo del destinatario.", footerFont),
                        (pageSize.getWidth()) / 2, document.bottomMargin() - 35, 0);

                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase("BuckTrack no se responsabiliza por el uso indebido de esta información.", footerFont),
                        (pageSize.getWidth()) / 2, document.bottomMargin() - 50, 0);

                // Número de página
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase("Página " + writer.getPageNumber(), footerFont),
                        (pageSize.getWidth()) / 2, document.bottomMargin() - 65, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
