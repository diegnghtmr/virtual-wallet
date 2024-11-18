package co.edu.uniquindio.virtualwallet.virtualwallet.model;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.UserException;
import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.WithdrawalException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

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

        double amount = deposit.getAmount();
        Account account = deposit.getAccount();

        account.setBalance(account.getBalance() + amount);

        String idUser = account.getUser().getId();
        User user = findUserById(idUser);
        updateUserBalance(user);

        account.getAssociatedDeposits().add(deposit);

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

    public String getUserRiskProfile(String id) {
        // Recuperar el usuario
        User user = findUserById(id);
        if (user == null) {
            return "Desconocido";
        }

        double totalBalance = user.getTotalBalance();
        double averageMonthlyIncome = calculateAverageMonthlyIncome(user);
        int numberOfInvestments = user.getAssociatedAccounts().size(); // Asumiendo que cada cuenta asociada es una inversión
        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();

        // Calcular un puntaje de riesgo basado en los factores
        int riskScore = 0;

        // Evaluación del saldo total
        if (totalBalance < 10000) {
            riskScore += 1;
        } else if (totalBalance < 50000) {
            riskScore += 2;
        } else {
            riskScore += 3;
        }

        // Evaluación de ingresos mensuales
        if (averageMonthlyIncome < 3000) {
            riskScore += 1;
        } else if (averageMonthlyIncome < 7000) {
            riskScore += 2;
        } else {
            riskScore += 3;
        }

        // Evaluación del historial de inversiones
        if (numberOfInvestments < 2) {
            riskScore += 1;
        } else if (numberOfInvestments < 5) {
            riskScore += 2;
        } else {
            riskScore += 3;
        }

        // Evaluación de la edad
        if (age < 30) {
            riskScore += 3;
        } else if (age < 50) {
            riskScore += 2;
        } else {
            riskScore += 1;
        }

        // Determinar el perfil de riesgo basado en el puntaje total
        if (riskScore <= 6) {
            return "Conservador";
        } else if (riskScore <= 10) {
            return "Moderado";
        } else {
            return "Agresivo";
        }
    }

    private double calculateAverageMonthlyIncome(User user) {
        List<Deposit> deposits = getDepositsByUser(user.getId());
        double totalIncome = 0.0;
        for (Deposit deposit : deposits) {
            totalIncome += deposit.getAmount();
        }
        return totalIncome / deposits.size();
    }

    public void getTransferToAccount(Transfer transfer) throws IllegalArgumentException {
        Account sourceAccount = transfer.getAccount();
        Account receivingAccount = transfer.getReceivingAccount();

        double totalAmount = transfer.getAmount() + transfer.getCommission();

        if (sourceAccount.getBalance() < totalAmount) {
            throw new IllegalArgumentException("Fondos insuficientes en la cuenta de origen");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - totalAmount);
        receivingAccount.setBalance(receivingAccount.getBalance() + transfer.getAmount());
        sourceAccount.getAssociatedTransfers().add(transfer);
        receivingAccount.getAssociatedTransfers().add(transfer);

        String idUserSourceAccount = sourceAccount.getUser().getId();
        String idUserReceivingAccount = receivingAccount.getUser().getId();
        User user1 = findUserById(idUserSourceAccount);
        User user2 = findUserById(idUserReceivingAccount);
        updateUserBalance(user1);
        updateUserBalance(user2);


    }

    public double getAverageUserBalance() {
        double totalBalance = 0;
        int userCount = userList.size();

        for (User user : userList) {
            totalBalance += user.getTotalBalance(); // Obtiene el saldo total de cada usuario
        }

        if (userCount > 0) {
            return totalBalance / userCount;
        } else {
            return 0; // Evita la división por cero si no hay usuarios
        }
    }

    public Map<String, Double> calculateTotalExpenses() {
        Map<String, Double> commonExpenses = new HashMap<>();

        for (User user : userList) {
            if (user.getCategoryList() != null) {
                for (Category category : user.getCategoryList()) {
                    double totalExpenses = calculateTotalExpensesCommon(category);
                    commonExpenses.put(category.getName(), commonExpenses.getOrDefault(category.getName(), 0.0) + totalExpenses);
                }
            } else {
                System.out.println("El usuario " + user.getFullName() + " tiene la lista de categorías vacía");
            }
        }
        return commonExpenses;
    }


    public double calculateTotalExpensesCommon(Category category) {
        double totalExpenses = 0;

        // Validar si la lista de transacciones está vacía
        if (category.getTransactionList() == null || category.getTransactionList().isEmpty()) {
            System.out.println("Depuración: La categoría con nombre " + category.getName() + " tiene la lista de transacciones vacía.");
        } else {
            // Si la lista no está vacía, se calcula el total
            for (Transaction transaction : category.getTransactionList()) {
                totalExpenses += transaction.getAmount();
            }
        }

        return totalExpenses;
    }

    public List<User> getUsersWithMostTransactions() {
        List<User> sortedUsers = new ArrayList<>(getUserList());

        sortedUsers.sort((user1, user2) -> {
            int count1 = countTransaction(user1);
            int count2 = countTransaction(user2);
            return Integer.compare(count2, count1);
        });
        return sortedUsers;
    }

    public int countTransaction(User user) {
        int totalTransaction = 0;

        for (Account account : user.getAssociatedAccounts()) {
            // Verificar que cada lista esté inicializada antes de acceder a su tamaño
            if (account.getAssociatedTransfers() != null) {
                totalTransaction += account.getAssociatedTransfers().size();
            }
            if (account.getAssociatedDeposits() != null) {
                totalTransaction += account.getAssociatedDeposits().size();
            }
            if (account.getAssociatedWithdrawals() != null) {
                totalTransaction += account.getAssociatedWithdrawals().size();
            }
        }

        return totalTransaction;
    }

    public boolean verifyUserExists(String id) {
        return verifyUserExistsRecursive(getUserList(), id, 0);

    }

    private boolean verifyUserExistsRecursive(List<User> userList, String id, int i) {
        if (i >= userList.size()) {
            return false;
        }
        if (userList.get(i).getId().equals(id)) {
            return true;
        }
        return verifyUserExistsRecursive(userList, id, i + 1);

    }

    public void addUser(User user) throws UserException {
        if (user == null) {
            throw new UserException("El usuario no puede ser nulo");
        }
        getUserList().add(user);
    }

    public boolean removeUser(String id) throws UserException {
        Iterator<User> iterator = getUserList().iterator(); // Usar un iterator para evitar ConcurrentModificationException
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId().equals(id)) {
                iterator.remove(); // Remover usando el iterator
                return true; // Retorna true si se encontró y eliminó el usuario
            }
        }
        // Si llegamos aquí, el usuario no fue encontrado
        throw new UserException("Usuario con ID " + id + " no encontrado."); // Lanza excepción si no se encuentra
    }

    public void getWithdrawalToAccount(Withdrawal withdrawal) throws WithdrawalException {
        Account account = withdrawal.getAccount();

        double totalAmount = withdrawal.getAmount() + withdrawal.getCommission();

        if (account.getBalance() < totalAmount) {
            throw new WithdrawalException("Fondos insuficientes");

        }

        account.setBalance(account.getBalance() - totalAmount);
        account.getAssociatedWithdrawals().add(withdrawal);
        String idUser = account.getUser().getId();
        User user = findUserById(idUser);
        updateUserBalance(user);

    }

    private void updateUserBalance(User user) {
        double totalBalance = 0;

        for (Account account : user.getAssociatedAccounts()) {
            totalBalance += account.getBalance();
        }
        user.setTotalBalance(totalBalance);
    }


    public void getBudgetToUser(Budget budget) {
        User user = budget.getUser();
        user.getBudgetList().add(budget);
    }

    public boolean removeBudget(String id) {
        return budgetList.removeIf(budget -> budget.getId().equals(id));
    }

    public void removeBudgetFromUser(String userId, String budgetId) {
        User user = findUserById(userId);
        if (user != null) {
            user.getBudgetList().removeIf(budget -> budget.getId().equals(budgetId));
        }
    }

    public boolean updateBudget(String id, Budget updatedBudget) {
        for (int i = 0; i < budgetList.size(); i++) {
            Budget budget = budgetList.get(i);
            if (budget.getId().equals(id)) {
                budgetList.set(i, updatedBudget);
                return true;
            }
        }
        return false;
    }

    public boolean isBudgetIdExists(String id) {
        for (Budget budget : budgetList) {
            if (budget.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void addTransferToAccount(Transfer transfer) {
        Account receivingAccount = transfer.getReceivingAccount();
        String idReceivingAccount = receivingAccount.getAccountNumber();
        Account sourceAccount = transfer.getAccount();

        if (validateIdRecivingAccount(idReceivingAccount)) {
            double totalAmount = transfer.getAmount() + transfer.getCommission();

            if (sourceAccount.getBalance() < totalAmount) {
                throw new IllegalArgumentException("Fondos insuficientes en la cuenta de origen");
            }

            sourceAccount.setBalance(sourceAccount.getBalance() - totalAmount);
            receivingAccount.setBalance(receivingAccount.getBalance() + transfer.getAmount());
            sourceAccount.getAssociatedTransfers().add(transfer);
            receivingAccount.getAssociatedTransfers().add(transfer);

            String idUserSourceAccount = sourceAccount.getUser().getId();
            String idUserReceivingAccount = receivingAccount.getUser().getId();
            User user1 = findUserById(idUserSourceAccount);
            User user2 = findUserById(idUserReceivingAccount);
            updateUserBalance(user1);
            updateUserBalance(user2);

        } else {
            transfer.setStatus(TransactionStatus.REJECTED);
        }

    }

    private boolean validateIdRecivingAccount(String idReceivingAccount) {
        for (Account receivingAccount : getAccounts()) {
            if (receivingAccount.getAccountNumber().equals(idReceivingAccount)) {
                return true;
            }
        }
        return false;
    }

    public void addCategoryToUser(String idUser, Category category) {
        User user = findUserById(idUser);
        if (user != null) {
            user.getCategoryList().add(category);
            System.out.println("se agrego la categoria");
        }
    }

    public boolean removeCategory(String idCategory) {
        return categoryList.removeIf(category -> category.getId().equals(idCategory));
    }

    public void removeCategoryFromUser(String idUser, String idCategory) {
        User user = findUserById(idUser);
        if (user != null) {
            user.getCategoryList().removeIf(category -> category.getId().equals(idCategory));
            System.out.println("se elimino la categoria");
        }
    }

    public boolean updateCategory(String idUser, String id, Category category) {
        User user = findUserById(idUser);
        if (user != null) {
            for (int i = 0; i < user.getCategoryList().size(); i++) {
                Category categoryCurrent = user.getCategoryList().get(i);
                if (categoryCurrent.getId().equals(id)) {
                    user.getCategoryList().set(i, category);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCategoryExists(String id) {
        for (Category category : categoryList) {
            if (category.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Account isAccountId(String id) {
        for (Account receivingAccount : getAccounts()) {
            if (receivingAccount.getAccountNumber().equals(id)) {
                return receivingAccount;
            }
        }
        return null;
    }
}