package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Person implements Serializable {
   @EqualsAndHashCode.Include
    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private static final long serialVersionUID = 1L;
}