package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder

public abstract class Person {
    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDate registrationDate;

    public Person() {

    }
    public Person(String id, String fullName, String phoneNumber, String email, String password, LocalDate birthDate, LocalDate registrationDate){
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

}