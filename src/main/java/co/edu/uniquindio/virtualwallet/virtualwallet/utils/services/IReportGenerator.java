package co.edu.uniquindio.virtualwallet.virtualwallet.utils.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public interface IReportGenerator {
    /**
     * Genera un reporte con la lista de transacciones, el saldo total del usuario y el rango de fechas.
     *
     * @param data         Lista de transacciones filtradas.
     * @param totalBalance Saldo total del usuario.
     * @param startDate    Fecha de inicio del rango.
     * @param endDate      Fecha de fin del rango.
     * @return Archivo del reporte generado.
     */
    File generateReport(List<TransactionDto> data, double totalBalance, LocalDate startDate, LocalDate endDate);
}
