package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.TransferDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;

import java.util.List;

public class AdminTransferManagementController extends CoreController{

    public AdminTransferManagementController() {
        super();
    }

    public List<TransferDto> getTransfers() {
        return modelFactory.getTransfers();
    }

    public List<Account> getAccounts() {
        return modelFactory.getAccountList();
    }

    public List<CategoryDto> getCategories() {
        return modelFactory.getCategories();
    }

    public boolean isTransactionIdExists(String idNumber) {
        return modelFactory.isTransactionIdExists(idNumber);
    }

    public boolean performTransfer(TransferDto transferDto) {
        return modelFactory.performTransfer(transferDto);
    }

    public User searchUserTransfer(TransferDto transferDto) {
        return modelFactory.searchUserTransfer(transferDto);
    }

    public User searchUserTransferReceiving(TransferDto transferDto) {
        return modelFactory.searchUserTransferReceiving(transferDto);
    }
}
