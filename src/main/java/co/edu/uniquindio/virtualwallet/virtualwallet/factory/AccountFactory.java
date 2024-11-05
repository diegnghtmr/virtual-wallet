package co.edu.uniquindio.virtualwallet.virtualwallet.factory;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.CheckingAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.SavingsAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.I18n;

public class AccountFactory {
    public Account getAccount(String accountType) {
        if (accountType == null) {
            return null;
        }
        if (accountType.equalsIgnoreCase(I18n.get("account.type.savings"))) {
            return new SavingsAccount();
        } else if (accountType.equalsIgnoreCase(I18n.get("account.type.checking"))) {
            return new CheckingAccount();
        }
        return null;
    }
}