package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.UserException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    private List<String> califications = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    // Account Management Methods
    // --------------------------
    
    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accountList = new ArrayList<>();
        accountList.addAll(savingsAccountList);
        accountList.addAll(checkingAccountList);
        return accountList;
    }

    public List<Account> getAccountListByUserId(String id) {
        return getAccounts().stream().filter(account -> account.getUser().getId().equals(id)).toList();
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

    public void addAccountToUser(Account account) {
        for (User u : userList) {
            if (u.getId().equals(account.getUser().getId())) {
                u.getAssociatedAccounts().add(account);
            }
        }
    }

    public void removeAccountFromUser(String accountNumber) {
        for (User u : userList) {
            for (Account a : u.getAssociatedAccounts()) {
                if (a.getAccountNumber().equals(accountNumber)) {
                    u.getAssociatedAccounts().remove(a);
                    break;
                }
            }
        }
    }

    public void updateAccountFromUser(String accountNumber, Account account) {
        for (User u : userList) {
            for (Account a : u.getAssociatedAccounts()) {
                if (a.getAccountNumber().equals(accountNumber)) {
                    a.setAccountNumber(account.getAccountNumber());
                    a.setBankName(account.getBankName());
                    a.setBalance(account.getBalance());
                    a.setUser(account.getUser());
                    a.setAssociatedTransfers(account.getAssociatedTransfers());
                    a.setAssociatedDeposits(account.getAssociatedDeposits());
                    a.setAssociatedWithdrawals(account.getAssociatedWithdrawals());
                    break;
                }
            }
        }
    }

    // Transaction Management Methods
    // ------------------------------

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.addAll(depositList);
        transactionList.addAll(withdrawalList);
        transactionList.addAll(transferList);
        return transactionList;
    }

    public List<Transaction> getTransactionsByUser(String userId) {
        return getTransactions().stream().filter(transaction -> transaction.getAccount().getUser().getId().equals(userId)).toList();
    }

    // Budget Management Methods
    // -------------------------

    public List<Budget> getBudgetsByUser(String userId) {
        return budgetList.stream().filter(budget -> budget.getUser().getId().equals(userId)).toList();
    }


    // User Management Methods
    // -----------------------

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

    public User findUserById(String id) {
        for (User u : userList) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    // Verification Code Methods
    // -------------------------

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

    public void setVerificationCode(String id, String verificationCode) {
        User user = findUserById(id);
        if (user != null) {
            int index = userList.indexOf(user);
            user.setVerificationCode(verificationCode);
        }
    }

    public boolean isVerified(String id) {
        for (User u : userList) {
            if (u.getId().equals(id)) {
                return u.isVerified();
            }
        }
        return false;
    }

    public boolean updateUser(String id, User user) throws UserException {
        User userCurrent = findUserById(id);
        if (userCurrent == null) {
            throw new UserException("El usuario a actualizar no existe");
        } else {
            userCurrent.setFullName(user.getFullName());
            userCurrent.setPhoneNumber(user.getPhoneNumber());
            userCurrent.setEmail(user.getEmail());
            userCurrent.setPassword(user.getPassword());
            userCurrent.setBirthDate(user.getBirthDate());
            userCurrent.setRegistrationDate(user.getRegistrationDate());
            userCurrent.setAddress(user.getAddress());
            userCurrent.setTotalBalance(user.getTotalBalance());
            // Combine existing budget list with new budget list
            if (user.getBudgetList() != null) {
                userCurrent.getBudgetList().addAll(user.getBudgetList());
            }

            // Combine existing associated accounts with new associated accounts
            if (user.getAssociatedAccounts() != null) {
                userCurrent.getAssociatedAccounts().addAll(user.getAssociatedAccounts());
            }

            if (user.getCategoryList() != null) {
                userCurrent.getCategoryList().addAll(user.getCategoryList());
            }

            // Combine existing notifications with new notifications
            if (user.getNotificationUtils() != null) {
                userCurrent.getNotificationUtils().addAll(user.getNotificationUtils());
            }
            return true;
        }
    }

    public List<Deposit> getDepositsByUser(String userId) {
        return depositList.stream().filter(deposit -> deposit.getAccount().getUser().getId().equals(userId)).toList();
    }

    public boolean isTransactionIdExists(String idTransaction) {
        for (Transaction t : getTransactions()) {
            if (t.getIdTransaction().equals(idTransaction)) {
                return true;
            }
        }
        return false;
    }

    public void addDepositToAccount(Deposit deposit) {
        for (Account a : getAccounts()) {
            if (a.getAccountNumber().equals(deposit.getAccount().getAccountNumber())) {
                a.getAssociatedDeposits().add(deposit);
            }
        }
    }

    public List<Category> getCategoryListByUserId(String userId) {
        User user = findUserById(userId);
        if (user != null) {
            return user.getCategoryList();
        }
        return new ArrayList<>();
    }

    public List<Transfer> getTransfersByUser(String userId) {
        return transferList.stream().filter(transfer -> transfer.getAccount().getUser().getId().equals(userId)).toList();
    }

    public List<Withdrawal> getWithdrawalsByUser(String userId) {
        return withdrawalList.stream().filter(withdrawal -> withdrawal.getAccount().getUser().getId().equals(userId)).toList();
    }

    public void addVotedUser(String selectedRating) {
        califications.add(selectedRating);
    }

    public double getAverageRating() {
        if (califications.isEmpty()) {
            return 0;
        }

        int totalStars = 0;
        for (String stars : califications) {
            totalStars += stars.length();
        }

        return (double) totalStars / califications.size();
    }
}