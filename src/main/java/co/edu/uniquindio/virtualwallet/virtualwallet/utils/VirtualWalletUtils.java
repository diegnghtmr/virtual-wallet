package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.AccountFactory;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.TransactionFactory;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.CheckingAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Deposit;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.SavingsAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Transfer;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;
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
                .id("123456789")
                .fullName("John Doe")
                .phoneNumber("3244544139")
                .email("john.doe@example.com")
                .password("p1ssw0rd")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.now())
                .budgetList(new ArrayList<>())
                .associatedAccounts(new ArrayList<>())
                .address("123 Main St")
                .totalBalance(3000.0)
                .build();

        user1.getAssociatedAccounts().add(savingsAccount);
        user1.getAssociatedAccounts().add(checkingAccount);
        user1.getBudgetList().add(foodBudget);
        user1.getBudgetList().add(travelBudget);

        checkingAccount.setUser(user1);
        savingsAccount.setUser(user1);

        // Create example administrator
        Administrator admin = Administrator.builder()
                .id("A001")
                .fullName("Admin User")
                .phoneNumber("0987654321")
                .email("admin@example.com")
                .password("adminpassword")
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

        return virtualWallet;
    }

    public static List<String> getTransactionTypes() {
        return Arrays.asList("DEPÓSITO", "RETIRO", "TRANSFERENCIA");
    }

    public static List<String> getAccountTypes() {
        return Arrays.asList("AHORROS", "CORRIENTE");
    }
}