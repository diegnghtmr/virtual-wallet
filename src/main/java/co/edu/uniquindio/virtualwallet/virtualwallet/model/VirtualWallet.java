package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class VirtualWallet implements Serializable {
    private String name = "BuckTrack";
    private List<Deposit> depositList = new ArrayList<>();
    private List<Transfer> transferList = new ArrayList<>();
    private List<Withdrawal> withdrawalList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private List<Budget> budgetList = new ArrayList<>();
    private List<SavingsAccount> savingsAccountList = new ArrayList<>();
    private List<CheckingAccount> checkingAccountList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private Administrator administrator;

    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accountList = new ArrayList<>();
        accountList.addAll(savingsAccountList);
        accountList.addAll(checkingAccountList);
        return accountList;
    }

    public void getFileAccounts(List<Account> loadedAccounts) {
        for (Account account : loadedAccounts) {
            if (account instanceof SavingsAccount) {
                savingsAccountList.add((SavingsAccount) account);
            } else if (account instanceof CheckingAccount) {
                checkingAccountList.add((CheckingAccount) account);
            }
        }
    }

    public boolean verifyAccountExistence(String accountNumber) {
        return verifyAccountExistenceRecursive(getAccounts(), accountNumber, 0);
    }

    private boolean verifyAccountExistenceRecursive(List<Account> accounts, String accountNumber, int i) {
        if (i >= accounts.size()) {
            return false;
        }
        if (accounts.get(i).getAccountNumber().equals(accountNumber)) {
            return true;
        }
        return verifyAccountExistenceRecursive(accounts, accountNumber, i + 1);
    }

    public void addAccount(Account account) throws AccountException {
        if (account instanceof SavingsAccount) {
            savingsAccountList.add((SavingsAccount) account);
        } else if (account instanceof CheckingAccount) {
            checkingAccountList.add((CheckingAccount) account);
        } else {
            throw new AccountException("Tipo de cuenta no soportado");
        }
    }

    public boolean removeAccount(String accountNumber) throws AccountException {
        Account account = getAccount(accountNumber);
        if (account == null) {
            throw new AccountException("La cuenta a eliminar no existe");
        } else {
            if (account instanceof SavingsAccount) {
                savingsAccountList.remove(account);
            } else if (account instanceof CheckingAccount) {
                checkingAccountList.remove(account);
            }
            return true;
        }
    }

    private Account getAccount(String accountNumber) {
        return getAccounts().stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public boolean updateAccount(String accountNumber, Account account) throws AccountException {
        Account accountCurrent = getAccount(accountNumber);
        if (accountCurrent == null) {
            throw new AccountException("La cuenta a actualizar no existe");
        } else {
            accountCurrent.setAccountNumber(account.getAccountNumber());
            accountCurrent.setBankName(account.getBankName());
            accountCurrent.setBalance(account.getBalance());
            accountCurrent.setUser(account.getUser());
            accountCurrent.setAssociatedTransfers(account.getAssociatedTransfers());
            accountCurrent.setAssociatedDeposits(account.getAssociatedDeposits());
            accountCurrent.setAssociatedWithdrawals(account.getAssociatedWithdrawals());
            return true;
        }
    }

//    public List<Transaction> getTransactionList() {
//        List<Transaction> transactionList = new ArrayList<>();
//        transactionList.addAll(depositList);
//        transactionList.addAll(withdrawalList);
//        transactionList.addAll(transferList);
//        return transactionList;
//    }
}