package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;

import java.time.LocalDate;

public record DepositDto(
        String idTransaction,
        LocalDate date,
        double amount,
        String description,
        CategoryDto category,
        Account account,
        String state
) implements TransactionDto {
    @Override
    public String transactionType() {
        return "Depósito";
    }
}
