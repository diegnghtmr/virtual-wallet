package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.TransferDto;

import java.util.List;

public class TransferManagementController extends CoreController {
    public TransferManagementController() {
        super();
    }

    public  boolean isTransactionIdExists(String idNumber) {
        return modelFactory.isTransactionIdExists(idNumber);
    }

    public boolean addTransfer(TransferDto transferDto) {
        return modelFactory.addTransfer(transferDto);
    }


    public List<TransferDto> getTransfersByUser(String userId) {
        return modelFactory.getTransfersByUser(userId);
    }

    public List<Account> getAccountsByUserId(String id) {
        return modelFactory.getAccountListByUserId(id);
    }

    public List<CategoryDto> getCategoriesByUserId(String id) {
        return modelFactory.getCategoriesByUserId(id);
    }

    public Account findByAccount(String id) {
        return modelFactory.isAccountId(id);
    }
}
