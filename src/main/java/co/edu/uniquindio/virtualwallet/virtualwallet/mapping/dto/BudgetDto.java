package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

public record BudgetDto(
        String id,
        String name,
        double totalAmount,
        double amountSpent,
        CategoryDto category,
        UserDto user
) {
}