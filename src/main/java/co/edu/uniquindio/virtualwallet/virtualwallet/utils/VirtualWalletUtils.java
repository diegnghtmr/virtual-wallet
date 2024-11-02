package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.AccountFactory;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.TransactionFactory;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.CheckingAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.SavingsAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.NotificationUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.enums.NotificationType;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VirtualWalletUtils {
    public static VirtualWallet initializeData() {
        AccountFactory accountFactory = new AccountFactory();
        TransactionFactory transactionFactory = new TransactionFactory();

        // Create example categories
        Category foodCategory = Category.builder()
                .id("001")
                .name("Food")
                .description("Expenses on food")
                .build();

        Category travelCategory = Category.builder()
                .id("002")
                .name("Travel")
                .description("Expenses on travel")
                .build();

        // Create example budgets
        Budget foodBudget = Budget.builder()
                .id("B001")
                .name("Monthly Food Budget")
                .totalAmount(500.0)
                .amountSpent(200.0)
                .category(foodCategory)
                .user(null)
                .build();

        Budget travelBudget = Budget.builder()
                .id("B002")
                .name("Monthly Travel Budget")
                .totalAmount(300.0)
                .amountSpent(100.0)
                .category(travelCategory)
                .user(null)
                .build();

        // Create example accounts
        SavingsAccount savingsAccount = (SavingsAccount) accountFactory.getAccount("AHORROS");
        savingsAccount.setBalance(1500);
        savingsAccount.setBankName("Banco de Occidente");
        savingsAccount.setAccountNumber("1234567890");

        CheckingAccount checkingAccount = (CheckingAccount) accountFactory.getAccount("CORRIENTE");
        checkingAccount.setBalance(1500);
        checkingAccount.setBankName("Bancolombia");
        checkingAccount.setAccountNumber("0987654321");

        // Create example users
        User user1 = User.builder()
                .id("1097032932")
                .fullName("Jorge William Montoya")
                .phoneNumber("3244544139")
                .email("jorgew.montoyat@uqvirtual.edu.co")
                .password("maloh")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.now())
                .budgetList(new ArrayList<>())
                .associatedAccounts(new ArrayList<>())
                .address("Quimbaya, Quindio")
                .totalBalance(3000.0).notificationUtils(new ArrayList<>())
                .build();

        user1.getAssociatedAccounts().add(savingsAccount);
        user1.getAssociatedAccounts().add(checkingAccount);
        user1.getBudgetList().add(foodBudget);
        user1.getBudgetList().add(travelBudget);

        checkingAccount.setUser(user1);
        savingsAccount.setUser(user1);

        // Crear notificaciones de ejemplo para user1
        NotificationUtil notification1 = new NotificationUtil(
                "¡Bienvenido a VirtualWallet!", LocalDate.now(), NotificationType.ADVERTISEMENT);
        NotificationUtil notification2 = new NotificationUtil(
                "Tu cuenta ha sido acreditada con $500", LocalDate.now(), NotificationType.TRANSACTION);

        user1.getNotificationUtils().add(notification1);
        user1.getNotificationUtils().add(notification2);



        // Create example administrator
        Administrator admin = Administrator.builder()
                .id("A001")
                .fullName("admin")
                .phoneNumber("0987654321")
                .email("admin@example.com")
                .password("admin")
                .birthDate(LocalDate.of(1985, 5, 15))
                .registrationDate(LocalDate.now())
                .build();

        // Create example transactions
        Deposit deposit = (Deposit) transactionFactory.getTransaction("DEPÓSITO");
        deposit.setIdTransaction("D001");
        deposit.setAmount(500.0);
        deposit.setDate(LocalDate.now());
        deposit.setDescription("Initial deposit");
        deposit.setCategory(foodCategory);
        deposit.setAccount(savingsAccount);

        Withdrawal withdrawal = (Withdrawal) transactionFactory.getTransaction("RETIRO");
        withdrawal.setIdTransaction("W001");
        withdrawal.setAmount(200.0);
        withdrawal.setDate(LocalDate.now());
        withdrawal.setDescription("ATM withdrawal");
        withdrawal.setCategory(travelCategory);
        withdrawal.setAccount(checkingAccount);

        Transfer transfer = (Transfer) transactionFactory.getTransaction("TRANSFERENCIA");
        transfer.setIdTransaction("T001");
        transfer.setAmount(300.0);
        transfer.setDate(LocalDate.now());
        transfer.setDescription("Transfer to checking account");
        transfer.setCategory(foodCategory);
        transfer.setAccount(savingsAccount);
        transfer.setReceivingAccount(checkingAccount);

        // Create and populate VirtualWallet
        VirtualWallet virtualWallet = new VirtualWallet();
        virtualWallet.getCategoryList().add(foodCategory);
        virtualWallet.getCategoryList().add(travelCategory);
        virtualWallet.getBudgetList().add(foodBudget);
        virtualWallet.getBudgetList().add(travelBudget);
        virtualWallet.getSavingsAccountList().add(savingsAccount);
        virtualWallet.getCheckingAccountList().add(checkingAccount);
        virtualWallet.getUserList().add(user1);
        virtualWallet.setAdministrator(admin);
        virtualWallet.getDepositList().add(deposit);
        virtualWallet.getWithdrawalList().add(withdrawal);
        virtualWallet.getTransferList().add(transfer);

        // New categories
        Category entertainmentCategory = Category.builder()
                .id("003")
                .name("Entertainment")
                .description("Expenses on entertainment")
                .build();

        Category healthCategory = Category.builder()
                .id("004")
                .name("Health")
                .description("Expenses on health")
                .build();

        // New budgets
        Budget entertainmentBudget = Budget.builder()
                .id("B003")
                .name("Monthly Entertainment Budget")
                .totalAmount(400.0)
                .amountSpent(150.0)
                .category(entertainmentCategory)
                .user(null)
                .build();

        Budget healthBudget = Budget.builder()
                .id("B004")
                .name("Monthly Health Budget")
                .totalAmount(200.0)
                .amountSpent(50.0)
                .category(healthCategory)
                .user(null)
                .build();

        // New accounts
        SavingsAccount newSavingsAccount = (SavingsAccount) accountFactory.getAccount("AHORROS");
        newSavingsAccount.setBalance(2000);
        newSavingsAccount.setBankName("Davivienda");
        newSavingsAccount.setAccountNumber("1122334455");

        CheckingAccount newCheckingAccount = (CheckingAccount) accountFactory.getAccount("CORRIENTE");
        newCheckingAccount.setBalance(2500);
        newCheckingAccount.setBankName("BBVA");
        newCheckingAccount.setAccountNumber("5566778899");

        // New user
        User user2 = User.builder()
                .id("1090273361")
                .fullName("Maria Camila Rosero Henao")
                .phoneNumber("3148613948")
                .email("mariac.roseroh@uqvirtual.edu.co")
                .password("soloplacah")
                .birthDate(LocalDate.of(1992, 2, 2))
                .registrationDate(LocalDate.now())
                .budgetList(new ArrayList<>())
                .associatedAccounts(new ArrayList<>())
                .address("Armenia, Quindio")
                .totalBalance(4500.0)
                .notificationUtils(new ArrayList<>())
                .build();

        user2.getAssociatedAccounts().add(newSavingsAccount);
        user2.getAssociatedAccounts().add(newCheckingAccount);
        user2.getBudgetList().add(entertainmentBudget);
        user2.getBudgetList().add(healthBudget);

        newCheckingAccount.setUser(user2);
        newSavingsAccount.setUser(user2);

        // Crear notificaciones de ejemplo para user2
        NotificationUtil notification3 = new NotificationUtil(
                "¡Bienvenido a VirtualWallet!", LocalDate.now(), NotificationType.ADVERTISEMENT);
        NotificationUtil notification4 = new NotificationUtil(
                "Tu cuenta ha sido debitada con $100", LocalDate.now(), NotificationType.TRANSACTION);

        user2.getNotificationUtils().add(notification3);
        user2.getNotificationUtils().add(notification4);

        // New transactions
        Deposit newDeposit = (Deposit) transactionFactory.getTransaction("DEPÓSITO");
        newDeposit.setIdTransaction("D002");
        newDeposit.setAmount(600.0);
        newDeposit.setDate(LocalDate.now());
        newDeposit.setDescription("Salary deposit");
        newDeposit.setCategory(entertainmentCategory);
        newDeposit.setAccount(newSavingsAccount);

        Withdrawal newWithdrawal = (Withdrawal) transactionFactory.getTransaction("RETIRO");
        newWithdrawal.setIdTransaction("W002");
        newWithdrawal.setAmount(100.0);
        newWithdrawal.setDate(LocalDate.now());
        newWithdrawal.setDescription("Pharmacy purchase");
        newWithdrawal.setCategory(healthCategory);
        newWithdrawal.setAccount(newCheckingAccount);

        Transfer newTransfer = (Transfer) transactionFactory.getTransaction("TRANSFERENCIA");
        newTransfer.setIdTransaction("T002");
        newTransfer.setAmount(200.0);
        newTransfer.setDate(LocalDate.now());
        newTransfer.setDescription("Transfer to savings account");
        newTransfer.setCategory(entertainmentCategory);
        newTransfer.setAccount(newCheckingAccount);
        newTransfer.setReceivingAccount(newSavingsAccount);

        // Add new data to VirtualWallet
        virtualWallet.getCategoryList().add(entertainmentCategory);
        virtualWallet.getCategoryList().add(healthCategory);
        virtualWallet.getBudgetList().add(entertainmentBudget);
        virtualWallet.getBudgetList().add(healthBudget);
        virtualWallet.getSavingsAccountList().add(newSavingsAccount);
        virtualWallet.getCheckingAccountList().add(newCheckingAccount);
        virtualWallet.getUserList().add(user2);
        virtualWallet.getDepositList().add(newDeposit);
        virtualWallet.getWithdrawalList().add(newWithdrawal);
        virtualWallet.getTransferList().add(newTransfer);

        return virtualWallet;
    }

    public static List<String> getTransactionTypes() {
        return Arrays.asList("DEPÓSITO", "RETIRO", "TRANSFERENCIA");
    }

    public static List<String> getAccountTypes() {
        return Arrays.asList("AHORROS", "CORRIENTE");
    }
}