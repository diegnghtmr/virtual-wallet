package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Withdrawal extends Transaction {
    private double withdrawalLimit;
    private double commission;
    private Account account;

    public Withdrawal() {
        super();
        this.withdrawalLimit =3000000;
        this.commission = 4000;

    }


}
