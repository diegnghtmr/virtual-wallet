package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.exceptions.AccountException;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.AccountDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.mappers.IVirtualWalletMapper;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.VirtualWallet;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.VirtualWalletUtils;
import lombok.Getter;
import lombok.Setter;

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
        return virtualWalletMapper.getAccountsDto(virtualWallet.getAccounts());
    }
    public boolean addAccount(AccountDto accountDto) {
        try {
            if (!virtualWallet.verifyAccountExistence(accountDto.accountNumber())) {
                Account account = virtualWalletMapper.accountDtoToAccount(accountDto);
                getVirtualWallet().addAccount(account);
            }
            return true;
        } catch (Exception e) {
            e.getMessage();
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
            Account account = virtualWalletMapper.accountDtoToAccount(accountDto);
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
