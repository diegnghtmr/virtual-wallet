package co.edu.uniquindio.virtualwallet.virtualwallet.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter

public abstract class Person {
    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDate registrationDate;

    public Person() {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }
}