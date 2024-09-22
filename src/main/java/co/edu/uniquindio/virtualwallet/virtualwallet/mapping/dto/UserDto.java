package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Budget;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        String id,
        String fullName,
        String phoneNumber,
        String email,
        String password,
        LocalDate birthDate,
        LocalDate registrationDate,
        String address,
        double totalBalance

) {
}