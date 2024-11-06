package co.edu.uniquindio.virtualwallet.virtualwallet.utils.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;

import java.io.File;
import java.util.List;

public interface IReportGenerator {
    /**
     * Genera un reporte con la lista de transacciones y el saldo total del usuario.
     *
     * @param data        Lista de transacciones.
     * @param totalBalance Saldo total del usuario.
     * @return Archivo del reporte generado.
     */
    File generateReport(List<TransactionDto> data, double totalBalance);
}
