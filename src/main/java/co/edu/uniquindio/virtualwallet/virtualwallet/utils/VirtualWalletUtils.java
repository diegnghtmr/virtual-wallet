package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.AccountFactory;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.TransactionFactory;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.enums.TransactionStatus;
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
                .name("Comida")
                .description("Compras de alimentos")
                .transactionList(new ArrayList<>())
                .build();

        Category travelCategory = Category.builder()
                .id("002")
                .name("Viaje")
                .description("Gastos en viajes")
                .transactionList(new ArrayList<>())
                .build();

        // Create example budgets
        Budget foodBudget = Budget.builder()
                .id("B001")
                .name("Mensualidad Comida")
                .totalAmount(500.0)
                .amountSpent(200.0)
                .category(foodCategory)
                .build();

        Budget travelBudget = Budget.builder()
                .id("B002")
                .name("Mensualidad Viaje")
                .totalAmount(300.0)
                .amountSpent(100.0)
                .category(travelCategory)
                .build();

        // Create example accounts
        SavingsAccount savingsAccount = (SavingsAccount) accountFactory.getAccount(I18n.get("account.type.savings"));
        savingsAccount.setBalance(10000);
        savingsAccount.setBankName("Banco de Occidente");
        savingsAccount.setAccountNumber("1234567890");

        CheckingAccount checkingAccount = (CheckingAccount) accountFactory.getAccount(I18n.get("account.type.checking"));
        checkingAccount.setBalance(10000);
        checkingAccount.setBankName("Bancolombia");
        checkingAccount.setAccountNumber("0987654321");

        // Create example users
        User user1 = User.builder()
                .id("1097032932")
                .fullName("Diego Alejandro Flores")
                .phoneNumber("3244544139")
                .email("diegoa.floresq@uqvirtual.edu.co") //jorgew.montoyat@uqvirtual.edu.co
                .password("maloh")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.now())
                .budgetList(new ArrayList<>())
                .associatedAccounts(new ArrayList<>())
                .address("Quimbaya, Quindio")
                .totalBalance(3000.0).notificationUtils(new ArrayList<>())
                .categoryList(new ArrayList<>())
                .isVerified(true)
                .build();

        user1.getAssociatedAccounts().add(savingsAccount);
        user1.getAssociatedAccounts().add(checkingAccount);
        user1.getBudgetList().add(foodBudget);
        user1.getBudgetList().add(travelBudget);

        checkingAccount.setUser(user1);
        savingsAccount.setUser(user1);

        // Crear notificaciones de ejemplo para user1
        NotificationUtil notification1 = new NotificationUtil(
                I18n.getFormatted("notification.message.WELCOME", user1.getFullName()),
                LocalDate.now(),
                NotificationType.INFORMATION
        );
        NotificationUtil notification2 = new NotificationUtil(
                "Deposito de $500 Aceptado. Referencia: T001.",
                LocalDate.now(), NotificationType.TRANSACTION);

        user1.getNotificationUtils().add(notification1);
        user1.getNotificationUtils().add(notification2);

        user1.getCategoryList().add(foodCategory);
        user1.getCategoryList().add(travelCategory);

        user1.getBudgetList().add(travelBudget);
        user1.getBudgetList().add(foodBudget);

        // Create example administrator
        Administrator admin = Administrator.builder()
                .id("A001")
                .fullName("admin")
                .phoneNumber("0987654321")
                .email(PersistenceUtil.ADMIN_EMAIL)
                .password(PersistenceUtil.ADMIN_PASSWORD)
                .birthDate(LocalDate.of(1985, 5, 15))
                .registrationDate(LocalDate.now())
                .build();

        // Create example transactions
        Deposit deposit = (Deposit) transactionFactory.getTransaction(I18n.get("transaction.type.deposit"));
        deposit.setIdTransaction("D001");
        deposit.setAmount(500.0);
        deposit.setDate(LocalDate.now());
        deposit.setDescription("Depósito de salario");
        deposit.setCategory(foodCategory);
        deposit.setAccount(savingsAccount);
        deposit.setStatus(TransactionStatus.ACCEPTED);

        Withdrawal withdrawal = (Withdrawal) transactionFactory.getTransaction(I18n.get("transaction.type.withdrawal"));
        withdrawal.setIdTransaction("W001");
        withdrawal.setAmount(200.0);
        withdrawal.setDate(LocalDate.now());
        withdrawal.setDescription("Retiro para viaje");
        withdrawal.setCategory(travelCategory);
        withdrawal.setAccount(checkingAccount);
        withdrawal.setStatus(TransactionStatus.ACCEPTED);

        Transfer transfer = (Transfer) transactionFactory.getTransaction(I18n.get("transaction.type.transfer"));
        transfer.setIdTransaction("T001");
        transfer.setAmount(300.0);
        transfer.setDate(LocalDate.now());
        transfer.setDescription("Transferencia a cuenta corriente");
        transfer.setCategory(foodCategory);
        transfer.setAccount(savingsAccount);
        transfer.setReceivingAccount(checkingAccount);
        transfer.setStatus(TransactionStatus.PENDING);

        savingsAccount.getAssociatedTransfers().add(transfer);
        savingsAccount.getAssociatedDeposits().add(deposit);
        checkingAccount.getAssociatedWithdrawals().add(withdrawal);

        foodCategory.getTransactionList().add(deposit);
        travelCategory.getTransactionList().add(withdrawal);
        foodCategory.getTransactionList().add(transfer);

        Deposit extraDeposit1 = (Deposit) transactionFactory.getTransaction(I18n.get("transaction.type.deposit"));
        extraDeposit1.setIdTransaction("D003");
        extraDeposit1.setAmount(100.0);
        extraDeposit1.setDate(LocalDate.now());
        extraDeposit1.setDescription("Depósito extra 1");
        extraDeposit1.setCategory(foodCategory);
        extraDeposit1.setAccount(savingsAccount);
        extraDeposit1.setStatus(TransactionStatus.ACCEPTED);

        Deposit extraDeposit2 = (Deposit) transactionFactory.getTransaction(I18n.get("transaction.type.deposit"));
        extraDeposit2.setIdTransaction("D004");
        extraDeposit2.setAmount(150.0);
        extraDeposit2.setDate(LocalDate.now());
        extraDeposit2.setDescription("Depósito extra 2");
        extraDeposit2.setCategory(foodCategory);
        extraDeposit2.setAccount(savingsAccount);
        extraDeposit2.setStatus(TransactionStatus.ACCEPTED);

        // Agregar las transacciones a la categoría
        foodCategory.getTransactionList().add(extraDeposit1);
        foodCategory.getTransactionList().add(extraDeposit2);

        // Agregar las transacciones a la cuenta de ahorros
        savingsAccount.getAssociatedDeposits().add(extraDeposit1);
        savingsAccount.getAssociatedDeposits().add(extraDeposit2);


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
        virtualWallet.getDepositList().add(extraDeposit1); //nuevo depósito
        virtualWallet.getDepositList().add(extraDeposit2); //nuevo depósito

        // New categories
        Category entertainmentCategory = Category.builder()
                .id("003")
                .name("Entretenimiento")
                .description("Gastos en entretenimiento")
                .transactionList(new ArrayList<>())
                .build();

        Category healthCategory = Category.builder()
                .id("004")
                .name("Salud")
                .description("Gastos en salud")
                .transactionList(new ArrayList<>())
                .build();

        // New budgets
        Budget entertainmentBudget = Budget.builder()
                .id("B003")
                .name("Mensualidad Entretenimiento")
                .totalAmount(400.0)
                .amountSpent(150.0)
                .category(entertainmentCategory)
                .build();

        Budget healthBudget = Budget.builder()
                .id("B004")
                .name("Mensualidad Salud")
                .totalAmount(200.0)
                .amountSpent(50.0)
                .category(healthCategory)
                .build();

        // New accounts
        SavingsAccount newSavingsAccount = (SavingsAccount) accountFactory.getAccount(I18n.get("account.type.savings"));
        newSavingsAccount.setBalance(20000);
        newSavingsAccount.setBankName("Davivienda");
        newSavingsAccount.setAccountNumber("1122334455");

        CheckingAccount newCheckingAccount = (CheckingAccount) accountFactory.getAccount(I18n.get("account.type.checking"));
        newCheckingAccount.setBalance(25000);
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
                .categoryList(new ArrayList<>())
                .build();

        user2.getAssociatedAccounts().add(newSavingsAccount);
        user2.getAssociatedAccounts().add(newCheckingAccount);
        user2.getBudgetList().add(entertainmentBudget);
        user2.getBudgetList().add(healthBudget);

        newCheckingAccount.setUser(user2);
        newSavingsAccount.setUser(user2);

        // Crear notificaciones de ejemplo para user2
        NotificationUtil notification3 = new NotificationUtil(
                I18n.getFormatted("notification.message.WELCOME", user2.getFullName()),
                LocalDate.now(),
                NotificationType.INFORMATION
        );
        NotificationUtil notification4 = new NotificationUtil(
                "¡Hola! Tu cuenta ha sido debitada con $100. Referencia: T002. Estado: Aceptado.",
                LocalDate.now(), NotificationType.TRANSACTION);

        user2.getNotificationUtils().add(notification3);
        user2.getNotificationUtils().add(notification4);

        user2.getCategoryList().add(foodCategory);
        user2.getCategoryList().add(travelCategory);

        user2.getBudgetList().add(entertainmentBudget);
        user2.getBudgetList().add(healthBudget);

        // New transactions
        Deposit newDeposit = (Deposit) transactionFactory.getTransaction(I18n.get("transaction.type.deposit"));
        newDeposit.setIdTransaction("D002");
        newDeposit.setAmount(600.0);
        newDeposit.setDate(LocalDate.now());
        newDeposit.setDescription("Depósito de salario");
        newDeposit.setCategory(entertainmentCategory);
        newDeposit.setAccount(newSavingsAccount);
        newDeposit.setStatus(TransactionStatus.ACCEPTED);

        Withdrawal newWithdrawal = (Withdrawal) transactionFactory.getTransaction(I18n.get("transaction.type.withdrawal"));
        newWithdrawal.setIdTransaction("W002");
        newWithdrawal.setAmount(100.0);
        newWithdrawal.setDate(LocalDate.now());
        newWithdrawal.setDescription("Retiro para salud");
        newWithdrawal.setCategory(healthCategory);
        newWithdrawal.setAccount(newCheckingAccount);
        newWithdrawal.setStatus(TransactionStatus.ACCEPTED);


        Transfer newTransfer = (Transfer) transactionFactory.getTransaction(I18n.get("transaction.type.transfer"));
        newTransfer.setIdTransaction("T002");
        newTransfer.setAmount(200.0);
        newTransfer.setDate(LocalDate.now());
        newTransfer.setDescription("Transferencia a cuenta de ahorros");
        newTransfer.setCategory(entertainmentCategory);
        newTransfer.setAccount(newCheckingAccount);
        newTransfer.setReceivingAccount(newSavingsAccount);
        newTransfer.setStatus(TransactionStatus.REJECTED);

        newSavingsAccount.getAssociatedTransfers().add(newTransfer);
        newSavingsAccount.getAssociatedDeposits().add(newDeposit);
        newCheckingAccount.getAssociatedWithdrawals().add(newWithdrawal);

        travelBudget.setUser(user1);
        foodBudget.setUser(user1);
        entertainmentBudget.setUser(user2);
        healthBudget.setUser(user2);

        entertainmentCategory.getTransactionList().add(newDeposit);
        healthCategory.getTransactionList().add(newWithdrawal);
        entertainmentCategory.getTransactionList().add(newTransfer);

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

        List<String> exampleVotes = Arrays.asList("★", "★★", "★★★★", "★★★★★", "★★★★★", "★★★★", "★★★");

        // Add votes to the califications list
        for (String vote : exampleVotes) {
            virtualWallet.addVotedUser(vote);
        }

        return virtualWallet;
    }

    public static List<String> getTransactionTypes() {
        return Arrays.asList(I18n.get("transaction.type.deposit"), I18n.get("transaction.type.withdrawal"), I18n.get("transaction.type.transfer"));
    }

    public static List<String> getAccountTypes() {
        return Arrays.asList(I18n.get("account.type.savings"), I18n.get("account.type.checking"));
    }

    public static List<String> getCalifications() {
        return Arrays.asList("★", "★★", "★★★", "★★★★", "★★★★★");
    }
}