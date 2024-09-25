package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CheckingAccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.SavingsAccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.mappers.IVirtualWalletMapper;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.VirtualWallet;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.VirtualWalletUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class ModelFactory {

    // Main model class
    VirtualWallet virtualWallet;
    IVirtualWalletMapper virtualWalletMapper = IVirtualWalletMapper.INSTANCE;

    private static class SingletonHolder {

        private static final ModelFactory eINSTANCE = new ModelFactory();




    }
    public static ModelFactory getInstance(){
        return SingletonHolder.eINSTANCE;
    }
    public ModelFactory() {
        initializeData();
    }
    private void initializeData() {
        virtualWallet = VirtualWalletUtils.initializeData();
    }
    public List<String> getTransactionTypes() {
        return VirtualWalletUtils.getTransactionTypes();
    }
    public List<String> getAccountTypes() {
        return VirtualWalletUtils.getAccountTypes();
    }


    // Methods to be implemented
    public List<AccountDto> getAccounts() {
        List<AccountDto> accountDtos = new ArrayList<>();
        accountDtos.addAll(virtualWalletMapper.getSavingsAccountsDto(virtualWallet.getSavingsAccountList()));
        accountDtos.addAll(virtualWalletMapper.getCheckingAccountsDto(virtualWallet.getCheckingAccountList()));
        return accountDtos;
    }

    public boolean addAccount(AccountDto accountDto) {
        try {
            if (!virtualWallet.verifyAccountExistence(accountDto.accountNumber())) {
                Account account;
                if (accountDto instanceof CheckingAccountDto) {
                    account = virtualWalletMapper.checkingAccountDtoToCheckingAccount((CheckingAccountDto) accountDto);
                } else if (accountDto instanceof SavingsAccountDto) {
                    account = virtualWalletMapper.savingsAccountDtoToSavingsAccount((SavingsAccountDto) accountDto);
                } else {
                    throw new IllegalArgumentException("Tipo de cuenta no soportado");
                }
                getVirtualWallet().addAccount(account);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAccount(AccountDto accountSelected) {
        boolean flagExist = false;
        try {
            flagExist = getVirtualWallet().removeAccount(accountSelected.accountNumber());
        } catch (AccountException e) {
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
            return true;
        } catch (AccountException e) {
            e.printStackTrace();
            return false;
        }
    }









//    public List<TransactionDto> getTransactionList() {
//        return  IVirtualWalletMapper.transactionDtoToTransaction(virtualWallet.getTransactionList());
//
//    }
}
