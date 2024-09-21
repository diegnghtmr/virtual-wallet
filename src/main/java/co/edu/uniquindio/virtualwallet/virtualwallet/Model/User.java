package co.edu.uniquindio.virtualwallet.virtualwallet.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder

public class User extends Person{
    private String address;
    private double totalBalance;

    public User(String address, double totalBalance) {
        super();
        this.address = address;
        this.totalBalance = totalBalance;
    }
}