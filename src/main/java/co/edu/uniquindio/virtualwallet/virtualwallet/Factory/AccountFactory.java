package co.edu.uniquindio.virtualwallet.virtualwallet.Factory;

import co.edu.uniquindio.virtualwallet.virtualwallet.Factory.inter.Account;

public class AccountFactory {
    public Account getAccount(String accountType) {
        if (accountType == null) {
            return null;
        }
        if (accountType.equalsIgnoreCase("SAVINGS")) {
            return new SavingsAccount();
        } else if (accountType.equalsIgnoreCase("CHECKING")) {
            return new CheckingAccount();
        }
        return null;
    }
}