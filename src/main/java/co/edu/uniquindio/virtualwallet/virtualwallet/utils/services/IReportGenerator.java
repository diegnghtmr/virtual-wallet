package co.edu.uniquindio.virtualwallet.virtualwallet.utils.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;

import java.io.File;
import java.util.List;

public interface IReportGenerator {
    File generateReport(List<TransactionDto> data);
}
