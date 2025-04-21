package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;

import java.util.List;

public record CategoryDto (
        String id,
        String name,
        String description,
        List<BudgetDto> budgetList,
        List<Transaction> transactionList
){
}