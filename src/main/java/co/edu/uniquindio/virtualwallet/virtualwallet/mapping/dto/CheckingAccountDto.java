package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

public record CheckingAccountDto(
        String id,
        String bankName,
        String accountNumber,
        UserDto user,
        double overdraftLimit
) {
}
