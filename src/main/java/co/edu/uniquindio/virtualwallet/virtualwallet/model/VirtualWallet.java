package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class VirtualWallet {
    private List<Deposit> depositList;
    private List<Transfer> transferList;
    private List<Withdrawal> withdrawalList;
    private List<Category> categoryList;
    private List<Budget> budgetList;
    private List<SavingsAccount> savingsAccountList;
    private List<CheckingAccount> checkingAccountList;
    private List<User> userList;
    private Administrator administrator;

    public VirtualWallet(){
        depositList = new ArrayList<>();
        transferList = new ArrayList<>();
        withdrawalList = new ArrayList<>();
        categoryList = new ArrayList<>();
        budgetList = new ArrayList<>();
        savingsAccountList = new ArrayList<>();
        checkingAccountList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        List<Account> accountList = new ArrayList<>();
        accountList.addAll(savingsAccountList);
        accountList.addAll(checkingAccountList);
        return accountList;
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
        Account account = null;
        boolean flagExist = false;
        account = getAccount(accountNumber);
        if (account == null) {
            throw new AccountException("La cuenta a eliminar no existe");
        } else {
            getAccounts().remove(account);
            flagExist = true;
        }
        return flagExist;
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