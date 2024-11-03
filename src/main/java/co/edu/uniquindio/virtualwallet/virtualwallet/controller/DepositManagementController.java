package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.DepositDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.AccountDto;

import java.util.List;

public class DepositManagementController extends CoreController {
    public DepositManagementController() {
        super();
    }

    public List<DepositDto> getDepositsByUser(String userId) {
        return modelFactory.getDepositsByUser(userId);
    }

    public List<Account> getAccountsByUserId(String id) {
        return modelFactory.getAccountListByUserId(id);
    }

    public List<CategoryDto> getCategoriesByUserId() {
        return modelFactory.getCategoriesByUserId();
    }

    public boolean isTransactionIdExists(String idTransaction) {
        return modelFactory.isTransactionIdExists(idTransaction);
    }

    public boolean addDeposit(DepositDto depositDto) {
        return modelFactory.addDeposit(depositDto);
    }
}
