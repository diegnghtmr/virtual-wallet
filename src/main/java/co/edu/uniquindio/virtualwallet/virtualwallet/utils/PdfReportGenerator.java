package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfReportGenerator implements IReportGenerator {

    @Override
    public void generateReport(List<TransactionDto> data) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    float yPosition = 700;
                    float leading = 15f;

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(50, yPosition);

                    // Write headers
                    contentStream.showText("ID   Account   Amount   Date   Status   Transaction Type");
                    contentStream.newLineAtOffset(0, -leading);

                    // Write data rows
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    for (TransactionDto transaction : data) {
                        String line = String.format("%s   %s - %s   %s   %s   %s   %s",
                                transaction.idTransaction(),
                                transaction.account().getBankName(),
                                transaction.account().getAccountNumber(),
                                transaction.amount(),
                                transaction.date(),
                                transaction.status(),
                                transaction.transactionType());
                        contentStream.showText(line);
                        contentStream.newLineAtOffset(0, -leading);
                    }
                    contentStream.endText();
                }

                document.save(file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        }

    }
}
