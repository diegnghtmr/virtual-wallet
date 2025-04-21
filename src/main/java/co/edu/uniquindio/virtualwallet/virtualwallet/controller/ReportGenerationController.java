package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;

import java.util.List;

public class ReportGenerationController extends CoreController {
    public ReportGenerationController() {
        super();
    }

    public void generateSerialization() {
        modelFactory.generateSerialization();
    }

    public List<TransactionDto> getTransactionsByUser(String userId) {
        return modelFactory.getTransactionsByUser(userId);
    }
}
