package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.services.Observable;
import co.edu.uniquindio.virtualwallet.virtualwallet.services.Observer;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.services.IReportGenerator;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportGeneration implements Observable {

    private List<Observer> observers = new ArrayList<>();
    private String reportType; // "PDF" o "CSV"
    private File reportFile;

    public ReportGeneration(String reportType) {
        this.reportType = reportType;
    }

    public File generateReport(List<TransactionDto> data, String userId) {
        // Generar el reporte basado en el tipo
        IReportGenerator reportGenerator;

        if (reportType.equalsIgnoreCase("PDF")) {
            reportGenerator = new PdfReportGenerator(userId);
        } else if (reportType.equalsIgnoreCase("CSV")) {
            reportGenerator = new CsvReportGenerator(userId);
        } else {
            throw new IllegalArgumentException("Tipo de reporte inválido: " + reportType);
        }

        // Generar el reporte y obtener el archivo
        reportFile = reportGenerator.generateReport(data);

        if (reportFile != null && reportFile.exists()) {
            // Notificar a los observadores que el reporte ha sido generado
            notifyObservers(new NotificationUtil(
                    "Reporte " + reportType.toUpperCase() + " generado exitosamente.",
                    LocalDate.now(),
                    NotificationType.INFORMATION
            ));
        }

        return reportFile;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(NotificationUtil notificationUtil) {
        for (Observer observer : observers) {
            observer.update(notificationUtil);
        }
    }
}
