package co.edu.uniquindio.virtualwallet.virtualwallet.utils.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import java.util.List;

public interface IReportGenerator {
    boolean generateReport(List<TransactionDto> data);
}
