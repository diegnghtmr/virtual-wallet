package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
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
