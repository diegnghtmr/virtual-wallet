package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.UserException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Transaction;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.*;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.mappers.IVirtualWalletMapper;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Person;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.VirtualWallet;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.BackupUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.PersistenceUtil;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Session;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.VirtualWalletUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ModelFactory {
    // Main model class
    VirtualWallet virtualWallet;
    IVirtualWalletMapper virtualWalletMapper = IVirtualWalletMapper.INSTANCE;



    // Singleton instance
    private static class SingletonHolder {

        private static final ModelFactory eINSTANCE = new ModelFactory();


    }
    public static ModelFactory getInstance() {
        return SingletonHolder.eINSTANCE;
    }
    public ModelFactory() {
        System.out.println("singleton class invocation");
        //1. initialize data and then save it to files
        //initializeData();
        //saveTestData();

        //2. Load data from files
        //loadDataFromFiles();

        //3. Save and Load the binary serializable resource
        //loadBinaryResource();
        //saveBinaryResource();

        // Backup the XML file before loading or saving
        BackupUtil.backupXMLFile(PersistenceUtil.VIRTUAL_WALLET_XML_FILE_PATH);
        //4. Save and Load the XML serializable resource
        loadXMLResource();
        saveXMLResource();


        //You should always check if the root of the resource is null

        if(virtualWallet == null){
            initializeData();
            //saveBinaryResource();
            saveXMLResource();
        }
        registerSystemActions("Login", 1, "login");
    }
    // Initialization Methods
    // ----------------------
    private void initializeData() {
        virtualWallet = VirtualWalletUtils.initializeData();
    }
    private void loadDataFromFiles() {
        virtualWallet = new VirtualWallet();
        try {
            PersistenceUtil.loadFileData(virtualWallet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveTestData() {
        try {
            PersistenceUtil.saveAccounts(getVirtualWallet().getAccounts());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadBinaryResource() {
        virtualWallet = PersistenceUtil.loadBinaryVirtualWalletResource();
    }
    private void saveBinaryResource() {
        PersistenceUtil.saveBinaryVirtualWalletResource(virtualWallet);
    }

    private void loadXMLResource() {
        virtualWallet = PersistenceUtil.loadXMLVirtualWalletResource();
    }

    private void saveXMLResource() {
        PersistenceUtil.saveXMLVirtualWalletResource(virtualWallet);
    }

    private void registerSystemActions(String logMessage, int level, String action) {
        PersistenceUtil.saveLogRecord(logMessage, level, action);
    }

    public void generateSerialization() {
        //saveBinaryResource();
        saveXMLResource();
    }


    // Utility Methods
    // ---------------

    public List<String> getTransactionTypes() {
        return VirtualWalletUtils.getTransactionTypes();
    }
    public List<String> getAccountTypes() {
        return VirtualWalletUtils.getAccountTypes();
    }

    public List<String> getCalifications() {
        return VirtualWalletUtils.getCalifications();
    }
    public String generateRandomCode() {
        return virtualWallet.generateRandomCode();
    }

    public boolean isVerified() {
        Person person = Session.getInstance().getPerson();
        return virtualWallet.isVerified(person.getId());
    }

    public boolean verifyCode(String verificationCode) {
        Person person = Session.getInstance().getPerson();
        boolean isCorrect = virtualWallet.verifyCode(person.getId(), verificationCode);
        if (isCorrect) {
            saveXMLResource();
        }
        return isCorrect;
    }
    public void setVerificationCode(String id, String verificationCode) {
        virtualWallet.setVerificationCode(id, verificationCode);
        saveXMLResource();
    }




    // Account Management Methods
    // --------------------------

    public List<AccountDto> getAccounts() {
        List<AccountDto> accountDtos = new ArrayList<>();
        accountDtos.addAll(virtualWalletMapper.getSavingsAccountsDto(virtualWallet.getSavingsAccountList()));
        accountDtos.addAll(virtualWalletMapper.getCheckingAccountsDto(virtualWallet.getCheckingAccountList()));
        return accountDtos;
    }
    public boolean addAccount(AccountDto accountDto) {
        try {
            if (virtualWallet.verifyAccountExistence(accountDto.accountNumber())) {
                return false;
            }
            Account account;
            if (accountDto instanceof CheckingAccountDto) {
                account = virtualWalletMapper.checkingAccountDtoToCheckingAccount((CheckingAccountDto) accountDto);
                registerSystemActions("CheckingAccountDto added: " + accountDto.accountNumber(), 1, "addAccount");
            } else if (accountDto instanceof SavingsAccountDto) {
                account = virtualWalletMapper.savingsAccountDtoToSavingsAccount((SavingsAccountDto) accountDto);
                registerSystemActions("SavingsAccountDto added: " + accountDto.accountNumber(), 1, "addAccount");
            } else {
                throw new IllegalArgumentException("Tipo de cuenta no soportado");
            }
            getVirtualWallet().addAccount(account);
            getVirtualWallet().addAccountToUser(account);
            saveXMLResource();
            return true;
        } catch (Exception e) {
            e.getMessage();
            registerSystemActions(e.getMessage(), 3, "addAccount");
            return false;
        }
    }

    public boolean removeAccount(AccountDto accountSelected) {
        boolean flagExist = false;
        try {
            flagExist = getVirtualWallet().removeAccount(accountSelected.accountNumber());

            if (flagExist) {
                getVirtualWallet().removeAccountFromUser(accountSelected.accountNumber());
                registerSystemActions("Account removed: " + accountSelected.accountNumber(), 1, "removeAccount");
                saveXMLResource();
            } else {
                registerSystemActions("Attempted to remove non-existent account: " + accountSelected.accountNumber(), 2, "removeAccount");
            }
        } catch (AccountException e) {
            registerSystemActions(e.getMessage(), 3, "removeAccount");
            e.printStackTrace();
        }
        return flagExist;
    }
    public boolean updateAccount(AccountDto accountSelected, AccountDto accountDto) {
        try {
            Account account;
            if (accountDto instanceof CheckingAccountDto) {
                account = virtualWalletMapper.checkingAccountDtoToCheckingAccount((CheckingAccountDto) accountDto);
            } else if (accountDto instanceof SavingsAccountDto) {
                account = virtualWalletMapper.savingsAccountDtoToSavingsAccount((SavingsAccountDto) accountDto);
            } else {
                throw new IllegalArgumentException("Tipo de cuenta no soportado");
            }

            getVirtualWallet().updateAccount(accountSelected.accountNumber(), account);
            getVirtualWallet().updateAccountFromUser(accountSelected.accountNumber(), account);
            registerSystemActions("Account updated: " + accountSelected.accountNumber(), 1, "updateAccount");
            saveXMLResource();
            return true;
        } catch (AccountException e) {
            registerSystemActions(e.getMessage(), 3, "updateAccount");
            e.printStackTrace();
            return false;
        }
    }

    public List<AccountDto> getAccountsByUserId(String userId) {
        List<Account> userAccounts = virtualWallet.getAccountListByUserId(userId);
        List<AccountDto> accountsDto = new ArrayList<>();
        for (Account account : userAccounts) {
            if (account instanceof SavingsAccount) {
                accountsDto.add(virtualWalletMapper.savingsAccountToSavingsAccountDto((SavingsAccount) account));
            } else if (account instanceof CheckingAccount) {
                accountsDto.add(virtualWalletMapper.checkingAccountToCheckingAccountDto((CheckingAccount) account));
            }
        }
        return accountsDto;
    }
    public List<TransactionDto> getTransactionsByUser(String userId) {
        List<Transaction> userTransactions = virtualWallet.getTransactionsByUser(userId);
        List<TransactionDto> transactionsDto = new ArrayList<>();
        for (Transaction transaction : userTransactions) {
            if (transaction instanceof Deposit) {
                transactionsDto.add(virtualWalletMapper.depositToDepositDto((Deposit) transaction));
            } else if (transaction instanceof Transfer) {
                transactionsDto.add(virtualWalletMapper.transferToTransferDto((Transfer) transaction));
            } else if (transaction instanceof Withdrawal) {
                transactionsDto.add(virtualWalletMapper.withdrawalToWithdrawalDto((Withdrawal) transaction));
            }
        }
        return transactionsDto;
    }




    // User Management Methods
    // -----------------------
    public Person validateLogin(String email, String password) throws Exception {
        return virtualWallet.validateLogin(email, password);
    }

    public boolean registerUser(UserDto userDto) {
        try {
            if (virtualWallet.verifyUserExistence(userDto.email())) {
                return false;
            }
            User user = virtualWalletMapper.userDtoToUser(userDto);
            registerSystemActions("UserDto added: " + userDto.email(), 1, "registerUser");
            getVirtualWallet().registerUser(user);
            saveXMLResource();
            return true;
        } catch (Exception e) {
            e.getMessage();
            registerSystemActions(e.getMessage(), 3, "registerUser");
            return false;
        }
    }
    public boolean updateUser(User person, UserDto userDto) {
        try {
            User user = virtualWalletMapper.userDtoToUser(userDto);
            getVirtualWallet().updateUser(person.getId(), user);
            registerSystemActions("User updated: " + userDto.email(), 1, "updateUser");
            saveXMLResource();
            return true;
        } catch (Exception e) {
            registerSystemActions(e.getMessage(), 3, "updateUser");
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(String id) {
        return virtualWallet.findUserById(id);
    }
    public List<DepositDto> getDepositsByUser(String userId) {
        return virtualWalletMapper.getDepositsDto(virtualWallet.getDepositsByUser(userId));
    }

    public boolean isTransactionIdExists(String idTransaction) {
        return virtualWallet.isTransactionIdExists(idTransaction);
    }
    public boolean addDeposit(DepositDto depositDto) {
        try {
            Deposit deposit = virtualWalletMapper.depositDtoToDeposit(depositDto);
            getVirtualWallet().getDepositList().add(deposit);
            getVirtualWallet().addDepositToAccount(deposit);
            registerSystemActions("DepositDto added: " + deposit.getIdTransaction(), 1, "addDeposit");
            saveXMLResource();
            return true;
        } catch (Exception e) {
            e.getMessage();
            registerSystemActions(e.getMessage(), 3, "addDeposit");
            return false;
        }
    }

    public List<Account> getAccountListByUserId(String id) {
        return virtualWallet.getAccountListByUserId(id);
    }
    public List<CategoryDto> getCategoriesByUserId(String id) {
        return virtualWalletMapper.getCategoriesDto(virtualWallet.getCategoryListByUserId(id));
    }

    public List<TransferDto> getTransfersByUser(String userId) {
        return virtualWalletMapper.getTransfersDto(virtualWallet.getTransfersByUser(userId));
    }
    public List<WithdrawalDto> getWithdrawalsByUser(String userId) {
        return virtualWalletMapper.getWithdrawalsDto(virtualWallet.getWithdrawalsByUser(userId));
    }

    public List<BudgetDto> getBudgetsByUser(String userId) {
        return virtualWalletMapper.getBudgetsDto(virtualWallet.getBudgetsByUser(userId));
    }

    public List<CategoryDto> getCategoriesByUser(String userId) {
        return virtualWalletMapper.getCategoriesDto(virtualWallet.getCategoryListByUserId(userId));
    }

    public void addVotedUser(String selectedRating) {
        virtualWallet.addVotedUser(selectedRating);
        saveXMLResource();
    }

    public String getUserRiskProfile(String id) {
        return virtualWallet.getUserRiskProfile(id);
    }

    public List<User> getUserList() {
        return virtualWallet.getUserList();

    }

    public List<DepositDto> getDeposits() {
        List<DepositDto> depositDtoList = new ArrayList<>();
        depositDtoList.addAll(virtualWalletMapper.getDepositsDto(virtualWallet.getDepositList()));
        return depositDtoList;
    }

    public List<CategoryDto> getCategories() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.addAll(virtualWalletMapper.getCategoriesDto(virtualWallet.getCategoryList()));
        return categoryDtoList;
    }


    public List<Account> getAccountList() {
        return virtualWallet.getAccounts();
    }

    public boolean adminAddDeposit(DepositDto depositDto) {
        try {
            Deposit deposit = virtualWalletMapper.depositDtoToDeposit(depositDto);
            getVirtualWallet().getDepositList().add(deposit);
            getVirtualWallet().addDepositToAccount(deposit);
            registerSystemActions("DepositDto added: " + deposit.getIdTransaction(), 1, "addDeposit");
            saveXMLResource();
            return true;
        } catch (Exception e) {
            e.getMessage();
            registerSystemActions(e.getMessage(), 3, "addDeposit");
            return false;
        }
    }

    public List<TransferDto> getTransfers() {
        List<TransferDto> transferDtoList = new ArrayList<>();
        transferDtoList.addAll(virtualWalletMapper.getTransfersDto(virtualWallet.getTransferList()));
        return transferDtoList;
    }


    public boolean performTransfer(TransferDto transferDto) {
        try {
            Transfer transfer = virtualWalletMapper.transferDtoToTransfer(transferDto);
            getVirtualWallet().getTransferList().add(transfer);
            getVirtualWallet().getTransferToAccount(transfer);  // Esta línea puede lanzar IllegalArgumentException
            registerSystemActions("Transferencia realizada de " + transfer.getAccount().getAccountNumber() + " a " + transfer.getReceivingAccount().getAccountNumber(), 1, "performTransfer");
            saveXMLResource();
            return true;
        } catch (IllegalArgumentException e) {
            // Aquí no es necesario hacer nada en específico, ya que se maneja en el controlador de vista.
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            registerSystemActions(e.getMessage(), 3, "performTransfer");
            return false;
        }
    }

    public List<WithdrawalDto> getWithdrawals() {
        List<WithdrawalDto> withdrawalDtoList = new ArrayList<>();
        withdrawalDtoList.addAll(virtualWalletMapper.getWithdrawalsDto(virtualWallet.getWithdrawalList()));
        return withdrawalDtoList;
    }

    public double getAverageRating() {
        return virtualWallet.getAverageRating();
    }

    public double getAverageUserBalance() {
        return virtualWallet.getAverageUserBalance();
    }

    public List<Category> getcommonExpenses() {
        return virtualWallet.getCategoryList();
    }

    public Map<String, Double> calculateTotalExpenses() {
        return virtualWallet.calculateTotalExpenses();
    }

    public List<User> getUsersWithMostTransactions() {
        return virtualWallet.getUsersWithMostTransactions();
    }

    public int countTransaction(User user) {
        return virtualWallet.countTransaction(user);
    }

    public List<UserDto> getUserListDto() {
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.addAll(virtualWalletMapper.getUsersDto(virtualWallet.getUserList()));
        return userDtoList;
    }

    public boolean addUser(UserDto userDto) {
        try {
            // Verificar si el usuario ya existe
            if (virtualWallet.verifyUserExists(userDto.id())) {
                return false; // Si existe, retorna false
            }
            User user = virtualWalletMapper.userDtoToUser(userDto);
            registerSystemActions("User added: " + userDto.id(), 1, "addUser");
            // Agregar el usuario a la billetera virtual
            getVirtualWallet().addUser(user);
            saveXMLResource();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            registerSystemActions(e.getMessage(), 3, "addUser");
            return false; // En caso de error, retorna false
        }
    }

    public boolean removeUser(UserDto userSelected) {
        boolean flagExist = false;
        try {
            flagExist = getVirtualWallet().removeUser(userSelected.id());

            if (flagExist) {
                registerSystemActions("Usuario eliminado: " + userSelected.id(), 1, "removeUser");
                saveXMLResource();
            } else {
                // Si el usuario no existe
                registerSystemActions("Intento de eliminar un usuario no existente: " + userSelected.id(), 2, "removeUser");
            }
        } catch (UserException e) {
            // Maneja las excepciones específicas de usuario
            registerSystemActions(e.getMessage(), 3, "removeUser");
            e.printStackTrace();
        }
        return flagExist; // Devuelve si el usuario fue eliminado exitosamente
    }

    public boolean adminUpdateUser(UserDto userSelected, UserDto userDto) {
        boolean userUpdated = false;
        try {
            User user = virtualWalletMapper.userDtoToUser(userDto);

            userUpdated = getVirtualWallet().updateUser(userSelected.id(), user);

            if (userUpdated) {
                registerSystemActions("User updated: " + userDto.email(), 1, "adminUpdateUser");
                saveXMLResource();
            } else {
                registerSystemActions("Failed to update user: " + userDto.email(), 2, "adminUpdateUser");
            }
        } catch (Exception e) {
            registerSystemActions(e.getMessage(), 3, "adminUpdateUser");
            e.printStackTrace();
        }
        return userUpdated;
    }


}