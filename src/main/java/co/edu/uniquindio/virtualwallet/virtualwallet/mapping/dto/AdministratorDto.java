package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import java.time.LocalDate;

public record AdministratorDto(
        String id,
        String fullName,
        String phoneNumber,
        String email,
        String password,
        LocalDate birthDate,
        LocalDate registrationDate
) {
}