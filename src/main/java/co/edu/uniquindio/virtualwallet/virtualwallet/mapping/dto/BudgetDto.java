package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;

public record BudgetDto(
        String id,
        String name,
        double totalAmount,
        double amountSpent,
        CategoryDto category,
        User user
) {
}