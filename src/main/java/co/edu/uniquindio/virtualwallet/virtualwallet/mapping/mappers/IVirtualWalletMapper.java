package co.edu.uniquindio.virtualwallet.virtualwallet.mapping.mappers;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Budget;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IVirtualWalletMapper {
    IVirtualWalletMapper INSTANCE = Mappers.getMapper(IVirtualWalletMapper.class);

    // Transaction mapping methods
    // Transfer mapping methods
    @Named("transferToTransferDto")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "commission", target = "commission")
    @Mapping(source = "account", target = "account")
    @Mapping(source = "receivingAccount", target = "receivingAccount")
    TransferDto transferToTransferDto(Transfer transfer);

    @Named("transferDtoToTransfer")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "commission", target = "commission")
    @Mapping(source = "account", target = "account")
    @Mapping(source = "receivingAccount", target = "receivingAccount")
    Transfer transferDtoToTransfer(TransferDto transferDto);

    // Deposit mapping methods
    @Named("depositToDepositDto")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "account", target = "account")
    DepositDto depositToDepositDto(Deposit deposit);

    @Named("depositDtoToDeposit")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "account", target = "account")
    Deposit depositDtoToDeposit(DepositDto depositDto);

    // Withdrawal mapping methods
    @Named("withdrawalToWithdrawalDto")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "withdrawalLimit", target = "withdrawalLimit")
    @Mapping(source = "commission", target = "commission")
    @Mapping(source = "account", target = "account")
    WithdrawalDto withdrawalToWithdrawalDto(Withdrawal withdrawal);

    @Named("withdrawalDtoToWithdrawal")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "withdrawalLimit", target = "withdrawalLimit")
    @Mapping(source = "commission", target = "commission")
    @Mapping(source = "account", target = "account")
    Withdrawal withdrawalDtoToWithdrawal(WithdrawalDto withdrawalDto);

    // SavingsAccount mapping methods
    @Named("savingsAccountToSavingsAccountDto")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "user", target = "user")
    SavingsAccountDto savingsAccountToSavingsAccountDto(SavingsAccount savingsAccount);

    @Named("savingsAccountDtoToSavingsAccount")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "user", target = "user")
    SavingsAccount savingsAccountDtoToSavingsAccount(SavingsAccountDto savingsAccountDto);

    // CheckingAccount mapping methods
    @Named("checkingAccountToCheckingAccountDto")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "overdraftLimit", target = "overdraftLimit")
    CheckingAccountDto checkingAccountToCheckingAccountDto(CheckingAccount checkingAccount);

    @Named("checkingAccountDtoToCheckingAccount")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "overdraftLimit", target = "overdraftLimit")
    CheckingAccount checkingAccountDtoToCheckingAccount(CheckingAccountDto checkingAccountDto);

    // User mapping methods
    @Named("userToUserDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "registrationDate", target = "registrationDate")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "totalBalance", target = "totalBalance")
    UserDto userToUserDto(User user);

    @Named("userDtoToUser")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "registrationDate", target = "registrationDate")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "totalBalance", target = "totalBalance")
    User userDtoToUser(UserDto userDto);

    // Administrator mapping methods
    @Named("administratorToAdministratorDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "registrationDate", target = "registrationDate")
    AdministratorDto administratorToAdministratorDto(Administrator administrator);

    @Named("administratorDtoToAdministrator")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "registrationDate", target = "registrationDate")
    Administrator administratorDtoToAdministrator(AdministratorDto administratorDto);

    // Category mapping methods
    @Named("categoryToCategoryDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryDto categoryToCategoryDto(Category category);

    @Named("categoryDtoToCategory")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Category categoryDtoToCategory(CategoryDto categoryDto);

    // Budget mapping methods
    @Named("budgetToBudgetDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "amountSpent", target = "amountSpent")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "user", target = "user")
    BudgetDto budgetToBudgetDto(Budget budget);

    @Named("budgetDtoToBudget")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "amountSpent", target = "amountSpent")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "user", target = "user")
    Budget budgetDtoToBudget(BudgetDto budgetDto);

    // Transaction mapping methods
    @Named("transactionToTransactionDto")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "account", target = "account")
    TransactionDto transactionToTransactionDto(Transaction transaction);

    @Named("transactionDtoToTransaction")
    @Mapping(source = "idTransaction", target = "idTransaction")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "account", target = "account")
    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    // Account mapping methods
    @Named("accountToAccountDto")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "user", target = "user")
    AccountDto accountToAccountDto(Account account);

    @Named("accountDtoToAccount")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "user", target = "user")
    Account accountDtoToAccount(AccountDto accountDto);

    // List mapping methods
    @IterableMapping(qualifiedByName = "transferToTransferDto")
    List<TransferDto> getTransfersDto(List<Transfer> listTransfers);

    @IterableMapping(qualifiedByName = "transferDtoToTransfer")
    List<Transfer> getTransfers(List<TransferDto> listTransfersDto);

    @IterableMapping(qualifiedByName = "depositToDepositDto")
    List<DepositDto> getDepositsDto(List<Deposit> listDeposits);

    @IterableMapping(qualifiedByName = "depositDtoToDeposit")
    List<Deposit> getDeposits(List<DepositDto> listDepositsDto);

    @IterableMapping(qualifiedByName = "withdrawalToWithdrawalDto")
    List<WithdrawalDto> getWithdrawalsDto(List<Withdrawal> listWithdrawals);

    @IterableMapping(qualifiedByName = "withdrawalDtoToWithdrawal")
    List<Withdrawal> getWithdrawals(List<WithdrawalDto> listWithdrawalsDto);

    @IterableMapping(qualifiedByName = "savingsAccountToSavingsAccountDto")
    List<SavingsAccountDto> getSavingsAccountsDto(List<SavingsAccount> listSavingsAccounts);

    @IterableMapping(qualifiedByName = "savingsAccountDtoToSavingsAccount")
    List<SavingsAccount> getSavingsAccounts(List<SavingsAccountDto> listSavingsAccountsDto);

    @IterableMapping(qualifiedByName = "checkingAccountToCheckingAccountDto")
    List<CheckingAccountDto> getCheckingAccountsDto(List<CheckingAccount> listCheckingAccounts);

    @IterableMapping(qualifiedByName = "checkingAccountDtoToCheckingAccount")
    List<CheckingAccount> getCheckingAccounts(List<CheckingAccountDto> listCheckingAccountsDto);

    @IterableMapping(qualifiedByName = "userToUserDto")
    List<UserDto> getUsersDto(List<User> listUsers);

    @IterableMapping(qualifiedByName = "userDtoToUser")
    List<User> getUsers(List<UserDto> listUsersDto);

    @IterableMapping(qualifiedByName = "administratorToAdministratorDto")
    List<AdministratorDto> getAdministratorsDto(List<Administrator> listAdministrators);

    @IterableMapping(qualifiedByName = "administratorDtoToAdministrator")
    List<Administrator> getAdministrators(List<AdministratorDto> listAdministratorsDto);

    @IterableMapping(qualifiedByName = "categoryToCategoryDto")
    List<CategoryDto> getCategoriesDto(List<Category> listCategories);

    @IterableMapping(qualifiedByName = "categoryDtoToCategory")
    List<Category> getCategories(List<CategoryDto> listCategoriesDto);

    @IterableMapping(qualifiedByName = "budgetToBudgetDto")
    List<BudgetDto> getBudgetsDto(List<Budget> listBudgets);

    @IterableMapping(qualifiedByName = "budgetDtoToBudget")
    List<Budget> getBudgets(List<BudgetDto> listBudgetsDto);

    @IterableMapping(qualifiedByName = "transactionToTransactionDto")
    List<TransactionDto> getTransactionsDto(List<Transaction> listTransactions);

    @IterableMapping(qualifiedByName = "transactionDtoToTransaction")
    List<Transaction> getTransactions(List<TransactionDto> listTransactionsDto);

    @IterableMapping(qualifiedByName = "accountToAccountDto")
    List<AccountDto> getAccountsDto(List<Account> listAccounts);

    @IterableMapping(qualifiedByName = "accountDtoToAccount")
    List<Account> getAccounts(List<AccountDto> listAccountsDto);
}
