package co.edu.uniquindio.virtualwallet.virtualwallet.model;

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

    }
    public Person(String id, String fullName, String phoneNumber, String email, String password, String birthDate, String registrationDate){
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

}