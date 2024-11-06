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

    /**
     * Genera el reporte basado en el tipo y pasa el saldo total del usuario junto con el rango de fechas.
     *
     * @param data         Lista de transacciones filtradas.
     * @param userId       ID del usuario.
     * @param totalBalance Saldo total del usuario.
     * @param startDate    Fecha de inicio del rango.
     * @param endDate      Fecha de fin del rango.
     * @return Archivo del reporte generado.
     */
    public File generateReport(List<TransactionDto> data, String userId, double totalBalance, LocalDate startDate, LocalDate endDate) {
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
        reportFile = reportGenerator.generateReport(data, totalBalance, startDate, endDate);

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
