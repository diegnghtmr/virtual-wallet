package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;

import java.time.LocalDate;

public record WithdrawalDto(
        String idTransaction,
        LocalDate date,
        double amount,
        String description,
        Category category,
        double withdrawalLimit,
        double commission,
        Account account
) {
}