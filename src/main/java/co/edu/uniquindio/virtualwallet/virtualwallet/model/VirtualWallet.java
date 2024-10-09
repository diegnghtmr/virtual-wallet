package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final long serialVersionUID = 1L;

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

    public Person validateLogin(String email, String password) throws Exception {
        if (administrator != null && administrator.getEmail().equals(email)
                && administrator.getPassword().equals(password)) {
            return administrator;
        }

        for (User user : userList) {
            if (verifyUserExistence(email)) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user;
                }
            } else {
                throw new Exception("El usuario no existe");
            }
        }
        throw new Exception("Identificación o contraseña incorrecta");
    }

    public boolean verifyUserExistence(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void registerUser(User user) {
        userList.add(user);
    }

    public String generateRandomCode() {
        StringBuilder codigoRegistro = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int numero = random.nextInt(8);
            codigoRegistro.append(numero);
        }

        return codigoRegistro.toString();
    }


    public boolean verifyCode(String id, String verificationCode) {

        boolean isCorrect = false;

        for (User u : userList) {
            if (u.getId().equals(id)) {
                 isCorrect = u.getVerificationCode().equals(verificationCode);
                if (isCorrect) {
                    u.setVerified(true);
                }
                break;
            }
        }
        return isCorrect;
    }

    public List<Account> getAccountList(String id) {
        return getAccounts().stream().filter(account -> account.getUser().getId().equals(id)).toList();
    }

    public void setVerificationCode(String id, String verificationCode) {

        User user = findById(id);
        if(user != null){
            int index = userList.indexOf(user);
            user.setVerificationCode(verificationCode);
        }

    }

    public boolean isVerified(String id) {
        for(User u : userList){
            if(u.getId().equals(id)){
                return u.isVerified();
            }
        }
        return false;
    }

    public User findById(String id) {
        for(User u : userList){
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }

    public void addAccountToUser(Account account) {
        for(User u : userList){
            if(u.getId().equals(account.getUser().getId())){
                u.getAssociatedAccounts().add(account);
            }
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