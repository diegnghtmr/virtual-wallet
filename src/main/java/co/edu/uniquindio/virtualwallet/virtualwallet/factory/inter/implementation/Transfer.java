package co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
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
