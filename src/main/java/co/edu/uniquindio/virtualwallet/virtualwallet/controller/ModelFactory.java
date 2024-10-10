package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.CheckingAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.SavingsAccount;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CheckingAccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.SavingsAccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.mappers.IVirtualWalletMapper;
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

    // Utility Methods
    // ---------------

    public List<String> getTransactionTypes() {
        return VirtualWalletUtils.getTransactionTypes();
    }

    public List<String> getAccountTypes() {
        return VirtualWalletUtils.getAccountTypes();
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
}