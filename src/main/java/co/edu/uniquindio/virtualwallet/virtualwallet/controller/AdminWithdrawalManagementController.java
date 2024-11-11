package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.Account;
import co.edu.uniquindio.virtualwallet.virtualwallet.factory.inter.implementation.Withdrawal;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.WithdrawalDto;

import java.util.List;

public class AdminWithdrawalManagementController extends CoreController{

    public AdminWithdrawalManagementController() {
        super();
    }

    public List<WithdrawalDto> getWithdrawals() {
        return modelFactory.getWithdrawals();
    }

    public List<Account> getAccounts() {
        return modelFactory.getAccountList();
    }

    public List<CategoryDto> getCategories(){
        return modelFactory.getCategories();
    }

    public boolean isTransactionIdExists(String idNumber) {
        return modelFactory.isTransactionIdExists(idNumber);
    }

    public boolean addWithdrawal(WithdrawalDto withdrawalDto) {
       return modelFactory.addWithdrawal(withdrawalDto);
    }
}
