package co.edu.uniquindio.virtualwallet.virtualwallet.factory;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.CheckingAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.SavingsAccount;

public class AccountFactory {
    public Account getAccount(String accountType) {
        if (accountType == null) {
            return null;
        }
        if (accountType.equalsIgnoreCase("AHORROS")) {
            return new SavingsAccount();
        } else if (accountType.equalsIgnoreCase("CORRIENTE")) {
            return new CheckingAccount();
        }
        return null;
    }
}