package co.edu.uniquindio.virtualwallet.virtualwallet.factory;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;

public class TransactionFactory {
    public Transaction getTransaction(String transactionType) {
        if (transactionType == null) {
            return null;
        }
        if (transactionType.equalsIgnoreCase(I18n.get("transaction.type.deposit"))) {
            return new Deposit();
        } else if (transactionType.equalsIgnoreCase(I18n.get("transaction.type.withdrawal"))) {
            return new Withdrawal();
        } else if (transactionType.equalsIgnoreCase(I18n.get("transaction.type.transfer"))) {
            return new Transfer();
        }else {
            return null;
        }
    }
}