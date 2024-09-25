package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

public record SavingsAccountDto(
        double balance,
        String bankName,
        String accountNumber,
        UserDto user
) {
}