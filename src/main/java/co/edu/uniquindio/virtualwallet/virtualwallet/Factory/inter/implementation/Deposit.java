package co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.implementation;

import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.Transaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Deposit extends Transaction {
    private Account account;

    public Deposit() {
        super();

    }

    public Deposit(Account account) {
        super();
        this.account = account;

    }
}
