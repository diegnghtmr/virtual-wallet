package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountNumber")

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(){
        super();
        this.overdraftLimit = 580000;
    }
}