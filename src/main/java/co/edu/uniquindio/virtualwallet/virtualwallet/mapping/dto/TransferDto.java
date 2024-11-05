package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;

import java.time.LocalDate;

public record TransferDto (
        String idTransaction,
        LocalDate date,
        double amount,
        String description,
        CategoryDto category,
        Account account,
        String status,
        Account receivingAccount,
        double commission
) implements TransactionDto {
    @Override
    public String transactionType() {
        return I18n.get("transaction.type.transfer");
    }
}