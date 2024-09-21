package co.edu.uniquindio.virtualwallet.virtualwallet.Factory;

import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.Transaction;

public class TransactionFactory {
    public Transaction getTransaction(String transactionType) {
        if (transactionType == null) {
            return null;
        }
        if (transactionType.equalsIgnoreCase("DEPOSIT")) {
            return new Deposit();
        } else if (transactionType.equalsIgnoreCase("WITHDRAWAL")) {
            return new Withdrawal();
        } else if (transactionType.equalsIgnoreCase("TRANSFER")) {
            return new Transfer();
        }

        return null;
    }
}