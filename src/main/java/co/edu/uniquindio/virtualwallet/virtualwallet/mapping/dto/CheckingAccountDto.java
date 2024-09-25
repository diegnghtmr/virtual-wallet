package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

public record CheckingAccountDto(
        double balance,
        String bankName,
        String accountNumber,
        UserDto user,
        double overdraftLimit
) {
}
