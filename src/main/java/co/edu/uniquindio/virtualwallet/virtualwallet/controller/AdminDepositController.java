package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.DepositDto;

import java.util.List;

public class AdminDepositController extends CoreController {

    public AdminDepositController(){
        super();
    }

    public List<DepositDto> getDeposits() {
        return modelFactory.getDeposits();
    }

    public List<CategoryDto> getCategories() {
        return modelFactory.getCategories();
    }

    public List<Account> getAccounts() {
        return modelFactory.getAccountList();
    }

    public boolean isTransactionIdExists(String idNumber) {
        return modelFactory.isTransactionIdExists(idNumber);
    }

    public boolean adminAddDeposit(DepositDto depositDto) {
        return modelFactory.adminAddDeposit(depositDto);
    }
}
