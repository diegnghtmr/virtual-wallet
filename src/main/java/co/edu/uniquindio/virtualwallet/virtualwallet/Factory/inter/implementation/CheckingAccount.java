package co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.implementation;

import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(){
        super();
        this.overdraftLimit = 580000;
    }

}
