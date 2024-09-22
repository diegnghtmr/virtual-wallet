package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

public record SavingsAccountDto(
        String id,
        String bankName,
        String accountNumber,
        UserDto user
) {
}