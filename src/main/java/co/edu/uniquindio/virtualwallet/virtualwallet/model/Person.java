package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public abstract class Person {
    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDate registrationDate;
}