package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import java.time.LocalDate;

public record TransactionDto(
        String idTransaction,
        LocalDate date,
        double amount,
        String description,
        CategoryDto category,
        AccountDto account,
        String state,
        String transactionType
) {
}
