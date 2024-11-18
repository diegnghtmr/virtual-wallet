package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;

import java.time.LocalDate;

public record DepositDto(
        String idTransaction,
        LocalDate date,
        double amount,
        String description,
        CategoryDto category,
        Account account,
        String status
) implements TransactionDto {
    @Override
    public String transactionType() {
        return I18n.get("transaction.type.deposit");
    }
    public String statusType() {
        return I18n.get("transaction.status." + status);
    }
}
