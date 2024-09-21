package co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.implementation;

import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.Transaction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Transfer extends Transaction {
    private final double commission;
    private Account sourceAccount;
    private Account receivingAccount;

    public Transfer(){
        super();
        this.commission = 6000;
    }

}
