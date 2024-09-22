package co.edu.uniquindio.virtualwallet.virtualwallet.factory;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;

public class TransactionFactory {
    public Transaction getTransaction(String transactionType) {
        if (transactionType == null) {
            return null;
        }
        if (transactionType.equalsIgnoreCase("DEPÓSITO")) {
            return new Deposit();
        } else if (transactionType.equalsIgnoreCase("RETIRO")) {
            return new Withdrawal();
        } else if (transactionType.equalsIgnoreCase("TRANSFERENCIA")) {
            return new Transfer();
        }else {
            return null;
        }
    }
}